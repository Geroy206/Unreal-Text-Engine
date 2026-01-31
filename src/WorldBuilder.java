import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class WorldBuilder {

    private static class LocationData {
        int id;
        String name;
        String description;
        String status;
        Integer requiredKeyId;
        List<String> view;
        List<Integer> neighborIds;
        List<Map<String, Object>> items;
        List<Map<String, Object>> enemyList;
    }

    public static Location loadWorld(String filePath) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<ArrayList<LocationData>>(){}.getType();
            List<LocationData> dataList = gson.fromJson(reader, listType);

            Map<Integer, Location> tempRegistry = new HashMap<>();


            for (LocationData data : dataList) {
                LocationStatus locStatus = (data.status != null)
                        ? LocationStatus.valueOf(data.status.toUpperCase())
                        : LocationStatus.OPEN;

                Location loc = new Location(
                        data.name,
                        data.id,
                        locStatus,
                        data.description,
                        data.view != null ? data.view : new ArrayList<>(),
                        new ArrayList<>(), // paths
                        new ArrayList<>(), // enemyList
                        data.requiredKeyId
                );

                if (data.items != null) {
                    for (Map<String, Object> itemData : data.items) {
                        loc.getInventory().addItem(parseItem(itemData));
                    }
                }
                if (data.enemyList != null) {
                    for (Map<String, Object> enemyData : data.enemyList) {
                        loc.addEnemy(parseEnemy(enemyData));
                    }
                }

                tempRegistry.put(data.id, loc);
            }

            for (LocationData data : dataList) {
                Location current = tempRegistry.get(data.id);
                if (data.neighborIds != null) {
                    for (int neighborId : data.neighborIds) {
                        Location neighbor = tempRegistry.get(neighborId);
                        if (neighbor != null) {
                            current.addPath(neighbor);
                        }
                    }
                }
            }

            // Возвращаем стартовую локацию
            return tempRegistry.get(dataList.get(0).id);

        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Ошибка при загрузке (проверь статус или путь): " + e.getMessage());
            return null;
        }
    }

    private static Item parseItem(Map<String, Object> map) {
        String name = (String) map.get("name");
        int id = ((Number) map.get("id")).intValue();
        String desc = (String) map.get("description");

        // Проверка на оружие
        if (map.containsKey("damage")) {
            float damage = ((Number) map.get("damage")).floatValue();
            return new Weapon(name, id, desc, damage);
        }

        // Проверка на предмет восстановления
        if (map.containsKey("healValue")) {
            float healValue = ((Number) map.get("healValue")).floatValue();

            int healAmount = map.containsKey("healAmount")
                    ? ((Number) map.get("healAmount")).intValue()
                    : 1;

            boolean isConsumable = map.containsKey("isConsumable")
                    ? (boolean) map.get("isConsumable")
                    : true;

            return new RecoveryItem(name, id, desc, isConsumable, healValue, healAmount);
        }

        // Проверка на обычный предмет
        boolean isConsumable = false;
        if (map.containsKey("isConsumable")) {
            isConsumable = (boolean) map.get("isConsumable");
        }

        return new Item(name, id, desc, isConsumable);
    }

    private static Enemy parseEnemy(Map<String, Object> map) {
        String name = (String) map.get("name");
        int id = ((Number) map.get("id")).intValue();
        float hp = ((Number) map.get("hp")).floatValue();
        float damage = ((Number) map.get("damage")).floatValue();

        Enemy enemy = new Enemy(name, id, hp, damage);

        if (map.containsKey("items") && map.get("items") instanceof List) {
            List<Map<String, Object>> enemyItemsData = (List<Map<String, Object>>) map.get("items");
            for (Map<String, Object> itemData : enemyItemsData) {
                enemy.getInventory().addItem(parseItem(itemData));
            }
        }

        return enemy;
    }
}


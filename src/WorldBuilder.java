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
                        new ArrayList<>(),
                        data.requiredKeyId
                );

                if (data.items != null) {
                    for (Map<String, Object> itemData : data.items) {
                        loc.getInventory().addItem(parseItem(itemData));
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

        if (map.containsKey("damage")) {
            float damage = ((Number) map.get("damage")).floatValue();
            return new Weapon(name, id, desc, damage);
        }

        boolean isConsumable = false;
        if (map.containsKey("isConsumable")) {
            isConsumable = (boolean) map.get("isConsumable");
        }

        return new Item(name, id, desc, isConsumable);
    }
}
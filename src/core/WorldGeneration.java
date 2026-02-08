package core;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/*
Правила генерации примерные -
Генерация предметов:
Должно быть сгенерировано как минимум 2 оружия
Как минимум 4 аптечки
Как минимум один ключ

Генерация противников:
Как минимум 1 противник, у которого должны быть какие-то предметы, как минимум 1 предмет

Построение мира:
Сначала мы генерируем перекрёстки и связи между ними

Локации:
Как минимум одна закрытая
Как минимум одна с ивентом
Как минимум одна с противником
Привязываются рандомно к какому-то из перекрёстков


Реализация приостановлена, в будущем реализовано будет через создание World.json который будет читать и воспроизводить WorldBuilder
 */
public class WorldGeneration {
    private static List<String> randomLocName = new ArrayList<>(Arrays.asList("Лес", "Болото", "Пещера"));
    private static Map<String, String> DescriptionMapForLoc = new HashMap<>(Map.of(
            "Лес", "Лес",
            "Болото", "Болото",
            "Пещера", "Пещера"
    ));
    private static List<Item> poolLocItems = new ArrayList<>();
    private static List<Item> poolEnemyItems = new ArrayList<>();


    public WorldGeneration() {



    }


    private static Map<Integer, Location> locationGeneration() {
        Map<Integer, Location> tempRegistry = new HashMap<>();

        for (int i =0; i < 4; i++) {
            String locName = randomLocName.get(ThreadLocalRandom.current().nextInt(randomLocName.size()));;

            Location loc = new Location(
                    locName,
                    200 + i,
                    LocationStatus.OPEN,
                    DescriptionMapForLoc.get(locName),
                    null,
                    null,
                    null,
                    null
            );

            tempRegistry.put(loc.getID(), loc);
        }

        return tempRegistry;
    }


    private static Map<Integer, Location> crossroadGeneration() {
        Map<Integer, Location> tempRegistry = new HashMap<>();

        for (int i = 0; i < 6; i++) {

            Location crossroad = new Location(
                    "Перекрёсток " + i,
                    100 + i,
                    LocationStatus.OPEN,
                    "Перекрёсток номер " + i,
                    null,
                    null,
                    null,
                    null
            );

            tempRegistry.put(crossroad.getID(), crossroad);
        }

        for (int i = 100; i < 105; i++) {
            Location current = tempRegistry.get(i);
            Location next = tempRegistry.get(i + 1);

            if (!current.getPaths().contains(next)) {
                current.addPath(next);
                next.addPath(current);
            }

            if (i < 104 && ThreadLocalRandom.current().nextInt(100) < 40) {
                Location skipOne = tempRegistry.get(i + 2);
                current.addPath(skipOne);
                skipOne.addPath(current);
            }
        }

        return tempRegistry;
    }

    public static Location Generation() {
        Map<Integer, Location> crossroads = crossroadGeneration();
        Map<Integer, Location> locations = locationGeneration();

        // Возвращаем стартовую локацию
        return crossroads.get(100);
    }
}

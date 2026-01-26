import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {

    // Работа с локациями
    public Map<Integer, Location> createPlayerChoices(Location currentLocation) {
        List<Location> availablePaths = currentLocation.getPaths();

        Map<Integer, Location> choicesMap = new HashMap<>();

        for (int i = 0; i < availablePaths.size(); i++) {

            int choiceNumber = i + 1;
            Location destination = availablePaths.get(i);

            choicesMap.put(choiceNumber, destination);
        }

        return choicesMap;
    }

    public void printMap(Map<Integer, Location> choices) {
        System.out.println("Выборы для игрока:");

        for (Map.Entry<Integer, Location> entry : choices.entrySet()) {
            int choiceNumber = entry.getKey();
            Location destination = entry.getValue();

            System.out.println(choiceNumber + ". " + destination.getName());
        }
    }

    public Map<Integer, Location> handler(Location playerCurrentLocation) {
        Map<Integer, Location> choices = createPlayerChoices(playerCurrentLocation);
        printMap(choices);

        return choices;
    }

    public void choicesHandler(int prompt, Player player, Map<Integer, Location> choices) {
        Location currentLocation = player.getCurrentLocation();
        Location nextLocation = currentLocation.handleSelection(prompt, choices);

        if (nextLocation != null) {
            if (nextLocation.getStatus() != LocationStatus.LOCKED) {
                currentLocation.setStatus(LocationStatus.EXPLORED);
                player.moveTo(nextLocation);
            } else {
                System.out.println("Путь закрыт!");
            }
        }
    }

    // Работа с инвентарём
    public Map<Integer, Item> createItemChoices(Player player) {
        List<Item> items = player.getInventory().getContents();
        Map<Integer, Item> itemMap = new HashMap<>();

        System.out.println("======== ВАШ ИНВЕНТАРЬ ========");
        if (items.isEmpty()) {
            System.out.println("Ваш рюкзак пуст.");
            System.out.println("0. Назад");
            return itemMap;
        }

        for (int i = 0; i < items.size(); i++) {
            int choice = i + 1;
            Item item = items.get(i);
            itemMap.put(choice, item);
            System.out.println(choice + ". " + item.getName() + " — " + item.getDescription());
        }
        System.out.println("0. Назад");
        return itemMap;
    }
}

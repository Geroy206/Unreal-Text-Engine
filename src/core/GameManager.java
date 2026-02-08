package core;

import java.util.ArrayList;
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
        System.out.println("-------------------");

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

    public void choicesHandler(int prompt, Player player, Map<Integer, Location> choices, GameLoop loop) {
        Location currentLocation = player.getCurrentLocation();
        Location nextLocation = currentLocation.handleSelection(prompt, choices);

        if (nextLocation != null) {
            if (nextLocation.canPlayerEnter(player)) {

                if (nextLocation.getStatus() == LocationStatus.LOCKED) {
                    int keyId = nextLocation.getRequiredKeyId();
                    Item keyItem = player.getInventory().getItemById(keyId);

                    System.out.println("Вы использовали " + keyItem.getName() + " и открыли проход!");
                    if (keyItem.isConsumable()) {
                        player.getInventory().removeItem(keyItem);
                        System.out.println("(Предмет '" + keyItem.getName() + "' был израсходован)");
                    } else {
                        System.out.println("(Предмет '" + keyItem.getName() + "' остался у вас)");
                    }

                    nextLocation.setStatus(LocationStatus.OPEN);
                } else if (nextLocation.getStatus() == LocationStatus.FIGHT) {
                    loop.setGameState(GameState.IN_COMBAT);
                } else if (nextLocation.getStatus() == LocationStatus.EVENT) {
                    loop.setGameState(GameState.EVENT);
                }

                currentLocation.setStatus(LocationStatus.EXPLORED);
                player.moveTo(nextLocation);
                System.out.println("Вы перешли в: " + nextLocation.getName() + "\n");

                if (nextLocation.getStatus() == LocationStatus.FIGHT) {
                    System.out.println("Но на вашем пути оказались враги!\n");
                }

            } else {
                System.out.println("Путь закрыт! Кажется, вам нужен какой-то особый предмет...\n");
            }
        } else {
            System.out.println("Туда нельзя пройти.\n");
        }
    }


    // Работа с инвентарём
    public Map<Integer, Item> createItemChoices(Player player) {
        List<Item> items = player.getInventory().getContents();
        Map<Integer, Item> itemMap = new HashMap<>();

        System.out.println("======== ИНВЕНТАРЬ ========");
        if (items.isEmpty()) {
            System.out.println("Ваш рюкзак пуст.");
            System.out.println("0. Назад");
            return itemMap;
        }

        for (int i = 0; i < items.size(); i++) {
            int choice = i + 1;
            Item item = items.get(i);
            itemMap.put(choice, item);
            System.out.println(choice + ". " + item.getName());
        }
        System.out.println("0. Назад");
        return itemMap;
    }

    public Map<Integer, String> createItemActionChoices(Item item) {
        Map<Integer, String> actionMap = new HashMap<>();

        List<String> allowedActions = item.getSupportedInteractions();

        System.out.println("======== " + item.getName() + " ========");
        for (int i = 0; i < allowedActions.size(); i++) {
            int choice = i + 1;
            String actionName = allowedActions.get(i);
            actionMap.put(choice, actionName);
            System.out.println(choice + ". " + actionName);
        }
        System.out.println("0. Назад");

        return actionMap;
    }
}

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {

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
        Location nextLocation = player.getCurrentLocation().handleSelection(prompt, choices);

        if (nextLocation != null) {
            player.moveTo(nextLocation);
        }
    }
}

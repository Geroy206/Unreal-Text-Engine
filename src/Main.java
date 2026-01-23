import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        GameLoop looper = new GameLoop();

        Location debug_location = new Location("DEBUG", 100, LocationStatus.OPEN, "DEBUG", null);
        Location crossroad0 = new Location("Перекрёсток 1", 200, LocationStatus.OPEN, "Перекрёсток номер 1", null);
        Location crossroad1 = new Location("Перекрёсток 2", 201, LocationStatus.OPEN, "Перекрёсток номер 2", null);
        Location crossroad2 = new Location("Перекрёсток 3", 202, LocationStatus.OPEN, "Перекрёсток номер 3", null);

        crossroad0.addPath(crossroad1);
        crossroad0.addPath(crossroad2);

        crossroad1.addPath(crossroad0);

        crossroad2.addPath(crossroad0);


        List<Location> locationsInGame = new ArrayList<Location>(List.of(crossroad0, crossroad1, crossroad2, debug_location));

        World world = new World(locationsInGame);

        Player player = new Player("Debug", 1, crossroad0);

        looper.loop(player, world);
    }
}
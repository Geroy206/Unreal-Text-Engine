import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        GameLoop looper = new GameLoop();

        Location debug_location = new Location("DEBUG", 100, LocationStatus.OPEN, "DEBUG", null, null);
        Location crossroad0 = new Location("Перекрёсток 1", 200, LocationStatus.OPEN, "Перекрёсток номер 1", null, null);
        Location crossroad1 = new Location("Перекрёсток 2", 201, LocationStatus.OPEN, "Перекрёсток номер 2", null, null);
        Location crossroad2 = new Location("Перекрёсток 3", 202, LocationStatus.OPEN, "Перекрёсток номер 3", null, null);

        Item debugItem = new Item("Пиздец", 300, "DEBUG");
        Weapon coltM1911 = new Weapon("Colt M1911", 301, "Американский пистолет .45 калибра", 20);

        crossroad0.addPath(crossroad1);
        crossroad0.addPath(crossroad2);

        crossroad1.addPath(crossroad0);

        crossroad2.addPath(crossroad0);

        crossroad0.addPath(debug_location);
        debug_location.addPath(crossroad0);

        debug_location.getInventory().addItem(debugItem);
        debug_location.getInventory().addItem(coltM1911);

        List<Location> locationsInGame = new ArrayList<Location>(List.of(crossroad0, crossroad1, crossroad2, debug_location));

        World world = new World(locationsInGame);

        Player player = new Player("Debug", 1, 100, crossroad0);

        looper.loop(player, world);
    }
}
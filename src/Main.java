import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Команды в игре:");
        System.out.println("exit: Выйти из игры");
        System.out.println("take *arg: взять предмет из локации, можно вписать что именно взять, а можно просто take, тогда берётся первый предмет");
        System.out.println("look: команда для осмотра локации");
        System.out.println("inv: открывает инвентарь игрока\n");

        GameLoop looper = new GameLoop();

        Location startLocation = WorldBuilder.loadWorld("src/resources/world.json");

        if (startLocation == null) {
            System.err.println("Критическая ошибка: Не удалось загрузить мир игры.");
            return;
        }

        Player player = new Player("Debug", 1, 100, startLocation);

        RecoveryItem bandage = new RecoveryItem("Бинт", 600, "Тестовый бинт", true, 50.0f, 1);
        Weapon testweapon = new Weapon("Тестовый анигилятор", 601, "ТЕСТ", 20);


        player.takeDamage(90);
        player.getCurrentLocation().getInventory().addItem(bandage);
        player.setEquippedWeapon(testweapon);

        looper.loop(player);
    }
}
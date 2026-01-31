import java.util.ArrayList;
import java.util.List;

// Временное решение
public class CombatManager {

    public enum Move {
        PLAYER,
        ENEMY
    }

    public void printEntityHp(Player player, Enemy enemy) {
        System.out.println("======== COMBAT ========");
        System.out.println("Здоровье игрока: " + player.getHp());
        System.out.println("Здоровье противника: " + enemy.getHp());
    }

    public void printMove(Player player, Enemy enemy, Move whoMove) {
        if (whoMove == Move.PLAYER) {
            System.out.println("\nВы нанесли " + player.giveDamage() + " Урона по " + enemy.getName());
            System.out.println("Здоровье " + enemy.getName() + ": " + enemy.getHp());
        } else if (whoMove == Move.ENEMY) {
            System.out.println("\n" + enemy.getName() + " атаковал вас и нанёс " + enemy.giveDamage());
            System.out.println("Здоровье игрока: " + player.getHp());
        }
    }

    public void createCombatActions(Player player, Enemy enemy) {
        System.out.println("1. Атаковать");
        System.out.println("2. Использовать аптечку");
        System.out.println("3. Попытаться сбежать");
    }

    public void choicesHandler(int prompt, Player player, Enemy enemy, GameLoop loop) {
        switch (prompt) {
            case 1:
                enemy.takeDamage(player.giveDamage());
                printMove(player, enemy, Move.PLAYER);

                if (!enemy.isAlive()) {
                    System.out.println("Противник " + enemy.getName() + " повержен!");

                    if (!enemy.getInventory().getContents().isEmpty()) {
                        enemy.dropItem(player.getCurrentLocation());
                    }

                    player.getCurrentLocation().removeEnemy(enemy);

                    if (player.getCurrentLocation().getEnemyList().isEmpty()) {
                        System.out.println(player.getCurrentLocation().getName() + ": Очищена от врагов!\n");
                        loop.setGameState(GameState.EXPLORING);
                        player.getCurrentLocation().setStatus(LocationStatus.OPEN);

                    } else {
                        System.out.println("\nВ локации остались враги! ");
                        enemy = player.getCurrentLocation().getOneEnemy();
                        System.out.println("Ваш новый враг! " + enemy.getName());
                    }
                }

                if (enemy != null && enemy.isAlive()) {
                    player.takeDamage(enemy.giveDamage());
                    printMove(player, enemy, Move.ENEMY);

                    if (player.getHp() <= 0) {
                        System.out.println("\nВы погибли! Игра окончена.");

                        loop.setGameState(GameState.GAME_OVER);
                    }
                }
                break;
            case 2:
                List<Item> inventorySnapshot = new ArrayList<>(player.getInventory().getContents());
                boolean hasUsedAny = false;

                for (Item item : inventorySnapshot) {
                    if (item instanceof RecoveryItem) {
                        item.use(player);
                        hasUsedAny = true;

                        break;
                    }
                }

                if (!hasUsedAny) {
                    System.out.println("У вас нет аптечек которые можно было бы использовать.");
                }
                break;
            case 3:
                System.out.println("TEST");
                break;
        }
    }
}

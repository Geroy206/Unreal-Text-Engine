
// Временное решение
public class CombatManager {

    public void printEntityHp(Player player, Enemy enemy) {
        System.out.println("======== COMBAT ========");
        System.out.println("Здоровье игрока: " + player.getHp());
        System.out.println("Здоровье противника: " + enemy.getHp());
    }

    public void printMove(Player player, Enemy enemy, int whoMove) {
        if (whoMove == 0) {
            System.out.println("Вы нанесли " + player.giveDamage() + "По " + enemy.getName());
            System.out.println("Здоровье противника: " + enemy.getHp());
        } else if (whoMove == 1) {
            System.out.println(enemy.getName() + "атаковал вас и нанёс " + enemy.giveDamage());
            System.out.println("Здоровье игрока: " + player.getHp());
        }
    }

    public void createCombatActions(Player player, Enemy enemy) {
        System.out.println("1. Атаковать");
        System.out.println("2. Использовать аптечку");
        System.out.println("3. Попытаться сбежать");
    }

    public void choicesHandler(int prompt, Player player, Enemy enemy) {

        switch (prompt) {
            case 1:
                enemy.takeDamage(player.giveDamage());
                break;
            case 2:
                System.out.println("TEST");
                break;
            case 3:
                System.out.println("TEST");
                break;



        }


    }
}

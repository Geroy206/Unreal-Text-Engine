import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

// СТРАШНЫЙ ГОВНОКОД! ОСТОРОЖНО!!!!
public class CombatManager {
        private CombatState combatState = CombatState.WEAPON_SELECT;



    public enum CombatState {
        COMBAT_PROCESS,
        WEAPON_SELECT,
    }

    public CombatState getCombatState() { return combatState; }

    public Map<Integer, Weapon> createWeaponChoices(Player player) {
        List<Item> items = player.getInventory().getContents();
        Map<Integer, Weapon> weaponMap = new HashMap<>();

        int counter = 1;
        for (Item item : items) {
            if (item instanceof Weapon) {
                weaponMap.put(counter++, (Weapon) item);
            }
        }
        return weaponMap;
    }

    public void printWeaponChoices(Map<Integer, Weapon> weaponMap) {
        if (weaponMap.isEmpty()) {
            System.out.println("У вас нет доступного оружия.");
            System.out.println("Введите цифру '1' чтобы продолжить.");
            return;
        }

        System.out.println("======== Выберите оружие для боя ========" );
        for (Map.Entry<Integer, Weapon> entry : weaponMap.entrySet()) {
            Weapon weapon = entry.getValue();
            System.out.println(entry.getKey() + ". " + weapon.getName());
        }
    }

    public void choiceWeaponHandler(int choice, Map<Integer, Weapon> weaponMap , Player player) {
        if (weaponMap.isEmpty() && player.getEquippedWeapon() == null) {
            Weapon tempKnife = new Weapon("Нож", 1000, "Временный нож", 10);

            player.setEquippedWeapon(tempKnife);

            System.out.println("Вы отправляетесь в бой с ножом!");

            combatState = CombatState.COMBAT_PROCESS;
        }

        if (weaponMap.containsKey(choice)) {
            Weapon selectedWeapon = weaponMap.get(choice);

            player.setEquippedWeapon(selectedWeapon);

            System.out.println("Вы выбрали: " + selectedWeapon.getName());
        } else if (!weaponMap.isEmpty()) {
            System.out.println("Неверный выбор.");
        }
    }

    public void printCombatData(Player player, Enemy enemy) {
        System.out.println("======== COMBAT ========");
        System.out.println("Здоровье игрока: " + player.getHp());
        System.out.println("Оружие игрока: " + player.getEquippedWeapon().getName());
        System.out.println("\nЗдоровье противника: " + enemy.getHp());
    }

    public void createCombatActions(Player player, Enemy enemy) {
        System.out.println("1. Атаковать");
        System.out.println("2. Использовать аптечку");
        System.out.println("3. Попытаться сбежать");
    }

    public void playerTurn(Player player, Enemy enemy) {
        enemy.takeDamage(player.giveDamage());
        System.out.println("\nВы нанесли " + player.giveDamage() + " Урона по " + enemy.getName());
        System.out.println("Здоровье " + enemy.getName() + ": " + enemy.getHp());
    }

    public void enemyTurn(Player player, Enemy enemy) {
        if (enemy != null && enemy.isAlive()) {
            player.takeDamage(enemy.giveDamage());
            System.out.println("\n" + enemy.getName() + " атаковал вас и нанёс " + enemy.giveDamage());
            System.out.println("Здоровье игрока: " + player.getHp());

            if (player.getHp() <= 0) {
                System.out.println("\nВы погибли! Игра окончена.");
            }
        }
    }

    public void playerHeal(Player player) {
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
    }

    public void choicesHandler(int prompt, Player player, Enemy enemy, Map<Integer, Weapon> weaponMap) {

        if (combatState == CombatState.COMBAT_PROCESS) {
            switch (prompt) {
                case 1:
                    playerTurn(player, enemy);

                    if (!enemy.isAlive()) {
                        System.out.println("Противник " + enemy.getName() + " повержен!");

                        if (!enemy.getInventory().getContents().isEmpty()) {
                            enemy.dropItem(player.getCurrentLocation());
                        }

                        player.getCurrentLocation().removeEnemy(enemy);

                        if (player.getCurrentLocation().getEnemyList().isEmpty()) {
                            System.out.println("\n" + player.getCurrentLocation().getName() + ": Очищена от врагов!\n");
                            player.getCurrentLocation().setStatus(LocationStatus.OPEN);
                            combatState = CombatState.WEAPON_SELECT;
                            return;
                        } else {
                            System.out.println("\nВ локации остались враги! ");
                            enemy = player.getCurrentLocation().getOneEnemy();
                            System.out.println("Ваш новый враг! " + enemy.getName());
                        }
                    }

                    enemyTurn(player, enemy);
                    break;
                case 2:
                    playerHeal(player);

                    enemyTurn(player, enemy);
                    break;
                case 3:
                    System.out.println("Попытка сбежать...");

                    // в будущем видимо нужно будет добавить проверку на класс босса, чтобы невозможно было сбежать
                    if (ThreadLocalRandom.current().nextInt(1, 5) == 1) {
                        System.out.println("УДАЧНО!");

                        combatState = CombatState.WEAPON_SELECT;
                        player.moveTo(player.getCurrentLocation().getFirstPath());
                    } else {
                        System.out.println("НЕУДАЧНО!");

                        enemyTurn(player, enemy);
                    }
                    break;
            }
        } else if (combatState == CombatState.WEAPON_SELECT) {
            choiceWeaponHandler(prompt, weaponMap, player);

            if (player.getEquippedWeapon() != null) {
                combatState = CombatState.COMBAT_PROCESS;
            }
        }
    }
}

import java.util.List;

public class RecoveryItem extends Item {
    public final float healValue;
    public int healAmount;

    public RecoveryItem(String name, int id, String description, boolean isConsumable, float healValue, int healAmount) {
        super(name, id, description, isConsumable);
        this.healValue = healValue;
        this.healAmount = healAmount;
    }

    @Override
    public String getDescription() {
        StringBuilder result = new StringBuilder(description);

        result.append("\nВосстанавливает: " + healValue + " HP");

        if (healAmount >= 1) {
            result.append("\nКоличество использований: " + healAmount);
        }

        return result.toString();
    }

    @Override
    public void use(Entity target) {
        boolean healUse = target.getHeal(healValue);

        if (healUse) {
            if (healAmount >= 1) {
                healAmount--;
            }

            if (isConsumable && healAmount <= 0) {
                target.getInventory().removeItem(this);
            }
        }
    }

    @Override
    public List<String> getSupportedInteractions() {
        return List.of("Использовать", "Осмотреть", "Выбросить");
    }
}

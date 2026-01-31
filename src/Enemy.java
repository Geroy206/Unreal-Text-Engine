import java.util.ArrayList;
import java.util.List;

public class Enemy extends Entity {
    private final float damage;

    public Enemy(String name, int id, float hp, float damage) {
        super(name, id, hp);
        this.damage = damage;
    }

    public float giveDamage() { return damage; }

    public void dropItem(Location loc) {
        List<Item> loot = new ArrayList<>(this.getInventory().getContents());

        for (Item item : loot) {
            loc.getInventory().addItem(item);
            this.getInventory().removeItem(item);
        }

        if (!loot.isEmpty()) {
            System.out.println(this.getName() + " выронил предметы: " + loot.size() + " шт.");
        }
    }
}

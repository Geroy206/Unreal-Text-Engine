import java.util.List;

public class Enemy extends Entity {
    private final float damage;

    public Enemy(String name, int id, float hp, float damage) {
        super(name, id, hp);
        this.damage = damage;
    }

    public float giveDamage() { return damage; }

    public void dropItem(Location loc) {
        loc.getInventory().addItem(this.getInventory().getItem());
    }
}

import java.util.ArrayList;
import java.util.Objects;

public class Player {
    private final String name;
    private final int ID;
    private float hp;
    private Location currentLocation;
    private final Inventory inventory;

    public Player (String name, int ID, float hp, Location currentLocation) {
        this.name = name;
        this.ID = ID;
        this.currentLocation = currentLocation;
        this.hp = hp;
        this.inventory = new Inventory(null);
    }

    public String getName() { return name; }

    public int getID() { return ID; }

    public float getHp() { return hp; }

    public void takeDamage(float take) {
        this.hp -= take;
    }

    public Inventory getInventory() { return inventory; }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void moveTo(Location newLocation) {
        this.currentLocation = newLocation;
    }

    public String getLocName() { return this.currentLocation.getName(); }

    public String getLocDescription() { return this.currentLocation.getDescription(); }

}

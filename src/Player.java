
public class Player extends Entity {
    private Location currentLocation;
    private Weapon equippedWeapon;

    public Player(String name, int id, float hp, Location currentLocation) {
        super(name, id, hp);
        this.currentLocation = currentLocation;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void moveTo(Location newLocation) {
        this.currentLocation = newLocation;
    }

    public String getLocName() { return this.currentLocation.getName(); }

    public String getLocDescription() { return this.currentLocation.getDescription(); }

    public Weapon getEquippedWeapon() { return equippedWeapon; }

    public void changeEquippedWeapon(Weapon newWeapon) {
        this.equippedWeapon = newWeapon;
    }

    public float giveDamage() { return equippedWeapon.giveDamage(); }
}

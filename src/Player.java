
public class Player {
    private final String name;
    private final int ID;
    private Location currentLocation;

    public Player (String name, int ID, Location currentLocation) {
        this.name = name;
        this.ID = ID;
        this.currentLocation = currentLocation;
    }

    public String getName() { return name; }

    public int getID() { return ID; }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void moveTo(Location newLocation) {
        this.currentLocation = newLocation;
    }

    public String getLocName() { return this.currentLocation.getName(); }

    public String getLocDescription() { return this.currentLocation.getDescription(); }

}

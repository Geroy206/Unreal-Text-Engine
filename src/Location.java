import java.util.*;

public class Location {
    private final String name;
    private final int ID;
    private final LocationStatus status;
    private String description;
    private final List<Location> paths;

    public Location (String name, int ID, LocationStatus status, String description, List<Location> Paths) {
        this.name = name;
        this.ID = ID;
        this.status = status;
        this.description = description;
        this.paths = Objects.requireNonNullElseGet(Paths, ArrayList::new);
    }

    public String getName() { return name; }

    public int getID() { return ID; }

    public String getDescription() { return description;}

    public LocationStatus getStatus() { return status; }

    public List<Location> getPaths() { return Collections.unmodifiableList(paths); }

    public void setDescription(String description) { this.description = description; }

    public void addPath(Location newDestination) {
        if (!paths.contains(newDestination)) {
            paths.add(newDestination);
        }
    }

    public void removePath(Location oldDestination) {
        paths.remove(oldDestination);
    }

    public Location handleSelection(int prompt, Map<Integer, Location> choices) {
        if (choices.containsKey(prompt)) {
            return choices.get(prompt);
        }
        return null;
    }
}

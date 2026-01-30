import java.util.*;

public class Location {
    private final String name;
    private final int ID;
    private LocationStatus status;
    private String description;
    private final List<String> view;
    private final List<Location> paths;
    private final Inventory inventory;
    private Integer requiredKeyId;

    public Location (String name, int ID, LocationStatus status, String description, List<String> view, List<Location> Paths, Integer requiredKeyId) {
        this.name = name;
        this.ID = ID;
        this.status = status;
        this.description = description;
        this.view = Objects.requireNonNullElseGet(view, ArrayList::new);
        this.paths = Objects.requireNonNullElseGet(Paths, ArrayList::new);
        this.inventory = new Inventory(null);
        this.requiredKeyId = requiredKeyId;
    }

    public String getName() { return name; }

    public int getID() { return ID; }

    public String getDescription() {
        StringBuilder result = new StringBuilder(description);

        if (this.status == LocationStatus.EXPLORED) {
            result.append(" [ИССЛЕДОВАНО]");
        }

        List<Item> locItems = inventory.getContents();

        if (!locItems.isEmpty()) {
            result.append("\nПредметы в этой локации: ");
            for (int i = 0; i < locItems.size(); i++) {
                result.append(locItems.get(i).getName());

                if (i < locItems.size() - 1) {
                    result.append(", ");
                }
            }
        }
        return result.toString();
    }

    public LocationStatus getStatus() { return status; }

    public List<Location> getPaths() { return Collections.unmodifiableList(paths); }

    public void setDescription(String description) { this.description = description; }

    public void addPath(Location newDestination) {
        if (!paths.contains(newDestination)) {
            paths.add(newDestination);
        }
    }

    public Inventory getInventory() { return inventory; }

    public void setRequiredKeyId(Integer keyId) {
        this.requiredKeyId = keyId;
    }

    public Integer getRequiredKeyId() {
        return requiredKeyId;
    }

    public boolean canPlayerEnter(Player player) {
        if (this.status != LocationStatus.LOCKED) return true;
        if (this.requiredKeyId == null) return true;

        return player.getInventory().hasItem(this.requiredKeyId);
    }

    public void setStatus(LocationStatus newStatus) {
        this.status = newStatus;
    }

    public List<String> getView() { return Collections.unmodifiableList(view); }

    public Location handleSelection(int prompt, Map<Integer, Location> choices) {
        if (choices.containsKey(prompt)) {
            return choices.get(prompt);
        }
        return null;
    }
}

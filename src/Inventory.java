import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Inventory {
    private final List<Item> contents;

    public Inventory(List<Item> contents) {
        this.contents = Objects.requireNonNullElseGet(contents, ArrayList::new);
    }

    public List<Item> getContents() { return Collections.unmodifiableList(contents); }

    public Item getItem() { return contents.getFirst(); }

    public void addItem(Item newItem) {
        contents.add(newItem);
    }

    public void removeItem(Item toRemove) {
        contents.remove(toRemove);
    }

    public Item getItemById(int itemId) {
        for (Item item : contents) {
            if (item.getId() == itemId) {
                return item;
            }
        }
        return null;
    }

    public boolean hasItem(int itemId) {
        for (Item item : contents) {
            if (item.getId() == itemId) return true;
        }
        return false;
    }
}

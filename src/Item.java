import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item {
    protected final String name;
    protected final int id;
    protected final String description;
    boolean isConsumable;

    public Item(String name, int id, String description, boolean isConsumable) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.isConsumable = isConsumable;
    }

    public String getName() { return name; }

    public int getId() { return id; }

    public String getDescription() { return description; }

    public boolean isConsumable() { return isConsumable; }

    public void use(Entity target) {
        System.out.println("С этим предметом нельзя взаимодействовать!");
    }

    public List<String> getSupportedInteractions() {
        return List.of("Осмотреть", "Выбросить");
    }
}

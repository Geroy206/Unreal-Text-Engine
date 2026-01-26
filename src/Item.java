
public class Item {
    protected final String name;
    protected final int id;
    protected final String description;

    public Item(String name, int id, String description) {
        this.name = name;
        this.id = id;
        this.description = description;
    }

    public String getName() { return name; }

    public int getId() { return id; }

    public String getDescription() { return description; }

    public void use(Player player) {
        System.out.println("С этим предметом нельзя взаимодействовать!");
    }
}

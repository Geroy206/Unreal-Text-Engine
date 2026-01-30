import java.util.List;

public class Weapon extends Item {
    private final float damage;

    public Weapon(String name, int id, String description, float damage) {
        super(name, id, description, false);
        this.damage = damage;
    }

    public float giveDamage() { return damage; }

    @Override
    public void use(Entity target) {
        System.out.println("Вы совершаете суицид!");

        target.takeDamage(500);
    }

    @Override
    public List<String> getSupportedInteractions() {
        return List.of("Использовать", "Осмотреть", "Выбросить");
    }
}

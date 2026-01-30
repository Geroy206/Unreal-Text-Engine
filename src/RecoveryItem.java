public class RecoveryItem extends Item {
    public final float hpToRestore;

    public RecoveryItem(String name, int id, String description, boolean isConsumable, float hpToRestore) {
        super(name, id, description, isConsumable);
        this.hpToRestore = hpToRestore;
    }

    @Override
    public void use(Entity target) {
        target.getHeal(hpToRestore);
    }
}

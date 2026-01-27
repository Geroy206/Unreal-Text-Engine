
public class InspectInteract implements ItemInteract {
    @Override
    public String getName() {
        return "Осмотреть";
    }

    @Override
    public void execute(Player player, Item item, GameLoop loop) {
        System.out.println("======== " + item.getName() + " ========");
        System.out.println(item.getDescription() + "\n");
    }
}

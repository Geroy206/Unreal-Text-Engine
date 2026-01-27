
public class UseInteract implements ItemInteract {
    @Override
    public String getName() {
        return "Использовать";
    }

    @Override
    public void execute(Player player, Item item, GameLoop loop) {
        item.use(player);
    }
}

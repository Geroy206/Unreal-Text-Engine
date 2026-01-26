import java.util.List;

public class TakeCommand implements Command {
    @Override
    public boolean execute(Player player) {
        Location loc = player.getCurrentLocation();
        Inventory inv = loc.getInventory();
        List<Item> items = inv.getContents();

        if (!items.isEmpty()) {
            Item item = items.getFirst();
            inv.removeItem(item);
            player.getInventory().addItem(item);
            System.out.println("Вы подобрали: " + item.getName());
        } else {
            System.out.println("В этой локации нет предметов которые можно взять!\n");
        }

        return true;
    }
}

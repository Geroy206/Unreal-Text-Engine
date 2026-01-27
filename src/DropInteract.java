public class DropInteract implements ItemInteract {
    @Override
    public String getName() {
        return "Выбросить";
    }

    @Override
    public void execute(Player player, Item item, GameLoop loop) {
        player.getInventory().removeItem(item);
        player.getCurrentLocation().getInventory().addItem(item);
        System.out.println("Вы выбросили " + item.getName());

        loop.setGameState(GameState.IN_INVENTORY);
    }
}

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GameLoop {
    private boolean isRunning = true;
    private GameState gameState = GameState.EXPLORING;
    private final GameInput INPUT = new GameInput();
    private final GameManager gameManager = new GameManager();
    private final Map<String, Command> commands = new HashMap<>();
    private final Map<String, ItemInteract> itemInteract = new HashMap<>();
    private Item itemSelected;

    public GameLoop() {
        commands.put("exit", new ExitCommand());
        commands.put("take", new TakeCommand());
        commands.put("inv", new InventoryCommand());
        commands.put("look", new LookAroundCommand());

        itemInteract.put("Использовать", new UseInteract());
        itemInteract.put("Осмотреть", new InspectInteract());
        itemInteract.put("Выбросить", new DropInteract());
    }

    public GameState getGameState() { return gameState; }

    public void setGameState(GameState newState) { gameState = newState; }

   public void loop(Player player) {

       while (isRunning) {
           if (gameState == GameState.EXPLORING) {
               Location playerLocation = player.getCurrentLocation();
               System.out.println("======== " + player.getLocName() + " ========");
               System.out.println("Описание локации: " + player.getLocDescription());
               Map<Integer, Location> currentChoices = gameManager.handler(playerLocation);

               String prompt = INPUT.getInput();
               String[] parts = prompt.split(" ", 2);
               String commandName = parts[0];
               String argument = (parts.length > 1) ? parts[1] : "";

               Command cmd = commands.get(commandName);

               if (cmd != null) {
                   this.isRunning = cmd.execute(player, this, argument);
               } else {
                   try {
                       int choice = Integer.parseInt(prompt);
                       gameManager.choicesHandler(choice, player, currentChoices);
                   } catch (NumberFormatException e) {
                       System.out.println("Некорректный ввод! Введите номер или команду.\n");
                   }
               }
           }
           else if (gameState == GameState.IN_INVENTORY) {
               Map<Integer, Item> itemChoices = gameManager.createItemChoices(player);

               String prompt = INPUT.getInput();
               try {
                   int choice = Integer.parseInt(prompt);

                   if (choice == 0) {
                       gameState = GameState.EXPLORING;
                   } else if (itemChoices.containsKey(choice)) {
                       itemSelected = itemChoices.get(choice);
                       gameState = GameState.ITEM_INTERACTION;
                   } else {
                       System.out.println("Нет такого предмета.");
                   }
               } catch (NumberFormatException e) {
                   System.out.println("Введите номер предмета или 0 для выхода.");
               }
           }
           else if (gameState == GameState.ITEM_INTERACTION) {
               Map<Integer, String> ItemActionChoices = gameManager.createItemActionChoices(itemSelected);

               String prompt = INPUT.getInput();
               try {
                   int choice = Integer.parseInt(prompt);

                   if (choice == 0) {
                        gameState = GameState.IN_INVENTORY;

                   } else if (ItemActionChoices.containsKey(choice)) {
                       ItemInteract interact = itemInteract.get(ItemActionChoices.get(choice));

                       interact.execute(player, itemSelected, this);
                   } else {
                       System.out.println("Нет такого действия");
                   }
               } catch (NumberFormatException e) {
                   System.out.println("Введите номер предмета или 0 для выхода.");
               }
           }
           else if (gameState == GameState.IN_COMBAT) {
               System.out.println("Временно не реализовано.");
           }

           if (player.getHp() <= 0) {
               isRunning = false;
           }
       }

       INPUT.closeScanner();
       System.out.println("\n Завершение игры \n");
   }
}

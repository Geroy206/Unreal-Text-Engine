import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GameLoop {
    private boolean isRunning = true;
    GameState gameState = GameState.EXPLORING;
    GameInput INPUT = new GameInput();
    GameManager gameManager = new GameManager();
    private final Map<String, Command> commands = new HashMap<>();

    public GameLoop() {
        commands.put("exit", new ExitCommand());
        commands.put("take", new TakeCommand());
    }

   public void loop(Player player, World world) {

       while (isRunning) {
           Location playerLocation = player.getCurrentLocation();
           System.out.println(player.getLocName());
           System.out.println("Описание локации: " + player.getLocDescription());
           Map<Integer, Location> currentChoices = gameManager.handler(playerLocation);

           if (player.getHp() <= 0) {
                isRunning = false;
           }

           String prompt = INPUT.getInput();


           if (gameState == GameState.EXPLORING) {
               Command cmd = commands.get(prompt);

               if (cmd != null) {
                   this.isRunning = cmd.execute(player);
               } else {
                   try {
                       int choice = Integer.parseInt(prompt);
                       gameManager.choicesHandler(choice, player, currentChoices);
                   } catch (NumberFormatException e) {
                       System.out.println("Некорректный ввод! Введите номер или команду.\n");
                   }
               }
           }

       }

       INPUT.closeScanner();
       System.out.println("\n Завершение игры \n");
   }
}

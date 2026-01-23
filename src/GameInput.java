import java.util.Locale;
import java.util.Scanner;


public class GameInput {
    private final Scanner game_input = new Scanner(System.in);

    public String getInput() {
        System.out.print("> ");
        String prompt = game_input.nextLine().trim();

        return prompt.toLowerCase(Locale.ROOT);
    }

    public void closeScanner() {
        game_input.close();
    }
}

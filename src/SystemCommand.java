import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SystemCommand {
    private static final List<String> commandList = Arrays.asList("выход");

    private static boolean gameExit() {
        return false;
    }

    public static boolean execute(String prompt) {

        if (Objects.equals(prompt, "выход")) {
            return gameExit();
        } else {
            return true;
        }
    }

    public static boolean isCommand(String prompt) {
        return commandList.contains(prompt);
    }
}

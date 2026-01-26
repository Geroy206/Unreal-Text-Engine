import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LookAroundCommand implements Command{
    @Override
    public boolean execute(Player player, GameLoop loop) {
        List<String> look = player.getCurrentLocation().getView();

        if (look.isEmpty()) {
            System.out.println("В данной локации нечего осматривать\n");
        }
        else {
            String randomElement = look.get(ThreadLocalRandom.current().nextInt(look.size()));

            System.out.println("Осмотр локации: " + randomElement + "\n");
        }
        return true;
    }
}

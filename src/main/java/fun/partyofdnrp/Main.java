package fun.partyofdnrp;

import fun.partyofdnrp.commands.Elevator;
import fun.partyofdnrp.commands.Help;
import fun.partyofdnrp.commands.Ping;
import io.github.cdimascio.dotenv.Dotenv;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory("./misc")
                .load();

        String token = dotenv.get("BOT_TOKEN");
        String prefix = dotenv.get("BOT_PREFIX");

        DiscordApi api = new DiscordApiBuilder()
                .setToken(token)
                .addServerBecomesAvailableListener(event -> {
                    System.out.println("Loaded " + event.getServer().getName());
                })
                .addMessageCreateListener(new Ping(prefix))
                .addMessageCreateListener(new Help(prefix))
                .addMessageCreateListener(new Elevator(prefix))
                .login()
                .join();
    }
}

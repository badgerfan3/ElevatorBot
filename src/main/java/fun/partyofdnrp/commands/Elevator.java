package fun.partyofdnrp.commands;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.ServerTextChannelBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Elevator implements MessageCreateListener {
    String prefix = "!";
    public Elevator(String prefix) {
        this.prefix = prefix;
    }
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String message = event.getMessageContent();
        String begin = prefix+"elevator";
        String arg = message.substring(begin.length());
        if(message.substring(0,begin.length()).equalsIgnoreCase(prefix+"elevator") && !arg.equals("")){
            try {
                arg = arg.substring(1);
                int floor = Integer.parseInt(arg);
                if(floor >= 0 && floor <= 100) {
                    Server server = event.getServer().get();
                    System.out.println("Creating floor " + floor);
                    ServerTextChannel channel = new ServerTextChannelBuilder(server)
                            .setName("Floor " + floor)
                            .create()
                            .join();
                } else {
                    System.out.println("Make sure the floor is between 0 and 100, inclusive!");
                }
            } catch (NumberFormatException e){
                System.out.println("\""+arg+"\"");
                System.out.println("Make sure this is an actual floor number!");
            }
        }
    }
}

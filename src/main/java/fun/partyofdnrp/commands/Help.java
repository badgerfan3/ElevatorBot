package fun.partyofdnrp.commands;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.Iterator;

public class Help implements MessageCreateListener {
    String prefix = "!";
    public Help(String prefix){
        this.prefix = prefix;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        StringBuilder sendVal = new StringBuilder();
        String message = event.getMessageContent();
        if(message.equalsIgnoreCase(prefix+"help")) {
            String JsonData = readFile("help.json");
            JSONObject jobj = new JSONObject(JsonData);
            for(Iterator<String> it = jobj.keys(); it.hasNext(); ){
                String key = it.next();
                if(sendVal.isEmpty()) {
                    sendVal.append(key).append(" : ").append(jobj.get(key));
                } else {
                    sendVal.append("\n").append(key).append(" : ").append(jobj.get(key));
                }
            }
        } else {
            String begin = prefix+"help";
            String arg =  message.substring(begin.length());
            if(message.substring(0, begin.length()).equalsIgnoreCase(prefix+"help")) {
                String JsonData = readFile("help.json");
                JSONObject jobj = new JSONObject(JsonData);
                try {
                    arg = arg.substring(1);
                    String help_desc = jobj.get(arg).toString();
                    sendVal.append(arg).append(" : ").append(help_desc);
                } catch (JSONException e) {
                    sendVal.append(arg).append(" is not a valid command!");
                }
            }
        }
        event.getChannel().sendMessage(String.valueOf(sendVal));
    }

    public static String readFile(String file){
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null){
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

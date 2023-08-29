import java.util.ArrayList;

public abstract class ChatRoom extends Thread {

    protected static ArrayList<ChatServerTh> list;

    public void addClient(ChatServerTh chatServerTh) {
        list.add(chatServerTh);
    }

    public void delClient(ChatServerTh chatServerTh) {
        list.remove(chatServerTh);
    }

    public void sendMessage(String message, String name) {
        for (ChatServerTh th : list) {
            if (th.getUserName().equals(name)) {
                th.writeln(th.getUserName() + ": " + message);
            }
        }
    }

    public void sendMessageAll(String message) {
        for (ChatServerTh th : list) {
            th.writeln(message);
        }
    }
}

import java.util.ArrayList;

public class MafiaRoom {

    private ArrayList<ChatServerTh> list;

    public MafiaRoom() {
        this.list = new ArrayList<>();
    }

    public void addMafia(ChatServerTh chatServerTh) {
        list.add(chatServerTh);
    }

    public void delMafia(ChatServerTh chatServerTh) {
        list.remove(chatServerTh);
    }

    public void mafiaChat(String message) {
        for (ChatServerTh c : list) {
            c.writeln(message);
        }
    }

}

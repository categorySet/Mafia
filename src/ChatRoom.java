import java.net.Socket;
import java.util.*;

/**
 * 플레이어들의 상태와 대화를 담당하는 클래스
 */
public class ChatRoom {

    private ArrayList<ChatServerTh> list;
    private ArrayList<ChatServerTh> deadList;

    public ChatRoom() {
        this.list = new ArrayList<>();
    }

    public void addClient(ChatServerTh chatServerTh) {
        list.add(chatServerTh);
    }

    public void delClient(ChatServerTh chatServerTh) {
        list.remove(chatServerTh);
    }

    public void kill (String name) {
        for (ChatServerTh c : list) {
            if (c.getUserName().equals(name)) {
                delClient(c);
                c.writeln("당신은 마피아에 의해 죽었습니다.");
            }
        }
    }

    public void sendMessageAll(String message) {
        for (ChatServerTh th : list) {
            th.writeln(message);
        }
    }

    public void sendMessageExceptMe(String message, String name) {
        for (ChatServerTh th : list) {
            if (!th.getUserName().equals(name)) {
                th.writeln(th.getUserName() + ": " + message);
            }
        }
    }

    public void sendMessage(String message, String name) {
        for (ChatServerTh th : list) {
            if (th.getUserName().equals(name)) {
                th.writeln(th.getUserName() + ": " + message);
            }
        }
    }

    public int getListSize() {
        return list.size();
    }

    public Roles getRoleByName(String name) {
        for (ChatServerTh th : list) {
            if (th.getUserName().equals(name)) {
                return th.getRoles();
            }
        }

        return null;
    }

    public void setKilled(String name) {
        for (ChatServerTh th : list) {
            if (th.getUserName().equals(name)) {
                delClient(th);
                deadList.add(th);
            }
        }
    }

}
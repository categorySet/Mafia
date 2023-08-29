import java.util.ArrayList;

/**
 * 채팅방 클래스. 게임방을 위한 추상 클래스
 * @author categorySet
 */
public abstract class ChatRoom extends Thread {

    protected static ArrayList<ChatServerTh> list;

    public void addClient(ChatServerTh chatServerTh) {
        list.add(chatServerTh);
    }

    public void delClient(ChatServerTh chatServerTh) {
        list.remove(chatServerTh);
    }

    /**
     * 특정 유저에게 귓속말
     * @param message 전달할 메시지
     * @param name 전달할 유저 이름
     */
    public void sendMessage(String message, String name) {
        for (ChatServerTh th : list) {
            if (th.getUserName().equals(name)) {
                th.writeln(th.getUserName() + ": " + message);
            }
        }
    }

    /**
     * 현재 방에 있는 유저 전부에게 메시지 전달
     * @param message 전달할 메시지
     */
    public void sendMessageAll(String message) {
        for (ChatServerTh th : list) {
            th.writeln(message);
        }
    }
}

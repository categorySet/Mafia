import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Room {

    private ArrayList<ChatServerTh> list;
    private ArrayList<ChatServerTh> dead;

    private HashMap<String, Integer> vote;

    private static boolean isDay;

    public Room() {
        this.list = new ArrayList<>();
        this.dead = new ArrayList<>();

        this.vote = new HashMap<>();
    }

    public void addClient(ChatServerTh chatServerTh) {
        list.add(chatServerTh);
    }

    public void delClient(ChatServerTh chatServerTh) {
        list.remove(chatServerTh);
    }

    public void kill(String name) {
        for (ChatServerTh c : list) {
            if (c.getName().equals(name)) {
                list.remove(c);
                dead.add(c);
            }
        }
    }

    public void vote(String name) {
        for (ChatServerTh c : list) {
            if (vote.containsKey(c.getName())) {
                vote.put(c.getName(), vote.get(c.getName()).intValue() + 1);
            } else {
                vote.put(c.getName(), 1);
            }
        }
    }

    public void clearVote() {
        vote.clear();
    }

    public Role scan(String name) {
        for (ChatServerTh c : list) {
            if (c.getName().equals(name)) {
                return c.getRole();
            }
        }

        return null;
    }

    public static boolean isDay() {
        return isDay;
    }

    public void deadByVote() {
        Map.Entry<String, Integer> maxEntity = vote.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).orElseGet(null);

        kill(maxEntity.getKey());
    }

    public void sendMessageAll(String message) {
        for (ChatServerTh th : list) {
            th.write(message);
        }
    }

}

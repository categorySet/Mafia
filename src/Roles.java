import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public abstract class Roles {

    public ChatRoom chatRoom;
    public DayTimer dayTimer;
    public boolean dead;
    private static HashMap<String, Integer> voteMap;
    public static String roleName;

    public Roles(final ChatRoom chatRoom, final DayTimer dayTimer) {
        this.chatRoom = chatRoom;
        this.dayTimer = dayTimer;
    }

    public synchronized void vote(String name) {
        if (voteMap.containsKey(name)) {
            voteMap.put(name, voteMap.get(name) + 1);
        } else {
            voteMap.put(name, 1);
        }
    }

    public synchronized String getResult() {
        Map.Entry<String, Integer> entry = voteMap.entrySet().stream().max(Comparator.comparingInt(x -> x.getValue())).orElseGet(null);

        if (entry != null) {
            return entry.getKey();
        } else {
            return null;
        }
    }

}

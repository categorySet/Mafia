import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public abstract class Roles {

    public MafiaRoom mafiaRoom;
    public boolean dead;
    private static HashMap<String, Integer> voteMap;
    public static String roleName;

    public boolean voted;

    public Roles(final MafiaRoom mafiaRoom) {
        this.mafiaRoom = mafiaRoom;

        voteMap = new HashMap<>();
    }

    public static HashMap<String, Integer> getVoteMap() {
        return voteMap;
    }

    public synchronized void vote(String name) {
        if (voteMap.containsKey(name)) {
            voteMap.put(name, voteMap.get(name) + 1);
        } else {
            voteMap.put(name, 1);
        }

        voted = true;
    }

    public static synchronized String getResult() {
        Map.Entry<String, Integer> entry = voteMap.entrySet().stream().max(Comparator.comparingInt(x -> x.getValue())).orElse(null);

        if (entry != null) {
            return entry.getKey();
        } else {
            return null;
        }
    }

}

import java.util.*;

public class Room {

    private ArrayList<ChatServerTh> list;
    private ArrayList<ChatServerTh> dead;

    private HashMap<String, Integer> vote;

    private boolean isDay = true;

    private boolean timerStart = false;

    private boolean mafiaKilled = false;

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

    public boolean isTimerStart() {
        return timerStart;
    }

    public void setTimerStart(boolean timerStart) {
        this.timerStart = timerStart;
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

    public boolean isDay() {
        return isDay;
    }

    public void deadByVote() {
        Map.Entry<String, Integer> maxEntity = vote.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).orElseGet(null);

        kill(maxEntity.getKey());
    }

    public void sendMessageAll(String message) {
        for (ChatServerTh th : list) {
            th.writeln(message);
        }
    }

    public void sendMessage(String message, String name) {
        for (ChatServerTh th : list) {
            if (th.getName().equals(name)) {
                th.writeln(th.getName() + ": " + message);
            }
        }
    }

    public boolean isMafiaKilled() {
        return mafiaKilled;
    }

    public void setMafiaKilled(final boolean mafiaKilled) {
        this.mafiaKilled = mafiaKilled;
    }

    public void switchDay() {
        if (isDay) {
            sendMessageAll("밤이 되었습니다.");
            mafiaKilled = false;
            isDay = false;
        } else {
            sendMessageAll("낮이 되었습니다.");
            isDay = true;
        }
    }

}
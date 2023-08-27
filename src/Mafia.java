public class Mafia extends Roles {

    public static boolean killed = false;
    public static String roleName = "Mafia";
    public static String nextKill = "";

    public Mafia(final ChatRoom chatRoom) {
        super(chatRoom);
    }

    public void kill(String name) {
        nextKill = name;
        killed = true;
    }

    public void clear() {
        killed = false;
    }

    @Override
    public String toString() {
        return "Mafia";
    }
}

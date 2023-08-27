public class Doctor extends Roles {

    public static boolean saved;

    public Doctor(final ChatRoom gameRoom, final DayTimer dayTimer) {
        super(gameRoom, dayTimer);
    }

    public void save(String name) {
        if (Mafia.nextKill == name) {
            Mafia.nextKill = null;
        }
    }

    @Override
    public String toString() {
        return "Doctor";
    }
}

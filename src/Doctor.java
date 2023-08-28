public class Doctor extends Citizen {

    public static boolean saved;

    public Doctor(final ChatRoom gameRoom) {
        super(gameRoom);
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

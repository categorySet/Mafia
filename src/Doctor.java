public class Doctor extends Citizen {

    public static boolean saved;
    public static String savePerson;

    public Doctor(final ChatRoom gameRoom) {
        super(gameRoom);
    }

    public void save(String name) {
        savePerson = name;
    }

    @Override
    public String toString() {
        return "Doctor";
    }
}

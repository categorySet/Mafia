public class Doctor extends Citizen {

    public static boolean saved;
    public static String savePerson;

    public Doctor(final MafiaRoom mafiaRoom) {
        super(mafiaRoom);
    }

    public void save(String name) {
        savePerson = name;
    }

    @Override
    public String toString() {
        return "Doctor";
    }
}

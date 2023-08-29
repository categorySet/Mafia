public class Police extends Citizen {

    public static boolean scanned;

    public Police(final MafiaRoom mafiaRoom) {
        super(mafiaRoom);
    }

    public String scan(String name) {
        return mafiaRoom.getRoleByName(name).toString();
    }

    @Override
    public String toString() {
        return "Police";
    }
}

public class Citizen extends Roles {

    private boolean voted = false;

    public Citizen(final MafiaRoom mafiaRoom) {
        super(mafiaRoom);
    }

    @Override
    public String toString() {
        return "Citizen";
    }
}

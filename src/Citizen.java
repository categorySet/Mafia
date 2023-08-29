public class Citizen extends Roles {

    private boolean voted = false;

    public Citizen(final ChatRoom chatRoom) {
        super(chatRoom);
    }

    @Override
    public String toString() {
        return "Citizen";
    }
}

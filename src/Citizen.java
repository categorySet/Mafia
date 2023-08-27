public class Citizen extends Roles {

    private boolean voted = false;

    public Citizen(final ChatRoom chatRoom) {
        super(chatRoom);
    }

    public void vote(String name) {
        if (DayTimer.isDay()) {
            vote(name);
            voted = true;
        }
    }

    @Override
    public String toString() {
        return "Citizen";
    }
}

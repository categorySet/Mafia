public class Citizen extends Roles {

    private boolean voted = false;

    public Citizen(final ChatRoom chatRoom, final DayTimer dayTimer) {
        super(chatRoom, dayTimer);
    }

    public void vote(String name) {
        if (dayTimer.isDay()) {
            vote(name);
            voted = true;
        }
    }

    @Override
    public String toString() {
        return "Citizen";
    }
}

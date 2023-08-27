public class Citizen extends Roles {

    public Citizen(final ChatRoom chatRoom, final DayTimer dayTimer) {
        super(chatRoom, dayTimer);
    }

    public void vote(String name) {
        if (dayTimer.isDay()) {
            vote(name);
        }
    }

    @Override
    public String toString() {
        return "Citizen";
    }
}

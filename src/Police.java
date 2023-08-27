public class Police extends Citizen {

    public Police(final ChatRoom chatRoom, final DayTimer dayTimer) {
        super(chatRoom, dayTimer);
    }

    public String scan(String name) {
        return chatRoom.getRoleByName(name).toString();
    }

    @Override
    public String toString() {
        return "Police";
    }
}

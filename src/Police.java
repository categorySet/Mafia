public class Police extends Citizen {

    public static boolean scaned;

    public Police(final ChatRoom chatRoom) {
        super(chatRoom);
    }

    public String scan(String name) {
        return chatRoom.getRoleByName(name).toString();
    }

    @Override
    public String toString() {
        return "Police";
    }
}

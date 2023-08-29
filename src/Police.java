/**
 * 경찰. 밤에 능력을 사용하면 한 사람의 직업을 알 수 있음
 * @author categorySet
 */
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

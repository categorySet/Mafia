/**
 * 마피아. 밤에 능력을 사용하면 죽일 사람 한 명을 선택할 수 있음
 * @author categorySet
 */
public class Mafia extends Roles {

    public static boolean killed = false;
    public static String roleName = "Mafia";
    public static String nextKill = "";

    public Mafia(final MafiaRoom mafiaRoom) {
        super(mafiaRoom);
    }

    /**
     * @param name 살해할 사람 이름
     */
    public void kill(String name) {
        nextKill = name;
        killed = true;
    }

    @Override
    public String toString() {
        return "Mafia";
    }
}

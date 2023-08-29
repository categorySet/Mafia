/**
 * 의사. 시민편으로 밤에 마피아에게 지목된 유저에게 능력을 사용하면 살릴 수 있음
 * @author categorySet
 */
public class Doctor extends Citizen {

    public static boolean saved;
    public static String savePerson;

    public Doctor(final MafiaRoom mafiaRoom) {
        super(mafiaRoom);
    }

    public void save(String name) {
        savePerson = name;
    }

    @Override
    public String toString() {
        return "Doctor";
    }
}

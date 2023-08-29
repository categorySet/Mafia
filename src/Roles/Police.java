package Roles;

import room.MafiaRoom;

/**
 * 경찰. {@link Citizen}편으로 밤에 유저에게 능력을 사용하면 역할을 알 수 있음
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

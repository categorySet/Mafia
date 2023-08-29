package Roles;

import room.MafiaRoom;

/**
 * 시민. 낮에 투표할 수 있는 능력밖에 없음
 * @author categorySet
 */
public class Citizen extends Roles {

    public Citizen(final MafiaRoom mafiaRoom) {
        super(mafiaRoom);
    }

    @Override
    public String toString() {
        return "Citizen";
    }
}

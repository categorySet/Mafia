import java.util.Timer;

/**
 * 시간 관리
 */
public class DayTimer extends Thread {

    private Timer timer = new Timer();
    private static boolean isDay = false;
    private int time = 30;

    public boolean dayTimerflag = true;

    public static boolean isDay() {
        return isDay;
    }

    private ChatRoom room;

    public DayTimer(final ChatRoom room) {
        this.room = room;
    }

    @Override
    public void run() {
        while (dayTimerflag) {
            while (time > 0) {
                time--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (isDay) {
                String voteResult = Roles.getResult();
                if (voteResult != null) {
                    room.killByVoting(voteResult);
                }

                isDay = false;
                room.sendMessageAll("밤이 되었습니다.");

                Mafia.killed = false;
                Doctor.saved = false;
                Police.scaned = false;

                Mafia.nextKill = null;

            } else {
                if (Mafia.nextKill == null) {
                    room.sendMessageAll("평화로운 밤이 지나갔습니다.");
                } else if (Mafia.nextKill.equals(Doctor.savePerson)) {
                    room.sendMessageAll("의사가 " + Doctor.savePerson + "님을 구했습니다.");
                } else {
                    room.kill(Mafia.nextKill);
                }

                isDay = true;
                room.sendMessageAll("낮이 되었습니다.");

                Roles.getVoteMap().clear();

                for (ChatServerTh c : room.getList()) {
                    if (c.getRoles() != null) {
                        c.getRoles().voted = false;
                    }
                }

            }

            time = 30;
        }
    }
}

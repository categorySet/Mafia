import java.util.Timer;

/**
 * 시간 관리
 */
public class DayTimer extends Thread {

    private Timer timer = new Timer();
    private boolean isDay = false;
    private int time = 30;

    public boolean isDay() {
        return isDay;
    }

    private ChatRoom room;

    public DayTimer(final ChatRoom room) {
        this.room = room;
    }

    @Override
    public void run() {
        while (true) {
            while (time > 0) {
                time--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (isDay) {
                isDay = false;
                System.out.println("밤이 되었습니다.");
                room.sendMessageAll("밤이 되었습니다.");

                Mafia.killed = false;
                Doctor.saved = false;
                Police.scaned = false;

            } else {
                Roles.getVoteMap().clear();

                for (ChatServerTh c : room.getList()) {
                    if (c.getRoles() != null) {
                        c.getRoles().voted = false;
                    }
                }

                isDay = true;
                System.out.println("낮이 되었습니다.");
                room.sendMessageAll("낮이 되었습니다.");
            }

            time = 30;
        }
    }
}

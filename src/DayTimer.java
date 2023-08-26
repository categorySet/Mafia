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
            } else {
                isDay = true;
                System.out.println("낮이 되었습니다.");
            }

            time = 30;
        }
    }
}

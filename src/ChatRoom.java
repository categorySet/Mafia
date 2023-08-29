import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 플레이어들의 상태와 대화를 담당하는 클래스
 */
public class ChatRoom extends Thread {

    public static final int MIN_PERSON = 5;
    private static DayTimer dayTimer = null;

    private static ArrayList<ChatServerTh> list;
    private ArrayList<ChatServerTh> deadList;

    public static int selected = 0;

    private int countMafia = 0;
    private int countCitizen = 0;

    private static List<String> winners;

    public ChatRoom() {
        this.list = new ArrayList<>();
    }

    public void addClient(ChatServerTh chatServerTh) {
        list.add(chatServerTh);
    }

    public void delClient(ChatServerTh chatServerTh) {
        list.remove(chatServerTh);
    }

    public void kill(String name) {
        for (int i = list.size() - 1; i >= 0; i--) {
            ChatServerTh c = list.get(i);

            if (c.getUserName().equals(name)) {
                delClient(c);
                c.writeln("당신은 마피아에 의해 죽었습니다.");
                sendMessageAll("악랄한 마피아에 의해 " + name + "님이 죽었습니다.");
            }
        }
    }

    public void killByVoting(String name) {
        synchronized (list) {
            for (int i = list.size() - 1; i >= 0; i--) {
                ChatServerTh c = list.get(i);

                if (c.getUserName().equals(name)) {
                    delClient(c);
                    c.writeln("투표에 의해 죽었습니다..");
                    sendMessageAll("투표에 의해 " + name + "님이 죽었습니다.");
                }
            }
        }
    }

    public void sendMessageAll(String message) {
        for (ChatServerTh th : list) {
            th.writeln(message);
        }
    }

    public void sendMessageAll(String message, RolesAdapter rolesAdapter, ChatServerTh chatServerTh) {
        if (DayTimer.isDay()) {
            for (ChatServerTh th : list) {
                if (th == chatServerTh) {
                    Pattern pattern = Pattern.compile("/vote (\\w+)");
                    Matcher matcher = pattern.matcher(message);

                    if (matcher.matches() && DayTimer.isDay()) {
                        if (!rolesAdapter.getRoles().voted) {
                            rolesAdapter.getRoles().vote(matcher.group(1));
                            th.writeln("투표되었습니다.");
                            rolesAdapter.getRoles().voted = true;
                        }
                    } else {
                        sendMessageAll("[" + th.getUserName() + "] " + message);
                    }
                }
            }
        } else {
            for (ChatServerTh th : list) {
                if (th == chatServerTh) {
                    Pattern pattern = Pattern.compile("/use (\\w+)");
                    Matcher matcher = pattern.matcher(message);

                    if (matcher.matches() && !DayTimer.isDay()) {
                        th.writeln(rolesAdapter.useAbllitity(matcher.group(1)));
                    }
                }
            }
        }
    }


    public void sendMessageExceptMe(String message, String name) {
        for (ChatServerTh th : list) {
            if (!th.getUserName().equals(name)) {
                th.writeln(th.getUserName() + ": " + message);
            }
        }
    }

    public void sendMessage(String message, String name) {
        for (ChatServerTh th : list) {
            if (th.getUserName().equals(name)) {
                th.writeln(th.getUserName() + ": " + message);
            }
        }
    }

    public int getListSize() {
        return list.size();
    }

    public Roles getRoleByName(String name) {
        for (ChatServerTh th : list) {
            if (th.getUserName().equals(name)) {
                return th.getRoles();
            }
        }

        return null;
    }

    public ArrayList<ChatServerTh> getList() {
        return list;
    }

    public void setKilled(String name) {
        for (ChatServerTh th : list) {
            if (th.getUserName().equals(name)) {
                delClient(th);
                deadList.add(th);
            }
        }
    }

    @Override
    public void run() {
        while (selected < MIN_PERSON) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        sendMessageAll("=== 게임 시작 ===");
        sendMessageAll("밤이 찾아옵니다.");

        if (dayTimer == null) {
            dayTimer = new DayTimer(this);

            dayTimer.start();
        }

        boolean flag = true;
        while (flag) {
            countMafia = 0;
            countCitizen = 0;

            synchronized (list) {
                for (int i = list.size() - 1; i >= 0; i--) {
                    ChatServerTh c = list.get(i);

                    if (c.getRoles() instanceof Mafia) {
                        countMafia++;
                    } else if (c.getRoles() instanceof Citizen) {
                        countCitizen++;
                    }
                }
            }

            if (countMafia >= countCitizen || countMafia == 0) {
                flag = false;
                System.out.println(countMafia + " " + countCitizen);
            }
        }

        winners = new ArrayList<>();
        if (countMafia == 0) {
            sendMessageAll("시민이 승리했습니다.");
            for (ChatServerTh c : list) {
                if (c.getRoles() instanceof Citizen) {
                    winners.add(c.getUserName());
                }
            }
        } else if (countMafia >= countCitizen) {
            sendMessageAll("마피아가 승리했습니다.");
            for (ChatServerTh c : list) {
                if (c.getRoles() instanceof  Mafia) {
                    winners.add(c.getUserName());
                }
            }
        }

        DayTimer.dayTimerflag = false;
        System.out.println("승리: " + winners);

    }
}
package room;

import Roles.Citizen;
import Roles.Mafia;
import Roles.Roles;
import Roles.RolesAdapter;
import server.ChatServerTh;
import server.DayTimer;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 플레이어들와 게임의 상태, 대화를 담당하는 클래스.
 * @author categorySet
 */
public class MafiaRoom extends ChatRoom {

    public static final int MIN_PERSON = 5;
    private static DayTimer dayTimer = null;


    private ArrayList<ChatServerTh> deadList;

    public static int selected = 0;

    private int countMafia = 0;
    private int countCitizen = 0;

    private static List<String> winners;

    public MafiaRoom() {
        super.list = new ArrayList<ChatServerTh>();
        this.deadList = new ArrayList<>();
    }



    public void setClientDead(ChatServerTh chatServerTh) {
        for (ChatServerTh c : list) {
            if (c.equals(chatServerTh)) {
                c.setAlivePerson(false);
            }

            deadList.add(c);
        }
    }

    public void kill(String name) {
        for (int i = list.size() - 1; i >= 0; i--) {
            ChatServerTh c = list.get(i);

            if (c.getUserName().equals(name)) {
                setClientDead(c);
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
                    setClientDead(c);
                    c.writeln("투표에 의해 죽었습니다..");
                    sendMessageAll("투표에 의해 " + name + "님이 죽었습니다.");
                }
            }
        }
    }

    public void sendMessageAll(String message) {
        for (ChatServerTh th : list) {
            if (th.isAlivePerson()) {
                th.writeln(message);
            }
        }
    }

    public void sendMessageAll(String message, RolesAdapter rolesAdapter, ChatServerTh chatServerTh) {
        if (chatServerTh.isAlivePerson()) {
            if (dayTimer.isDay()) {
                for (ChatServerTh th : list) {
                    if (th == chatServerTh) {
                        Pattern pattern = Pattern.compile("/vote (\\w+)");
                        Matcher matcher = pattern.matcher(message);

                        if (matcher.matches() && dayTimer.isDay()) {
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

                        if (matcher.matches() && !dayTimer.isDay()) {
                            th.writeln(rolesAdapter.useAbility(matcher.group(1)));
                        }
                    }
                }
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

            dayTimer.setDaemon(true);

            dayTimer.start();
        }

        boolean flag = true;
        while (flag) {
            countMafia = 0;
            countCitizen = 0;

            synchronized (list) {
                for (int i = list.size() - 1; i >= 0; i--) {
                    ChatServerTh c = list.get(i);

                    if (c.getRoles() instanceof Mafia && c.isAlivePerson()) {
                        countMafia++;
                    } else if (c.getRoles() instanceof Citizen && c.isAlivePerson()) {
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

        dayTimer.dayTimerflag = false;
        dayTimer.interrupt();

        System.out.println("승리: " + winners);

    }
}
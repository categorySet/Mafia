import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatServerTh extends Thread {

    private static final int MIN_PERSON = 5;
    private static DayTimer dayTimer = null;

    private ChatRoom gameRoom;

    private Socket socket;
    private String userName;
    private BufferedReader reader;
    private PrintWriter writer;

    private RolesAdapter rolesAdapter;

    public ChatServerTh(Socket socket, ChatRoom gameRoom) {
        this.socket = socket;
        this.gameRoom = gameRoom;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRoles(Roles roles) {
        this.rolesAdapter = new RolesAdapter(roles);
    }

    public String getUserName() {
        return userName;
    }

    public void writeln(String message) {
        writer.println(message);
        writer.flush();
    }

    public void write(String message) {
        writer.print(message);
        writer.flush();
    }

    public Roles getRoles() {
        return rolesAdapter.getRoles();
    }

    @Override
    public void run() {
        try {
            userName = reader.readLine();
            System.out.println("name = " + userName);

            writeln("===직업 선택===");
            writeln("0. 마피아 1. 시민 2. 의사 3. 경찰");

            int select = -1;
            do {
                try {
                    String s = reader.readLine();
                    select = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    continue;
                }
            } while (!(0 <= select && select < Role.values().length));

            switch (select) {
                case 0:
                    rolesAdapter = new RolesAdapter(new Mafia(gameRoom, dayTimer));
                    break;
                case 1:
                    rolesAdapter = new RolesAdapter(new Citizen(gameRoom, dayTimer));
                    break;
                case 2:
                    rolesAdapter = new RolesAdapter(new Doctor(gameRoom, dayTimer));
                    break;
                case 3:
                    rolesAdapter = new RolesAdapter(new Police(gameRoom, dayTimer));
                    break;
            }

            writeln("당신은 " + rolesAdapter.toString() + "입니다.");
            if (rolesAdapter.toString().equals("Mafia")) {
                writeln("'/kill 이름' 명령어로 밤에 한명을 죽일 수 있습니다.");
                writeln("마피아와 시민이 같은 수가 되면 승리합니다.");
            }

            gameRoom.sendMessageAll(userName + "님이 입장하셨습니다.");

            while (gameRoom.getListSize() < MIN_PERSON) {
                String str = reader.readLine();

                gameRoom.sendMessageExceptMe(str, this.userName);
            }

            gameRoom.sendMessageAll("=== 게임 시작 ===");

            if (dayTimer == null) {
                dayTimer = new DayTimer();

                dayTimer.start();
            }

            while (true) {
                while (dayTimer.isDay()) {

                }

                while (!dayTimer.isDay()) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatServerTh extends Thread {

    private ChatRoom gameRoom;

    private Socket socket;
    private String userName;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean alivePerson = true;

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

    public boolean isAlivePerson() {
        return alivePerson;
    }

    public void setAlivePerson(boolean alivePerson) {
        this.alivePerson = alivePerson;
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
                    rolesAdapter = new RolesAdapter(new Mafia(gameRoom));
                    break;
                case 1:
                    rolesAdapter = new RolesAdapter(new Citizen(gameRoom));
                    break;
                case 2:
                    rolesAdapter = new RolesAdapter(new Doctor(gameRoom));
                    break;
                case 3:
                    rolesAdapter = new RolesAdapter(new Police(gameRoom));
                    break;
                default:
                    rolesAdapter = new RolesAdapter(new Citizen(gameRoom));
            }

            writeln("당신은 " + rolesAdapter.toString() + "입니다.");
            if (rolesAdapter.toString().equals("Mafia")) {
                writeln("'/use 이름' 명령어로 밤에 한명을 죽일 수 있습니다.");
                writeln("마피아와 시민이 같은 수가 되면 승리합니다.");
            } else if (rolesAdapter.toString().equals("Doctor")) {
                writeln("'/use 이름' 명령어로 밤에 마피아에 지목된 사람을 살릴 수 있습니다.");
            } else if (rolesAdapter.toString().equals("Police")) {
                writeln("'/use 이름' 명령어로 밤에 한 사람의 직업을 확인할 수 있습니다.");
            }

            gameRoom.sendMessageAll(userName + "님이 입장하셨습니다.");

            ChatRoom.selected++;

            while (ChatRoom.selected < ChatRoom.MIN_PERSON) {
                Thread.sleep(1000);
            }

            while (true) {
                String read = reader.readLine();

                gameRoom.sendMessageAll(read, rolesAdapter, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatServerTh extends Thread {

    private Socket socket;
    private Room room;
    private String name;
    private BufferedReader reader;
    private PrintWriter writer;

    private Role role;
    private boolean dead;

    public ChatServerTh(Socket socket, Room room) {
        this.socket = socket;
        this.room = room;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String message) {
        writer.write(message);
        writer.flush();
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public void run() {

        try {
            name = reader.readLine();

            writer.write("당신의 직업은 " + role.getRoleName() + "입니다.");
            room.sendMessageAll(name + "님이 입장하셨습니다.");

            while (true) {
                while (!room.isDay()) {
                    doCitizen();
                }

                while (!room.isDay()) {
                    if (role == Role.Mafia) {
                        doMafia();
                    } else if (role == Role.Police) {
                        doPolice();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doMafia() {
        if (!room.isDay()) {
            try {
                String str = reader.readLine();

                Pattern pattern = Pattern.compile("/kill (\\w+)");
                Matcher matcher = pattern.matcher(str);
                if (matcher.matches()) {
                    room.kill(matcher.group(1));
                    room.sendMessageAll("악랄한 마피아가 " + matcher.group(1) + "님을 죽였습니다.");
                } else {
                    room.sendMessageAll(str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void doCitizen() {
        if (room.isDay()) {
            try {
                String str = reader.readLine();

                Pattern pattern = Pattern.compile("/vote (\\w+)");
                Matcher matcher = pattern.matcher(str);
                if (matcher.matches()) {
                    room.vote(matcher.group(1));
                } else {
                    room.sendMessageAll(str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void doPolice() {
        if (!room.isDay()) {
            try {
                String str = reader.readLine();

                Pattern pattern = Pattern.compile("/scan (\\w+)");
                Matcher matcher = pattern.matcher(str);

                if (matcher.matches()) {
                    writer.write(room.scan(matcher.group(1)).getRoleName() + "입니다.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

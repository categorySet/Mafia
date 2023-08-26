import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Server {

    public static void main(String[] args) {
        Room room = new Room();

        if (!room.isTimerStart()) {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    room.switchDay();
                }
            };
            timer.schedule(task, 3000, 100000);

            room.setTimerStart(true);
        }




        try {
            ServerSocket serverSocket = new ServerSocket(5001);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("접속: " + socket.getInetAddress());

                ChatServerTh chatServerTh = new ChatServerTh(socket, room);

                room.addClient(chatServerTh);

                chatServerTh.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

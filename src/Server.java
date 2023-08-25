import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        Room room = new Room();

        try {
            ServerSocket serverSocket = new ServerSocket(5001);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("접속: " + socket.getInetAddress());

                ChatServerTh th = new ChatServerTh(socket, room);

                room.addClient(th);

                th.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

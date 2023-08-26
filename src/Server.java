import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {

    public static void main(String[] args) {
        Room room = new Room();

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

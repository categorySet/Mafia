import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    public static void main(String[] args) {
        MafiaRoom mafiaRoom = new MafiaRoom();

        mafiaRoom.start();

        try {
            ServerSocket serverSocket = new ServerSocket(50001);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("[소켓 연결]" + socket.getInetAddress());

                ChatServerTh chatServerTh = new ChatServerTh(socket, mafiaRoom);

                mafiaRoom.addClient(chatServerTh);

                chatServerTh.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

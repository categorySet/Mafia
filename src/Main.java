import server.Gamable;
import server.MafiaServer;

public class Main {

    public static void main(String[] args) {
        Gamable gamable = new MafiaServer();

        gamable.run(50001);
    }

}

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Logger;

/**
 * Class representing Server
 */
public class Server {
    /**
     * connects to a server
     */
    private ServerSocket serverConnection;
    /**
     * list of ClientHandler
     */
    protected List<ClientHandler> clients;
    /**
     * the number players
     */
    private int playersCount;
    /**
     * initial payment
     */
    private int ante;
    /**
     * number of rounds
     */
    private int roundCount;
    static final Logger logger  = Logger.getLogger(Server.class.getName());

    /**
     * Constructor
     */
    public Server(int port) {
        try {
            this.serverConnection = new ServerSocket(port);
            logger.info("New server initialized!");
            clients = Collections
                    .synchronizedList(new ArrayList<ClientHandler>());
            this.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a Server socket
     */
    public void start() {
        try {
            Socket firstClient = serverConnection.accept();
            logger.info(firstClient.getInetAddress().getHostName()
                    + " connected");
            clients.add(new ClientHandler(firstClient));
            clients.get(0).print("FIRST");
            clients.get(0).print("Enter number of players: ");
            playersCount = Integer.parseInt(clients.get(0).get());
            clients.get(0).print("Enter ante: ");
            ante = Integer.parseInt(clients.get(0).get());
            clients.get(0).print("Enter round number: ");
            roundCount = Integer.parseInt(clients.get(0).get());
            while (clients.size()< playersCount) {
                Socket client = serverConnection.accept();
                logger.info(firstClient.getInetAddress().getHostName()
                        + " connected");
                clients.add(new ClientHandler(client));
                clients.get(clients.size()-1).print("WAIT_FOR_OTHERS");
            }
            clients.forEach(c -> c.print("ante: "+ante +" round number : "+ roundCount));
            Rozgrywka rozgrywka = new Rozgrywka(ante,playersCount,roundCount,clients);
            rozgrywka.game();
            clients.forEach(c -> c.print("\nEND"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Server server = new Server(59091);
        server.start();

    }
}

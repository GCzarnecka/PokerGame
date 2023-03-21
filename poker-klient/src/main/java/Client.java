
import org.apache.logging.log4j.core.jmx.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Class representing a Client
 */
public class Client {
    protected PrintWriter out;
    protected Scanner in;

    /**
     * Constructor
     */
    public Client() {
        //constructor
    }

    /**
     * Starts a Client.
     * @throws IOException
     */
    public boolean start() throws IOException {
        try (Socket socket = new Socket("localhost", 59091)){
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new Scanner(socket.getInputStream());

            for(String serverResponse = this.in.nextLine(); !serverResponse.equals("END"); serverResponse = this.in.nextLine()) {
                if (serverResponse.equals("PRINT")) {
                    var s = this.in.nextLine();
                    System.out.println(s);
                } else if (serverResponse.equals("GET")) {
                    Scanner scan = new Scanner(System.in);
                    String tmp = scan.nextLine();
                    this.out.println(tmp);
                }
            }
            return  true;
        }catch (IOException e){
            System.out.println(e);
            return false;
        }

    }

    public static void main(String[] args) {
        Client client = new Client();

        try {
            client.start();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }
}

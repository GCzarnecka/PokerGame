import java.io.*;
import java.net.Socket;
import java.util.Scanner;


/**
 * Handles client.
 */
public class ClientHandler {
    protected Socket client;
    private PrintWriter out;
    private Scanner in;

    /**
     * Constructor
     * @param client - client's Socket
     */
    public ClientHandler(Socket client) {
        this.client = client;
        try {
            this.out = new PrintWriter(client.getOutputStream(), true);
            this.in = new Scanner(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor for testing
     * @param inputStream
     */
    public ClientHandler(InputStream inputStream) {
        this.in = new Scanner(inputStream);
    }

    /**
     * Tells client to print next line.
     * @param s - string that will be printed
     */
    public void print(String s){
        if(client!=null) {
            out.println("PRINT");
            out.println(s);
        }
    }

    /**
     * @return client's response.
     */
    public String get(){
        if(client!=null) {
            out.println("GET");
        }
        return in.nextLine();
    }
}
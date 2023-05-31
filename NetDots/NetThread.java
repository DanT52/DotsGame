
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class NetThread extends Thread {
    private NetDot parent;
    private Socket socket;
    private ServerSocket serverSocket;
    private BufferedReader in;
    private PrintWriter out;

    public NetThread(NetDot parent, Socket socket) {
        this.parent = parent;
        this.socket = socket;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public NetThread(NetDot parent, ServerSocket serverSocket) {
        this.parent = parent;
        this.serverSocket = serverSocket;

        try {
            socket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveParent() {
        // Save the parent class (NetDot) information
        // You can implement the specific logic here
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                // Perform actions based on received messages
                // You can implement the specific logic here

                // Example: Print the received message
                System.out.println("Received message: " + inputLine);

                // Example: Send a response back to the other side
                sendMessage("Response: " + inputLine);

                // Example: Perform some action based on the received message
                if (inputLine.equalsIgnoreCase("quit")) {
                    // Exit the thread if "quit" is received
                    break;
                }
            }

            // Clean up resources
            in.close();
            out.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

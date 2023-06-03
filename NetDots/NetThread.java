
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class NetThread extends Thread {
    NetDot parent;
    ServerSocket ss;
	Scanner sc;
	PrintWriter pw;
	Socket socket;
	NetThread gameThread;
	boolean isServer;
	boolean stopServer = false;

    

    public NetThread(NetDot parent) {
    	this.parent = parent;
    	isServer = parent.isServer;
        // Save the parent class (NetDot) information
    }

    public void sendMessage(String message) { //send info
        pw.println(message);
        pw.flush();
    }
    
    public void startServer() {
        new Thread(() -> {
            createServer();
        }).start();
    }

    public void stopServer() { //cancel server creation.
        stopServer = true;
    }
    
    public void createServer() {
        try {
            ss = new ServerSocket(1234);
            ss.setSoTimeout(1000); // Timeout after 1 second. 
        } catch (Exception e) {
            System.out.println("Cannot create ServerSocket!");
            return;
        }

        while (!stopServer) {
            try {
                socket = ss.accept();
            } catch (SocketTimeoutException ste) {
                // Timeout reached without a connection. Continue to the next iteration to check the stop flag.
                continue;
            } catch (Exception e) {
                System.out.println("Cannot accept connection in socket!");
                return;
            }

            try {
                sc = new Scanner(socket.getInputStream());
                pw = new PrintWriter(socket.getOutputStream());
            } catch (Exception e) {
                System.out.println("Cannot create Scanner and PrintWriter!");
                return;
            }
            
            int sliderVal = parent.slider.getValue(); // Send slider value and name, and receive client name.
            
            sendMessage(""+parent.playerA.getText() + " " + sliderVal);
            parent.playerB.setText(sc.nextLine());
            
            parent.isConnected = true;
            parent.startButtonHandle();
            parent.noteLabel.setText("connected!");
            parent.strtBTN.setText("Start");
			parent.isWaitingForClient = false;
            System.out.println("Connected!");
            this.start();
            break; // Connection was successful. Break the loop.
        }
        
        if (stopServer) {
            System.out.println("Server stopped!");
            cleanup();
            
        }
    }
	
	public void createClient()//create ClientPortal with port 1234
	  {	try {	socket=new Socket(parent.connectToField.getText(),1234);
	  }catch(Exception e) {
		  parent.noteLabel.setText("Could not connect to server!");
	    System.out.println("cannot connect to server!");
	    return;
	  }

	    try {	sc=new Scanner(socket.getInputStream());
	      pw=new PrintWriter(socket.getOutputStream());
	    }catch(Exception e) {
	      System.out.println("cannot create scanner and printwriter!");
	      return;
	    }
	    
	    sendMessage(parent.playerB.getText()); //send name and recive server name and gridsize
	    String lineRecived = sc.nextLine();
	    String[] values = lineRecived.split(" ");
	    parent.playerA.setText(values[0]);
	    parent.slider.setValue(Integer.parseInt(values[1]));
	    
	    
	    parent.isConnected = true;
	    
	    parent.noteLabel.setText("connected!");
	    System.out.println("Connected!");
	    this.start();
	  }

    @Override
    public void run() {
    	
    	
        
        while (sc.hasNextLine()) {
			String line = sc.nextLine();
		    
		    System.out.println("Received message: " + line);
		    
		    if (line.equals("quit")) {
		    	parent.isConnected = false;
		    	String otherPlayer = (parent.isServer) ? parent.playerB.getText() : parent.playerA.getText();
		    	parent.noteLabel.setText(otherPlayer + " has left.");		    			    	
		     // Exit the thread if "quit" is received
		        break;
		    }
		    if (line.startsWith("Click ")) {
		        // Parse the coordinates from the line
		        String[] parts = line.split(" ");
		        if (parts.length == 3) {
		        	int x = Integer.parseInt(parts[1]);
	                int y = Integer.parseInt(parts[2]);

	                // Call mouseClickedHandle with the parsed coordinates and isSentYou set to false
	                parent.mouseClickedHandle(x, y, false);
		        }
		        	
		        }
		    
		    
		}

		// Clean up resources
		cleanup();
    }
    
    public void cleanup() {
        try {
            if (pw != null) {
                pw.close();
            }
        } catch (Exception e) {
            System.out.println("Error closing PrintWriter");
        }
        
        try {
            if (sc != null) {
                sc.close();
            }
        } catch (Exception e) {
            System.out.println("Error closing Scanner");
        }
        
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("Error closing Socket");
        }
        
        try {
            if (ss != null) {
                ss.close();
            }
        } catch (Exception e) {
            System.out.println("Error closing ServerSocket");
        }
        
        System.out.println("Resources cleaned up successfully");
    }

}

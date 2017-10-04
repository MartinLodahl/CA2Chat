package GUI;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class receiver implements Runnable{

private Socket socket;
    private MainGUI gui;

    public receiver(Socket socket, MainGUI gui) {
        this.gui = gui;
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Local Port :" + socket.getLocalPort());
        System.out.println("Server = " + socket.getRemoteSocketAddress() + ":" + socket.getPort());
        try {
            InputStream serverInStream = socket.getInputStream();
            Scanner serverIn = new Scanner(serverInStream);

            while (!socket.isClosed()) {
                if (serverInStream.available() > 0) {
                    if (serverIn.hasNextLine()) {
                        gui.updateChatBox(serverIn.nextLine());
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}

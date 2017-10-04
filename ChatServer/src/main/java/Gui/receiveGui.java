package Gui;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class receiveGui implements Runnable {

    private Socket socket;
    private MainGUI gui;

    public receiveGui(Socket socket, MainGUI gui) {
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

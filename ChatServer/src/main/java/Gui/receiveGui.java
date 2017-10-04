package Gui;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class receiveGui implements Runnable {

    private Socket socket;
    private String userName;
    private final LinkedList<String> messagesToSend;
    private boolean hasMessages = false;
    private MainGUI gui;

    public receiveGui(Socket socket, MainGUI gui) {
        this.gui = gui;
        this.socket = socket;
        messagesToSend = new LinkedList<String>();
    }

    public void addNextMessage(String message) {
        synchronized (messagesToSend) {
            hasMessages = true;
            messagesToSend.push(message);
        }
    }

    @Override
    public void run() {
        System.out.println("Local Port :" + socket.getLocalPort());
        System.out.println("Server = " + socket.getRemoteSocketAddress() + ":" + socket.getPort());

        try {
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), false);
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

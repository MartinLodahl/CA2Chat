package GUI2;

import GUI2.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class ReceiverGUI implements Runnable {

    private Socket socket;
    private MainGUI gui;

    public ReceiverGUI(Socket socket, MainGUI gui) {
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
                        String input = serverIn.nextLine();
                        String[] inputSplit = input.split(":", 3);

                        for (String str : inputSplit) {
                            System.out.println(str);
                        }

                        switch (inputSplit[0].toUpperCase()) {
                            case "MSGRES":
                                gui.updateChatBox("<" + inputSplit[1] + ">" + inputSplit[2]);
                                break;
                            case "CLIENTLIST":
                                gui.updateOnlineList(inputSplit[1]);
                                break;
                            default:
                                gui.updateChatBox(input);
                                break;
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

package Client;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class ClientThread implements Runnable {

    private Socket socket;
    private String userName;
    private final LinkedList<String> messagesToSend;
    private boolean hasMessages = false;

    public ClientThread(Socket socket, String userName) {
        this.socket = socket;
        this.userName = userName;
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
        System.out.println("Welcome :" + userName);

        System.out.println("Local Port :" + socket.getLocalPort());
        System.out.println("Server = " + socket.getRemoteSocketAddress());

        try {
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), false);
            InputStream serverInStream = socket.getInputStream();
            Scanner serverIn = new Scanner(serverInStream);
            serverOut.println("LOGIN:" + userName);
            while (!socket.isClosed()) {
                if (serverInStream.available() > 0) {
                    if (serverIn.hasNextLine()) {
                        String serverOutput = serverIn.nextLine();
                        if(serverOutput.startsWith("MSGRES")){
                            String[] serverOutputSplit = serverOutput.split(":");
                            serverOutput = serverOutputSplit[1];
                            serverOutput +=" : "+ serverOutputSplit[2];
                        }
                        System.out.println(serverOutput);
                    }
                }
                if (hasMessages) {
                    String nextSend = "";
                    synchronized (messagesToSend) {
                        nextSend = messagesToSend.pop();
                        hasMessages = !messagesToSend.isEmpty();
                    }
                    // serverOut.println(userName + " > " + nextSend);
                    serverOut.println(nextSend);
                    serverOut.flush();
                    if (nextSend.toUpperCase().startsWith("LOGOUT")) {
                        System.exit(0);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

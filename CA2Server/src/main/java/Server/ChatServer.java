package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatServer {

    private int serverPort = 8081;
    private final int minPortValue = 6000;
    private final int maxPortValue = 6999;
    private boolean validPort = false;
    private List<ServerThread> clients;
    
    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.startServer();
    }

    public ChatServer() {
    }

    public List<ServerThread> getClients() {
        return clients;
    }

    private void startServer() {
        clients = new ArrayList<ServerThread>();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(serverPort);
            acceptClients(serverSocket);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + serverPort);
            System.exit(1);
        }
    }

    private void selectPort() {
        while (!validPort) {
            System.out.println("Enter a port number between: " + minPortValue + " - " + maxPortValue);
            Scanner sc = new Scanner(System.in);
            serverPort = sc.nextInt();
            if (serverPort >= minPortValue && serverPort <= maxPortValue) {
                validPort = true;
            }
        }
    }

    private void acceptClients(ServerSocket serverSocket) {

        System.out.println("server starts port = " + serverSocket.getLocalSocketAddress());
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("User connect: " + socket.getRemoteSocketAddress());
                ServerThread client = new ServerThread(this, socket);
                Thread thread = new Thread(client);
                thread.start();
                clients.add(client);
            } catch (IOException ex) {
                System.out.println("Accept failed on : " + serverPort);
            }
        }
    }

    public void removeClient(ServerThread st) {
        System.out.println("User disconnect IP: " + st.getClientIP()  + " Name: " + st.getClientName());
        clients.remove(st);
    }

    public String toStringClientList() {
        String clientlist = "CLIENTLIST:";
        for (int i = 0; i < clients.size(); i++) {
            if (i == clients.size() - 1) {
                clientlist += clients.get(i).getClientName();
            } else {
                clientlist += clients.get(i).getClientName() + ",";
            }

        }

        return clientlist;
    }

}

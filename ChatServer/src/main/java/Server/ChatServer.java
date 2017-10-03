package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private static final int portNumber = 4444;

    private int serverPort;
    private List<ServerThread> clients;

    public static void main(String[] args) {
        ChatServer server = new ChatServer(portNumber);
        server.startServer();
    }

    public ChatServer(int portNumber) {
        this.serverPort = portNumber;
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

    private void acceptClients(ServerSocket serverSocket) {

        System.out.println("server starts port = " + serverSocket.getLocalSocketAddress());
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("accepts : " + socket.getRemoteSocketAddress());
                ServerThread client = new ServerThread(this, socket);
                Thread thread = new Thread(client);
                thread.start();
                clients.add(client);
            } catch (IOException ex) {
                System.out.println("Accept failed on : " + serverPort);
            }
        }
    }
    
    public void removeClient(ServerThread st){
        clients.remove(st);
    }

    
    public String toStringClientList()
    {
        String clientlist = "CLIENTLIST:";
        for (int i = 0; i < clients.size(); i++)
        {
            if (i==clients.size()-1){
               clientlist+=clients.get(i).getClientName(); 
            }else{
               clientlist+=clients.get(i).getClientName()+","; 
            }
           
        }
        
        return clientlist;
    }
    
    
}

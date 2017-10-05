package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable {

    private Socket socket;
    private PrintWriter clientOut;
    private ChatServer server;
    private String clientName = "guest";
    private BufferedReader in;

    public ServerThread(ChatServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    private PrintWriter getWriter() {
        return clientOut;
    }

    @Override
    public void run() {
        try {
            this.clientOut = new PrintWriter(socket.getOutputStream(), false);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String input = in.readLine();
                //System.out.println("User IP: " + socket.getRemoteSocketAddress() + " Debugging: " + input);
                /* retrive the client name, and send a list containing all the connected
                    clients. */
                if (input.toUpperCase().startsWith("CLIENTS:")) {
                    input = server.toStringClientList();
                    for (ServerThread thatClient : server.getClients()) {
                        PrintWriter thatClientOut = thatClient.getWriter();
                        if (thatClientOut != null) {
                            thatClientOut.write(input + "\r\n");
                            thatClientOut.flush();
                        }
                    }
                }

                if (input.toUpperCase().startsWith("LOGIN:") && clientName.equals("guest")) {
                    clientName = input.substring(6);
                    System.out.println("user IP: " + socket.getRemoteSocketAddress() + " changed name to: " + clientName);
                    input = server.toStringClientList();
                    for (ServerThread thatClient : server.getClients()) {
                        PrintWriter thatClientOut = thatClient.getWriter();
                        if (thatClientOut != null) {
                            thatClientOut.write(input + "\r\n");
                            thatClientOut.flush();
                        }
                    }
                } // Sends a message to all the active clients.
                else if (input.toUpperCase().startsWith("MSG:*:")) {
                    String[] message = input.split(":", 3);
                    System.out.println("User IP: " + socket.getRemoteSocketAddress() + " <" + clientName + "> sent message to all: " + message[2]);
                    for (ServerThread thatClient : server.getClients()) {
                        PrintWriter thatClientOut = thatClient.getWriter();
                        if (thatClientOut != null) {
                            thatClientOut.write("MSGRES:" + clientName + ":" + message[2] + "\r\n");
                            thatClientOut.flush();
                        }
                    }
                } // Sends a message to the clients which are parsed in as parameter
                else if (input.toUpperCase().startsWith("MSG:")) {
                    //MSG: NAMES : MESSAGE
                    String[] semiSplit = input.split(":", 3);
                    if (semiSplit.length == 3) {
                        String[] receivers = semiSplit[1].split(",");
                        input = semiSplit[2];
                        for (ServerThread thatClient : server.getClients()) {
                            for (int i = 0; i < receivers.length; i++) {
                                String receiver = receivers[i];
                                if (thatClient.getClientName().toUpperCase().equals(receiver.toUpperCase())) {
                                    PrintWriter thatClientOut = thatClient.getWriter();
                                    if (thatClientOut != null) {
                                        System.out.println("User IP: " + socket.getRemoteSocketAddress() + " <" + clientName + "> whispers <" + receiver + ">: " + input);
                                        thatClientOut.write("MSGRES:" + clientName + ":" + input + "\r\n");
                                        thatClientOut.flush();
                                    }
                                }
                            }
                        }
                    } else {
                        clientOut.write("MSGRES:SERVER: The message format is incorrect. \r\n");
                        clientOut.flush();
                    }
                } else if (input.toUpperCase().startsWith("LOGOUT:")) {
                    break;
                } else {
                    clientOut.println("MSGRES:SERVER: The message format is incorrect. ");
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            server.removeClient(this);
            String clientListDisconnect = server.toStringClientList();
            for (ServerThread thatClient : server.getClients()) {
                PrintWriter thatClientOut = thatClient.getWriter();
                if (thatClientOut != null) {
                    thatClientOut.write(clientListDisconnect + "\r\n");
                    thatClientOut.flush();
                }
            }
        }
    }

    public String getClientIP() {
        return "" + socket.getRemoteSocketAddress();
    }

    public String getClientName() {
        return clientName;
    }
}

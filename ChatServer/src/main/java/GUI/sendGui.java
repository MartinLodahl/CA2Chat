package GUI;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class sendGui implements Runnable {

    private Socket socket;
    private ArrayBlockingQueue<String> messagesToSend;
    private String nextSend;

    private PrintWriter serverOut;

    public sendGui(Socket socket, ArrayBlockingQueue messagesToSend, String username) {
        this.socket = socket;
        this.messagesToSend = messagesToSend;

        try {
            serverOut = new PrintWriter(socket.getOutputStream(), false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        serverOut.println("login:"+username);
        serverOut.flush();
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                nextSend = messagesToSend.take();
               
                if(!nextSend.contains(":")){
                    nextSend = "msg:*:" + nextSend;
                }
                
                serverOut.println(nextSend);
                serverOut.flush();
                
                System.out.println("send a msg!");
                
                if (nextSend.toUpperCase().startsWith("LOGOUT")) {
                    System.exit(0);
                }
            } catch (InterruptedException ex) {

            }
        }
    }
}

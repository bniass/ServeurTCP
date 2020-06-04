package serveur;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static Map<String, Socket> clients = new HashMap<String, Socket>();
    public static void main(String[] args) throws Exception
    {
        Socket client = null;
        ServerSocket serverSocket = null;

        try {
            // je suis joignable sur le port 5001
            serverSocket = new ServerSocket(5001);
            System.out.println("serveur lancé sur le port 5001");
            int nbClient = 0;
            while (true){
                System.out.println("en attente de connexion cliente");
                client = serverSocket.accept();
                nbClient++;
                System.out.println("client "+(nbClient)+" vient de se connecter");
                PrintStream out = new PrintStream(client.getOutputStream(), true);
                out.println("Bienvenu(e)");
                new ServeurThreadLecture(client).start();
            }

        }catch (Exception e){
            if(serverSocket != null && !serverSocket.isClosed()){
                serverSocket.close();
            }
            if(client != null && !client.isClosed()){
                client.close();
            }
        }
    }
}

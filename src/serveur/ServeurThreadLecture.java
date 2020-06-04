package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

public class ServeurThreadLecture extends Thread {
    private Socket client;
    public ServeurThreadLecture(Socket client){
        this.client = client;
    }

    @Override
    public void run() {
        // thread de lecture
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            String pseudo = in.readLine();
            Main.clients.put(pseudo, client);
            while (true){
                String msg = in.readLine();
                if(msg == null){
                    break;
                }

                Set<String> keys = Main.clients.keySet();
                for (String cle : keys) {
                    Socket c = Main.clients.get(cle);
                    if(!cle.equals(pseudo)) {
                        PrintStream out = new PrintStream(c.getOutputStream(), true);
                        out.println(pseudo + " : " + msg);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

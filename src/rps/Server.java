/**
 *
 * @author Shaheed Ahmed Dewan Sagar
 *         AUST-12.01.04.085
 *         sdewan64@gmail.com
 */
package rps;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shaheed Ahmed Dewan Sagar
 *         AUST-12.01.04.085
 *         sdewan64@gmail.com
 */
public class Server {
    
    private static ServerSocket server;
    
    private static List<Info> infos = new ArrayList<>();
    public static int id =0;
    
    public static void main(String[] args){
        try {
            server = new ServerSocket(8794);
            Thread mustUseThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<2;i++){
                        try {
                            Socket client = server.accept();
                            Info info = new Info();
                            info.id = id;
                            info.fromclient = new DataInputStream(client.getInputStream());
                            info.toclient = new DataOutputStream(client.getOutputStream());
                            info.toclient.writeUTF("ready:"+info.id);
                            System.out.println("added");
                            infos.add(info);
                            id++;
                        } catch (IOException ex) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            mustUseThread.start();
            try {
                mustUseThread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            int player1won = 0;
            int player2won = 0;
            int draw = 0;
            
            for(int i=0;i<5;i++){
                String res1 = infos.get(0).fromclient.readUTF();
            String res2 = infos.get(1).fromclient.readUTF();
            
            String res = "";
            if (res1.equals(res2)) {
                    res = "Draw";
                    draw++;
            }

            else if (res1.equals("R") && res2.equals("S")) {
                    res = "Player 1 wins.";
                    player1won++;
            }

            else if (res1.equals("S") && res2.equals("R")) {
                    res = "Player 2 wins.";
                    player2won++;
                    }

            else if (res1.equals("R") && res2.equals("P")) {
                    res = "Player 2 wins.";
                    player2won++;
            }

            else if (res1.equals("P") && res2.equals("R")) {
                    res = "Player 1 wins.";
                    player1won++;
            }

            else if (res1.equals("S") && res2.equals("P")) {
                    res = "Player 1 wins.";
                    player1won++;
            }

            else if (res1.equals("P") && res2.equals("S")) {
                    res = "Player 2 wins.";
                    player2won++;
            }
            
            for(Info info : infos){
                info.toclient.writeUTF(res);
            }
            }
            
            for(Info info : infos){
                info.toclient.writeUTF("Player 1 won = "+player1won+"\nPlayer 2 won = "+player2won+"\nDraw = "+draw);
            }
                    
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static class Info{
        public int id;
        public DataInputStream fromclient;
        public DataOutputStream toclient;
    }
}

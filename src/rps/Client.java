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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shaheed Ahmed Dewan Sagar
 *         AUST-12.01.04.085
 *         sdewan64@gmail.com
 */
public class Client {
    
    public static void main(String[] args){
        int id;
        try {
            Socket client = new Socket("localhost",8794);
            DataInputStream fromserver = new DataInputStream(client.getInputStream());
            DataOutputStream toserver = new DataOutputStream(client.getOutputStream());
            
            id = Integer.parseInt(fromserver.readUTF().split(":")[1]);
            System.out.println(id);
            for(int i=0;i<5;i++){
                System.out.println("Enter your Move (R)ock , (P)aper or (S)cissor");
            String choise = new Scanner(System.in).nextLine();
            
            toserver.writeUTF(choise);
            
            String result = fromserver.readUTF();
            
            System.out.println(result);
            }
            
            System.out.println(fromserver.readUTF());
                    
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;

public class SimpleServerProgram {

   public static void main(String args[]) {

       ServerSocket listener = null;
       String line;
       BufferedReader is;
       BufferedWriter os;
       Socket socketOfServer = null;

       try {
           listener = new ServerSocket(9999);
       } catch (IOException e) {
           System.out.println(e);
           System.exit(1);
       }

       try {
           System.out.println(" Server is waiting to accept user...");
           // Accept client connection request
           // Get new Socket at Server.    
           socketOfServer = listener.accept();
           System.out.println(" Accept a client!");
           // Open input and output streams
           is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
           os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));

           while (true) {
               // Read data to the server (sent from client).
               line = is.readLine();
               // Write to socket of Server
               // (Send by the client)
               System.out.println("send by client : " + line);

               // get only the PCR number
               String [] client_line = line.split("\\s+");
               String pcr_client_number = client_line[0];
               String pcr_client_date = client_line[1];

               // DB CONNECTION
               DatabaseConnect db = new DatabaseConnect();
               db.getAllValue();
              Boolean val = db.Check_ID(pcr_client_number);
               if (val==true){
                   db.Check_Status(pcr_client_number);
               }




               // If users send QUIT (To end conversation).
               if (line.equals("QUIT")) {
                   os.write(">> OK");
                   os.newLine();
                   os.flush();
                   break;
               }
           }

       } catch (IOException | SQLException | ClassNotFoundException e) {
           System.out.println(e);
           e.printStackTrace();
       }
       System.out.println("Sever stopped!");
   }
}
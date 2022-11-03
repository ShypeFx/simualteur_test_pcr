import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {

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
               // (Send to client)
               os.write(" sent by client : " + line);
               // End of line
               os.newLine();
               // Flush data.
               os.flush();  
               
               String [] client_line = line.split("\\s+");
               // check if the data who we received from client after the split is working
               System.out.println(" tab client line : " + Arrays.toString(client_line));
               String pcr_client_number = client_line[0];

               // Get data from file
               FileReader fileR = new FileReader("server/src/list_pcr.txt");
               BufferedReader buffR = new BufferedReader(fileR);
               
               String l_file;

               while( ( l_file = buffR.readLine()) != null) {
            	   
            	   String [] file_line = l_file.split("\\s+");
                   //System.out.println(" file_line : "+Arrays.toString(file_line));


            	   if(file_line[0].equals(pcr_client_number)) {
            		   System.out.println(" WORKING ");
            		   os.write(" WORKING FOR : "+ pcr_client_number+"");
            		   os.newLine();
            		   os.flush();
            	   }else {
            		   System.out.println(" Not Matching Number for : "+file_line[0]);
            		   os.write(" This number don't work : " +file_line[0]);
            		   os.newLine();
            		   os.flush();
            	   }
            	   
               }
               


               // If users send QUIT (To end conversation).
               if (line.equals("QUIT")) {
                   os.write(">> OK");
                   os.newLine();
                   os.flush();
                   break;
               }
           }

       } catch (IOException e) {
           System.out.println(e);
           e.printStackTrace();
       }
       System.out.println("Sever stopped!");
   }
}
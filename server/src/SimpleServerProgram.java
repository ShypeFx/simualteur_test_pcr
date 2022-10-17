import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServerProgram {

   public static void main(String args[]) {

       ServerSocket listener = null;
       String line;
       BufferedReader is;
       BufferedWriter os;
       Socket socketOfServer = null;

       // Try to open a server socket on port 9999
       // Note that we can't choose a port less than 1023 if we are not
       // privileged users (root)

 
       try {
           listener = new ServerSocket(9999);
       } catch (IOException e) {
           System.out.println(e);
           System.exit(1);
       }

       try {
           System.out.println("Server is waiting to accept user...");

           // Accept client connection request
           // Get new Socket at Server.    
           socketOfServer = listener.accept();
           System.out.println("Accept a client!");

           // Open input and output streams
           is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
           os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));


           while (true) {
               // Read data to the server (sent from client).
               line = is.readLine();
               
               // Write to socket of Server
               // (Send to client)
               os.write(">> " + line);
               
               // End of line
               os.newLine();
               // Flush data.
               os.flush();  
               
               String [] client_line = line.split(" ");
               String pcr_client_number = client_line[1];
               String validity_time = client_line[2];
               
               os.write(pcr_client_number + " ");
               os.write(validity_time);
               os.newLine();
               
               FileReader fileR = new FileReader("C:\\Users\\PC\\eclipse-workspace\\test_serveur_client\\src\\test_serveur_client\\list_pcr.txt");
               BufferedReader buffR = new BufferedReader(fileR);
               
               String l_file;
         
               
               while( ( l_file = buffR.readLine()) != null) {
            	   
            	   String [] file_line = l_file.split(" ");
            	   if(file_line[0] == pcr_client_number) {
            		   System.out.println(" WORKING ");
            		   os.write(" Working with number : '"+ pcr_client_number+"'");
            		   os.newLine();
            		   os.flush();
            	   }else {
            		   System.out.println(" Not Matching Number ");
            		   os.write(" This number don't work : '"+ file_line[0]+"'");
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

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
               int pcr_validity_time = Integer.parseInt(client_line[1]);

               // DB CONNECTION
               DatabaseConnect db = new DatabaseConnect();
               Date test_date = db.getDate(pcr_client_number);
               db.getAllValue();
              Boolean val = db.Check_ID(pcr_client_number);
               if (val==true){
                   db.Check_Status(pcr_client_number);
               }

              Boolean val = db.Check_ID(pcr_client_number);
               if (val){
                   if(db.Check_Status(pcr_client_number)){
                       if(db.Check_Validity_Date(pcr_client_number,pcr_validity_time)){
                           System.out.println(" ------------------------ WORKING ------------------------");
                           System.out.println(" Le test numero '"+pcr_client_number+"' est VALIDE ");
                           System.out.println(" Date du test = "+db.getDate(pcr_client_number));
                           System.out.println(" Date + duree de validite = "+db.getDatePlusValidityFormater(test_date,pcr_validity_time));
                           System.out.println(" Date actuel = "+db.getCurentDateFormater());
                           System.out.println("----------------------------------------------------------");
                       }else{
                           System.out.println(" ------------------------ WARNING ------------------------");
                           System.out.println(" Le test numero '"+pcr_client_number+"' n'est plus valide ");
                           System.out.println(" Date du test = "+db.getDate(pcr_client_number));
                           System.out.println(" Date actuel = "+db.getCurentDateFormater());
                           System.out.println("----------------------------------------------------------");
                       }
                   }else{
                       System.out.println(" ------------------------ WARNING ------------------------");
                       System.out.println(" Le test numero '"+pcr_client_number+"' est POSITIF au COVID-19");
                       System.out.println("----------------------------------------------------------");
                   }
               }else{
                   System.out.println(" ------------------------ WARNING ------------------------");
                   System.out.println(" Le test numero '"+pcr_client_number+"' n'existe pas");
                   System.out.println("----------------------------------------------------------");
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
       } catch (ParseException e) {
           e.printStackTrace();
       }
       System.out.println("Sever stopped!");
   }
}
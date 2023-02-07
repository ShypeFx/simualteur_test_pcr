import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class SimpleServerProgram {

    public static void main(String [] args) throws IOException {

        String line;
        BufferedReader is;
        BufferedWriter os;
        Socket socketOfServer = null;

        ServerSocket listener = new ServerSocket(9999);
        System.out.println(" Server is waiting to accept user...");


        try {
            socketOfServer = listener.accept();

            //
            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
            PrintWriter out = new PrintWriter(socketOfServer.getOutputStream(), true);

            while (true) {
                line = is.readLine();
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
                if (val){
                    if(db.Check_Status(pcr_client_number)){
                        if(db.Check_Validity_Date(pcr_client_number,pcr_validity_time)){
                            System.out.println(" ------------------------ WORKING ------------------------");
                            System.out.println(" Le test numero '"+pcr_client_number+"' est VALIDE ");
                            System.out.println(" Date du test = "+db.getDate(pcr_client_number));
                            System.out.println(" Date + duree de validite = "+db.getDatePlusValidityFormater(test_date,pcr_validity_time));
                            System.out.println(" Date actuel = "+db.getCurentDateFormater());
                            System.out.println("----------------------------------------------------------");
                            out.println("green");
                            out.println(" date test : " + db.getDate(pcr_client_number));
                            out.println(" resultat : " + db.getStatus(pcr_client_number));
                        }else{
                            System.out.println(" ------------------------ WARNING ------------------------");
                            System.out.println(" Le test numero '"+pcr_client_number+"' n'est plus valide ");
                            System.out.println(" Date du test = "+db.getDate(pcr_client_number));
                            System.out.println(" Date actuel = "+db.getCurentDateFormater());
                            System.out.println("----------------------------------------------------------");
                            out.println("red");
                            out.println(" date test : " + db.getDate(pcr_client_number));
                            out.println(" resultat : " + db.getStatus(pcr_client_number));
                        }
                    }else{
                        System.out.println(" ------------------------ WARNING ------------------------");
                        System.out.println(" Le test numero '"+pcr_client_number+"' est POSITIF au COVID-19");
                        System.out.println("----------------------------------------------------------");
                        out.println("red");
                        out.println(" date test : " + db.getDate(pcr_client_number));
                        out.println(" resultat : " + db.getStatus(pcr_client_number));
                    }
                }else{
                    System.out.println(" ------------------------ WARNING ------------------------");
                    System.out.println(" Le test numero '"+pcr_client_number+"' n'existe pas");
                    System.out.println("----------------------------------------------------------");
                    out.println("red");
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
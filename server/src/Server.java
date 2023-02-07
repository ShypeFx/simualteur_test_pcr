import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String line;
                while (true) {
                    line = in.readLine();
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

                    System.out.println(" val boolean pcr : " + val);

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
                        out.println("pink");
                        out.println(" date test : ");
                        out.println(" resultat : ");
                    }
                }
            } catch (IOException | SQLException | ClassNotFoundException e) {
                System.out.println(e);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}

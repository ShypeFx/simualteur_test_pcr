import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class Server {
    //Fonction principale du serveur permettant d'accepter les connexions clients
    public static void main(String[] args) throws IOException {
        //Création du socket pour l'acceptation des clients
        ServerSocket serverSocket = new ServerSocket(9090);
        //Le serveur accepte les clients en continue sans limite
        while (true) {
            //acceptation d'un client via un nouveau socket
            Socket clientSocket = serverSocket.accept();

            //Création d'un thread pour le nouveau client avec le nouveau socket
            new Thread(new ClientHandler(clientSocket)).start();
            //Chaque client ayant son propre Thread, le serveur pourra traiter les requêtes clients
            // de manière simultanée et en parallèle.
        }
    }

    //Class permettant la gestion des requêtes faites par les clients
    //Implémentée de Runnable pour la partie threading
    private static class ClientHandler implements Runnable {
        private Socket socket;

        //Quand un client est créer ou lui associe le socket créer par le serveur pour ce dernier
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        //Fonction Run executant le code répondant aux requêtes des clients
        public void run() {
            try {
                //Création d'un BufferedReader pour lire les requêtes clients
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //Création d'un PrintWriter pour écrire des requêtes clients
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Connexion à la base de données
                DatabaseConnect db = new DatabaseConnect();

                //INUTILE
                //BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                //line va contenir les informations envoyées par le client
                String line;
                //On execute cette partie en continu qui teste la validité des tests PCR
                while (true) {
                    // On lit en premier lieu les informations envoyées par le client de type "[Numero du test] [nombre de jours]
                    line = in.readLine();
                    System.out.println("send by client : " + line);

                    // On scinde la chaine de caractères pour séparer le numéro et le nombre de jours
                    String [] client_line = line.split("\\s+");
                    //On stocke le numéro du test
                    String pcr_client_number = client_line[0];
                    //On stocke le nombre de jours valide
                    int pcr_validity_time = Integer.parseInt(client_line[1]);

                    //On obtient la date du test PCR
                    Date test_date = db.getDate(pcr_client_number);
                    db.getAllValue();
                    //On regarde si le numéro de test existe
                    Boolean val = db.Check_ID(pcr_client_number);

                    System.out.println(" val boolean pcr : " + val);

                    if (val){
                        // On regarde si le test PCR à bien un résultat négatif
                        if(db.Check_Status(pcr_client_number)){
                            //Si la date actuelle plus le nombre de jours de validité du test rensignés est
                            // antérieur à la date de validité du test.
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
                                out.println("orange");
                                out.println(" date test : " + db.getDate(pcr_client_number));
                                out.println(" resultat : résultat non valide");
                            }
                            // Si test PCR positif au covid
                        }else{
                            System.out.println(" ------------------------ WARNING ------------------------");
                            System.out.println(" Le test numero '"+pcr_client_number+"' est POSITIF au COVID-19");
                            System.out.println("----------------------------------------------------------");
                            out.println("red");
                            out.println(" date test : " + db.getDate(pcr_client_number));
                            out.println(" resultat : " + db.getStatus(pcr_client_number));
                        }
                        //Si aucune information n'a été trouvée sur le numéro de test et que par conséquent il n'existe pas
                    }else{
                        System.out.println(" ------------------------ WARNING ------------------------");
                        System.out.println(" Le test numero '"+pcr_client_number+"' n'existe pas");
                        System.out.println(" resultat : PCR inconnu");
                        System.out.println("date test : Invalide");
                        System.out.println("----------------------------------------------------------");
                        out.println("purple");
                        out.println(" date test : Invalide ");
                        out.println(" resultat : PCR inconnu");
                    }
                }
                // Prise en compte des erreurs possible
            } catch (IOException | SQLException | ClassNotFoundException e) {
                System.out.println(e);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
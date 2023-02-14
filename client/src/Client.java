import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Client extends JFrame implements ActionListener {
    private JTextField numero1;
    private JLabel label1;
    private JLabel label2;
    private JTextField numero2;
    private JButton bouton;
    private JLabel resultat;
    private JLabel date_res;
    private JTextArea  log;

    ArrayList<String> events = new ArrayList<String>();

    JFrame frame = new JFrame();

    Container contentPane = frame.getContentPane();
    BoxLayout boxLayout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
    StringBuilder sb = new StringBuilder();
    public Client()  {
        super("Client");
        frame.setBackground(Color.DARK_GRAY);
        contentPane.setBackground(Color.DARK_GRAY);

        frame.setSize(450,350);
        frame.setResizable(false);

        contentPane.setLayout(boxLayout);

        JPanel pcrPanel = new JPanel();
        label1 = new JLabel("Numéro pcr : ");
        numero1 = new JTextField(10);
        pcrPanel.add(label1);
        pcrPanel.add(numero1);
        pcrPanel.setOpaque(false);

        JPanel jourPanel = new JPanel();
        label2 = new JLabel("Jour validité :");
        numero2 = new JTextField(10);
        jourPanel.add(label2);
        jourPanel.add(numero2);
        jourPanel.setOpaque(false);

        JPanel resultPanel = new JPanel();
        resultat = new JLabel();
        date_res = new JLabel();
        resultPanel.add(resultat);
        resultPanel.add(date_res);
        resultPanel.setOpaque(false);
        resultPanel.setBackground(Color.DARK_GRAY);

        bouton = new JButton("Envoyer");

        log = new JTextArea();
        log.setEditable(false);
        log.setBackground(Color.DARK_GRAY);

        label1.setForeground(Color.WHITE);
        label2.setForeground(Color.WHITE);
        resultat.setForeground(Color.WHITE);
        date_res.setForeground(Color.WHITE);
        log.setForeground(Color.WHITE);

        bouton.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPane.add(pcrPanel);
        contentPane.add(jourPanel);
        contentPane.add(bouton);
        contentPane.add(resultPanel);
        contentPane.add(log);
        bouton.addActionListener(this);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (!numero1.getText().matches("\\d+") || !numero2.getText().matches("\\d+") ){
            JOptionPane.showMessageDialog(null, "Les champs doivent être que des nombres");
        }else {
            if (numero1.getText().length() != 6) {
                if (numero1.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(null, "Le champ n°PCR est vide");
                } else if (numero1.getText().length() <= 6) {
                    JOptionPane.showMessageDialog(null, "Il manque des chiffres au n°PCR");
                } else {
                    JOptionPane.showMessageDialog(null, "Il y a trop des chiffres au n°PCR");
                }
            } else if (numero2.getText().length() <= 0) {
                JOptionPane.showMessageDialog(null, "Les champs sont vides veuillez les remplirs");
            } else {
                try {
                    String resultatPcr;
                    Socket socket = new Socket("localhost", 9090);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out.println(numero1.getText() + " " + numero2.getText());

                    String response = in.readLine();

                    if (response.equals("green")) {
                        contentPane.setBackground(new Color(0, 194, 62));
                        log.setBackground(new Color(0, 194, 62));
                        date_res.setText(in.readLine());
                        resultat.setText(in.readLine());
                        resultatPcr = "Négative";
                        System.out.println(" test pcr fonctionnel ");
                    } else if (response.equals("red")) {
                        contentPane.setBackground(new Color(200, 0, 0));
                        log.setBackground(new Color(200, 0, 0));
                        date_res.setText(in.readLine());
                        resultat.setText(in.readLine());
                        resultatPcr = "Positive";
                        System.out.println(" test pcr non ");
                    } else if (response.equals("orange")) {
                        contentPane.setBackground(new Color(255, 128, 0));
                        log.setBackground(new Color(255, 128, 0));
                        date_res.setText(in.readLine());
                        resultat.setText(in.readLine());
                        resultatPcr = "Non valide";
                        System.out.println("Le test PCR n'est plus valide");
                    } else {
                        frame.getContentPane().setBackground(new Color(102, 0, 102));
                        log.setBackground(new Color(102, 0, 102));
                        date_res.setText(in.readLine());
                        resultat.setText(in.readLine());
                        resultatPcr = "Pcr inconnu";
                        System.out.println(" test pcr inconnu ");

                    }

                    events.add(numero1.getText() + " " + numero2.getText() + " " + resultatPcr);
                    StringBuilder sb = new StringBuilder();
                    for (String event : events) {
                        sb.append(event).append("\n");
                    }
                    log.setText(sb.toString());

                    validate();
                    repaint();

                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }
    }
    public static void main(String[] args) throws ClassNotFoundException {
        new Client();
    }
        /* private void enregistrerLog(String log) throws SQLException {
         Connection connection = DatabaseConnect.DatabaseConnection();
         try (connection) {
             PreparedStatement statement = (PreparedStatement) connection.prepareStatement("INSERT INTO log (message) VALUES (?)");
             statement.setString(1, log);
             statement.executeUpdate();
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
     }*/
}


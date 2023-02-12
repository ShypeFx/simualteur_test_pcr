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
    ArrayList<String> events = new ArrayList<String>();
    private JTextArea  log;



    public Client() {
        super("Client");
        setLayout(new FlowLayout());
        label1 = new JLabel(" Numero pcr : ");
        label2 = new JLabel(" Jour validite : ");
        numero1 = new JTextField(10);
        numero2 = new JTextField(10);
        bouton = new JButton("Envoyer");
        resultat = new JLabel();
        date_res = new JLabel();
        log = new JTextArea ();
        log.setEditable(false);

        add(label1);
        add(numero1);
        add(label2);
        add(numero2);
        add(bouton);
        add(resultat);
        add(date_res);
        add(log);
        bouton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (numero1.getText().length()<=0 || numero2.getText().length()<=0){
            JOptionPane.showMessageDialog(null, "Les champs sont vides veuillez les remplirs");
        } else {
            try {
                String resultatPcr;
                Socket socket = new Socket("localhost", 9090);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.println(numero1.getText() + " " + numero2.getText());

                String response = in.readLine();

                if(response.equals("green")){
                    getContentPane().setBackground(new Color(0,200,0));
                    log.setBackground(Color.GREEN);
                    date_res.setText(in.readLine());
                    resultat.setText(in.readLine());
                    resultatPcr = "Positif";
                    System.out.println(" test pcr fonctionnel ");

                }else if(response.equals("red")){
                    getContentPane().setBackground(new Color(200,0,0));
                    log.setBackground(new Color(200,0,0));
                    date_res.setText(in.readLine());
                    resultat.setText(in.readLine());
                    resultatPcr = "Négatif";
                    System.out.println(" test pcr non ");
                }else{
                    getContentPane().setBackground(Color.PINK);
                    log.setBackground(Color.PINK);
                    resultatPcr = "Pcr inconnu";
                    System.out.println(" test pcr non ");
                }
                events.add(numero1.getText() + " " + numero2.getText()+" "+resultatPcr);
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

    public static void main(String[] args) {
        Client client = new Client();
        client.setSize(600, 450);
        client.setVisible(true);
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

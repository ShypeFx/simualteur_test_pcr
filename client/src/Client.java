import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Client extends JFrame implements ActionListener {
    private JTextField numero1;
    private JLabel label1;
    private JLabel label2;
    private JTextField numero2;
    private JButton bouton;
    private JLabel resultat;

    public Client() {
        super("Client");
        setLayout(new FlowLayout());
        label1 = new JLabel();
        label2 = new JLabel();
        numero1 = new JTextField(10);
        numero2 = new JTextField(10);
        bouton = new JButton("VERIFIER");
        label1.setText("N° PCR : ");
        label2.setText("Jours de validité : ");
        label2.setBounds(20,100,100,30);
        label1.setBounds(20,50,100,30);
        resultat = new JLabel();

        add(label1);
        add(numero1);
        add(label2);
        add(numero2);
        add(bouton);
        add(resultat);
        bouton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {



        try {
            Socket socket = new Socket("localhost", 9999);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(numero1.getText() + " " + numero2.getText());

            if(in.readLine().equals("green")){
                getContentPane().setBackground(Color.GREEN);
            }
            validate();
            repaint();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
        int height = tailleEcran.height;
        int width = tailleEcran.width;
        client.setBackground(Color.ORANGE);
        client.setVisible(true);
        client.setSize(400,300);
        client.setSize(width/2, height/2);
        client.setLocationRelativeTo(null);

        client.setResizable(true);
        client.setTitle("PCR TEST VERIFICATION");

        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
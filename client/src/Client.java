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
    private JLabel date_res;

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

        add(label1);
        add(numero1);
        add(label2);
        add(numero2);
        add(bouton);
        add(resultat);
        add(date_res);
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
                date_res.setText(in.readLine());
                resultat.setText(in.readLine());
            }else{
                getContentPane().setBackground(Color.RED);
                date_res.setText(in.readLine());
                resultat.setText(in.readLine());
            }
            validate();
            repaint();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.setSize(600, 450);
        client.setVisible(true);
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Fenetre extends JFrame {

    public static void main(String[] args){
        setFenetre();
        textField();
    }
    static JFrame maFenetre = new JFrame();
    public static void setFenetre() {
        Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
        int height = tailleEcran.height;
        int width = tailleEcran.width;
        maFenetre.setVisible(true);
        maFenetre.setSize(400,300);
        maFenetre.setSize(width/2, height/2);
        maFenetre.setLocationRelativeTo(null);

        maFenetre.setResizable(true);
        maFenetre.setTitle("PCR TEST VERIFICATION");

        maFenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



    public static void textField(){

        JTextField text1 = new JTextField();
        JTextField text2 = new JTextField();
        JLabel label1 = new JLabel();
        JButton btn;
        label1.setBounds(20,50,100,30);
        text1.setBounds(70,50,180,30);


        label1.setText("N° PCR : ");
        btn = new JButton("VERIFIER");
        btn.setBounds(100,140,100,40);

        btn.addActionListener(actionPerformed());


        maFenetre.add(label1);
        maFenetre.add(text1);

        maFenetre.add(btn);
        maFenetre.setSize(340,250);
        maFenetre.setLayout(null);
        maFenetre.setVisible(true);
    }
    public static ActionListener actionPerformed() {
        SimpleServerProgram ssp = new SimpleServerProgram();
        setFenetre();
        Label label = new Label("");
        label.setBounds(80,80,100,30);
        label.setText(ssp.toString());
        maFenetre.add(label);
        return (ActionListener) maFenetre;
    }
}
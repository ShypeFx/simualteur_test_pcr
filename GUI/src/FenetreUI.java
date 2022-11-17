import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreUI extends JFrame {
   public static JTextField text1 = new JTextField();
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


        JLabel label1 = new JLabel();
        JButton btn;
        label1.setBounds(20,50,100,30);
        text1.setBounds(70,50,180,30);


        label1.setText("N° PCR : ");
        btn = new JButton("VERIFIER");
        btn.setBounds(100,140,100,40);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Label label = new Label();
               label.setText("null");//SimpleServerProgram.essai());
               maFenetre.add(label);
            }
                              });


        maFenetre.add(label1);
        maFenetre.add(text1);

        maFenetre.add(btn);
        maFenetre.setSize(340,250);
        maFenetre.setLayout(null);
        maFenetre.setVisible(true);
    }
    public static JTextField getText1(){
        return text1;

    }
   // public static ActionListener actionPerformed() {
     //   new setFenetre();
       // return (ActionListener) text1;
        /*SimpleServerProgram ssp = new SimpleServerProgram();
        setFenetre();
        Label label = new Label("");
        label.setBounds(80,80,100,30);
        System.out.println(ssp.toString());
        label.setText("hey");
        maFenetre.add(label);
        return (ActionListener) maFenetre;*/
    //}
}
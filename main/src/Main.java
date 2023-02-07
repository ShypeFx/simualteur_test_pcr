import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Client c1 = new Client();

        c1.setSize(600, 450);
        c1.setVisible(true);
        c1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Client c2 = new Client();
        c2.setSize(600, 450);
        c2.setVisible(true);
        c2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
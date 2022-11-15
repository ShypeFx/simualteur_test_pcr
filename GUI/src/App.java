import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App extends JDialog {
    private JPanel contentPane;
    private JButton btn;
    private JTextField text1;
    private JLabel label1;

    public App() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btn);

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        App dialog = new App();

        Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
        int height = tailleEcran.height;
        int width = tailleEcran.width;
        dialog.setSize(400,300);
        dialog.setSize(width/2, height/2);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(true);
        dialog.setTitle("PCR TEST VERIFICATION");
        dialog.setVisible(true);
        System.exit(0);
    }
}

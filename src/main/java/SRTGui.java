import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SRTGui extends JFrame {
    private JTextField passField1;
    private JTextField passField2;
    private JButton encryptButton;
    private JButton decryptButton;
    private JFileChooser fileChooser;

    public SRTGui() {
        setTitle("SRT Encryption/Decryption");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        passField1 = new JTextField("Enter passphrase");
        passField2 = new JTextField("Re-enter passphrase");

        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");

        fileChooser = new JFileChooser();

        add(passField1);
        add(passField2);
        add(encryptButton);
        add(decryptButton);

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle encryption logic
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle decryption logic
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SRTGui().setVisible(true);
        });
    }
}

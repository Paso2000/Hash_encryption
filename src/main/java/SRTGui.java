import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SRTGui extends JFrame {
    public SRTGui() {
        // Imposta il titolo della finestra
        setTitle("SRT tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Label e campo di testo "message"
        JLabel messageLabel = new JLabel("Message");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Spaziatura
        add(messageLabel, gbc);

        JTextField messageField = new JTextField(20);
        gbc.gridx = 1;
        add(messageField, gbc);

        // Label e pulsante "search file"
        JLabel searchLabel = new JLabel("Search file");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(searchLabel, gbc);

        JButton searchButton = new JButton("Browse");
        gbc.gridx = 1;
        add(searchButton, gbc);

        // Tendina per la scelta dell'algoritmo
        JLabel algorithmLabel = new JLabel("Algoritmo");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(algorithmLabel, gbc);

        String[] algorithms = {"Algoritmo 1", "Algoritmo 2", "Algoritmo 3", "Algoritmo 4"};
        JComboBox<String> algorithmComboBox = new JComboBox<>(algorithms);
        gbc.gridx = 1;
        add(algorithmComboBox, gbc);

        // Label e campo password
        JLabel passwordLabel = new JLabel("Password");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Pulsanti "Cipher" e "Decipher"
        JButton cipherButton = new JButton("Cipher");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(15, 5, 5, 5); // Spaziatura
        add(cipherButton, gbc);

        JButton decipherButton = new JButton("Decipher");
        gbc.gridx = 1;
        add(decipherButton, gbc);

        // ActionListener per il pulsante "Cipher"
        cipherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logica per cifrare il messaggio
                String message = messageField.getText();
                String password = new String(passwordField.getPassword());
                String algorithm = (String) algorithmComboBox.getSelectedItem();

                // Esegui la cifratura (inserisci qui la logica)
                System.out.println("Cifrando: " + message + " con password: " + password + " usando: " + algorithm);
            }
        });

        // ActionListener per il pulsante "Decipher"
        decipherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logica per decifrare il messaggio
                String message = messageField.getText();
                String password = new String(passwordField.getPassword());
                String algorithm = (String) algorithmComboBox.getSelectedItem();

                // Esegui la decifratura (inserisci qui la logica)
                System.out.println("Decifrando: " + message + " con password: " + password + " usando: " + algorithm);
            }
        });

        // Visualizza la finestra
        setLocationRelativeTo(null); // Centro della finestra
        setVisible(true);

    }
}

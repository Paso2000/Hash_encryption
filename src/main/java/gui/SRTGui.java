package gui;

import utils.PBEAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

public class SRTGui extends JFrame {
    PBEAlgorithm pbe = new PBEAlgorithm();
    public SRTGui() {
        // Imposta il titolo della finestra
        setTitle("SRT tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
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

        final String algorithms[] = {"none", "PBEWithMD5AndDES", "PBEWithMD5AndTripleDES",
                "PBEWithSHA1AndDESede", "PBEWithSHA1AndRC2_40" };
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

        JTextArea outputArea = new JTextArea(5, 10);
        outputArea.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(15, 5, 5, 5); // Spaziatura
        add(outputArea, gbc);

        // ActionListener per il pulsante "Browse"
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(SRTGui.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // Ottieni il file selezionato
                    File selectedFile = fileChooser.getSelectedFile();

                    // Aggiorna il campo di testo con il percorso del file
                    messageField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        // ActionListener per il pulsante "Cipher"
        cipherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logica per cifrare il messaggio
                String message = messageField.getText();
                String password = new String(passwordField.getPassword());
                String algorithm = (String) algorithmComboBox.getSelectedItem();
                try {
                    String output = pbe.Encrypt(message,password,algorithm);
                    System.out.println("Cifrando: " + message + " con password: " + password + " usando: " + algorithm);
                    System.out.println("Otteniamo: "+ output);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                // Esegui la cifratura (inserisci qui la logica)

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
                try {
                    String output = pbe.Decrypt(message,password,algorithm);
                    System.out.println("Decrypting: " + message + " with password: " + password + " using: " + algorithm);
                    System.out.println("Output: "+ output);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Visualizza la finestra
        setLocationRelativeTo(null); // Centro della finestra
        setVisible(true);

    }
}

package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class PBEView extends JFrame {
    private JTextField inputField;
    private JTextField passwordField;
    private JComboBox<String> algorithmsBox;
    private JTextArea resultArea;
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton fileSelectButton;
    private JLabel selectedFileLabel;
    private File selectedFile;  // Per salvare il file selezionato

    public PBEView() {
        setTitle("Symmetric Encryption Tool");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        // Input e password
        inputField = new JTextField();
        passwordField = new JTextField();

        // Algorith choose
        final String[] algorithms = { "PBEWithMD5AndDES", "PBEWithMD5AndTripleDES", "PBEWithSHA1AndDESede", "PBEWithSHA1AndRC2_40" };
        algorithmsBox = new JComboBox<>(algorithms);

        // Output area
        resultArea = new JTextArea(5, 20);
        resultArea.setEditable(false);

        // Pulsanti per cifrare e decifrare
        encryptButton = new JButton("Cifra");
        decryptButton = new JButton("Decifra");

        // Pulsante per selezionare file
        fileSelectButton = new JButton("Seleziona File");
        selectedFileLabel = new JLabel("Nessun file selezionato");

        // Aggiunta dei componenti alla finestra
        add(new JLabel("Inserisci il testo:"));
        add(inputField);
        add(new JLabel("Inserisci la password:"));
        add(passwordField);
        add(new JLabel("Algoritmo:"));
        add(algorithmsBox);
        add(fileSelectButton);
        add(selectedFileLabel);  // Per mostrare il file selezionato
        add(encryptButton);
        add(decryptButton);
        add(new JLabel("Risultato:"));
        add(new JScrollPane(resultArea));

        // Mostra la finestra
        setVisible(true);
    }

    // Metodi per ottenere i dati dalla GUI
    public String getInputText() {
        return inputField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public String getAlgorithm() {
        return (String) algorithmsBox.getSelectedItem();
    }

    public File getSelectedFile() {
        return selectedFile;  // Restituisce il file selezionato
    }

    // Metodo per mostrare il file selezionato
    public void setSelectedFile(File file) {
        this.selectedFile = file;
        selectedFileLabel.setText(file != null ? file.getName() : "Nessun file selezionato");
    }

    // Metodo per mostrare il risultato
    public void setResult(String result) {
        resultArea.setText(result);
    }

    // Listener per i pulsanti
    public void addEncryptButtonListener(ActionListener listener) {
        encryptButton.addActionListener(listener);
    }

    public void addDecryptButtonListener(ActionListener listener) {
        decryptButton.addActionListener(listener);
    }

    // Listener per il pulsante di selezione del file
    public void addFileSelectButtonListener(ActionListener listener) {
        fileSelectButton.addActionListener(listener);
    }

    // Metodo per mostrare messaggi di errore
    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
}

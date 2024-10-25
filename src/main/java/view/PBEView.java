package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class PBEView extends JFrame {
    private JTextField inputField;
    private JTextField passwordField;
    private JComboBox<String> algorithmsBox;
    private JTextArea resultArea;
    private JButton encryptButton;
    private JButton decryptButton;

    public PBEView() {
    setTitle("symmetric encryption tool");
    setSize(600, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridLayout(6, 2));

    //input e password
    inputField = new JTextField();
    passwordField = new JTextField();
    //algorith choose
    final String[] algorithms = { "PBEWithMD5AndDES", "PBEWithMD5AndTripleDES", "PBEWithSHA1AndDESede", "PBEWithSHA1AndRC2_40" };
    algorithmsBox = new JComboBox<>(algorithms); // Inizializza l'algorithmsBox con il campo di istanza

        //output area
    resultArea = new JTextArea(5, 20);
    resultArea.setEditable(false);

    // Pulsanti per cifrare e decifrare
    encryptButton = new JButton("Cifra");
    decryptButton = new JButton("Decifra");

    // Aggiunta dei componenti alla finestra
    add(new JLabel("Inserisci il testo:"));
    add(inputField);
    add(new JLabel("Inserisci la password:"));
    add(passwordField);
    add(new JLabel("Algoritmo:"));
    add(algorithmsBox);
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
        System.out.println((String) this.algorithmsBox.getSelectedItem());
        return (String) this.algorithmsBox.getSelectedItem();
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

    // Metodo per mostrare messaggi di errore
    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
}

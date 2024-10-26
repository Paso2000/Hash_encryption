package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Used MVC pattern to manage GUI
 * this class is the pattern view, where are defined the components GUI.
 */
public class PBEView extends JFrame {
    private JTextField inputField;
    private JTextField passwordField;
    private JComboBox<String> algorithmsBox;
    private JTextArea resultArea;
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton fileSelectButton;
    private JLabel selectedFileLabel;
    private File selectedFile;

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

        // encrypt/decrypt button
        encryptButton = new JButton("encrypt");
        decryptButton = new JButton("decrypt");

        // button to select a file
        fileSelectButton = new JButton("Select a File");
        selectedFileLabel = new JLabel("No file selected");

        //add components in the panel
        add(new JLabel("Insert text:"));
        add(inputField);
        add(new JLabel("Insert password:"));
        add(passwordField);
        add(new JLabel("Algorithms:"));
        add(algorithmsBox);
        add(fileSelectButton);
        add(selectedFileLabel);
        add(encryptButton);
        add(decryptButton);
        add(new JLabel("Output:"));
        add(new JScrollPane(resultArea));

        setVisible(true);
    }

    // Methods to get GUI info
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
        return selectedFile;
    }

    // Methods to select a file
    public void setSelectedFile(File file) {
        this.selectedFile = file;
        selectedFileLabel.setText(file != null ? file.getName() : "No file selected");
    }

    // Methods to view output
    public void setResult(String result) {
        resultArea.setText(result);
    }

    // button listener
    public void addEncryptButtonListener(ActionListener listener) {
        encryptButton.addActionListener(listener);
    }

    public void addDecryptButtonListener(ActionListener listener) {
        decryptButton.addActionListener(listener);
    }

    public void addFileSelectButtonListener(ActionListener listener) {
        fileSelectButton.addActionListener(listener);
    }

    // Methods to view error message
    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
}

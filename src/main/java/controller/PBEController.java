package controller;

import model.PBEAlgorithm;
import utils.PBEAlgorithmFile;
import view.PBEView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Used MVC pattern to manage GUI
 * this class is the pattern controller, it connects to the view and uses
 * the logic of the model (PBEAlgorithm, PBEAlgorithmFile).
 */
public class PBEController {

    private PBEAlgorithm pbeAlgorithm;
    private PBEAlgorithmFile pbeAlgorithmFile;
    private PBEView view;

    public PBEController(PBEAlgorithm pbeAlgorithm,PBEAlgorithmFile pbeAlgorithmFile,  PBEView view) {
        this.pbeAlgorithm = pbeAlgorithm;
        this.pbeAlgorithmFile = pbeAlgorithmFile ;
        this.view = view;

        // connect listener to button
        this.view.addEncryptButtonListener(new EncryptButtonListener());
        this.view.addDecryptButtonListener(new DecryptButtonListener());
        this.view.addFileSelectButtonListener(new FileSelectButtonListener());
    }

    // Cipher controller button
    class EncryptButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String plaintext = view.getInputText();
                String password = view.getPassword();
                String algorithm = view.getAlgorithm();
                File selectedFile = view.getSelectedFile();
                if (selectedFile != null) {
                    pbeAlgorithmFile.Encrypt(selectedFile, password, algorithm);
                    view.setResult("Successfully encrypted file: " + selectedFile.getPath() +
                            "\nSize: " + selectedFile.length() + "byte");
                } else {
                    String encryptedText = pbeAlgorithm.Encrypt(plaintext, password, algorithm);
                    view.setResult("Ciphertext: " + encryptedText);
                }
            } catch (Exception ex) {
                view.showError("Error during encryption: " + ex.getMessage());
            }
        }
    }

    // Decipher controller button
    class DecryptButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String encryptedText = view.getInputText();
                String password = view.getPassword();
                String algorithm = view.getAlgorithm();
                File selectedFile = view.getSelectedFile();

                if (selectedFile != null) {
                    pbeAlgorithmFile.Decrypt(selectedFile, password, algorithm);
                    view.setResult("Successfully encrypted file: " + selectedFile.getPath() +
                            "\nSize: " + selectedFile.length() + "byte");
                } else {
                    String decryptedText = pbeAlgorithm.Decrypt(encryptedText, password, algorithm);
                    view.setResult("Plaintext: " + decryptedText);
                }
            } catch (Exception ex) {
                view.showError("Error during encryption: " + ex.getMessage());
            }
        }
    }

    // Listener to select a file
    class FileSelectButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                view.setSelectedFile(selectedFile);
            }
        }
    }
}

package controller;

import model.PBEAlgorithm;
import utils.PBEAlgorithmFile;
import view.PBEView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PBEController {

    private PBEAlgorithm pbeAlgorithm;
    private PBEAlgorithmFile pbeAlgorithmFile;
    private PBEView view;

    public PBEController(PBEAlgorithm pbeAlgorithm,PBEAlgorithmFile pbeAlgorithmFile,  PBEView view) {
        this.pbeAlgorithm = pbeAlgorithm;
        this.pbeAlgorithmFile = pbeAlgorithmFile ;
        this.view = view;

        // Collegare i listener ai pulsanti
        this.view.addEncryptButtonListener(new EncryptButtonListener());
        this.view.addDecryptButtonListener(new DecryptButtonListener());
        this.view.addFileSelectButtonListener(new FileSelectButtonListener());
    }

    // Listener per la cifratura
    class EncryptButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String plaintext = view.getInputText();
                String password = view.getPassword();
                String algorithm = view.getAlgorithm();
                File selectedFile = view.getSelectedFile();

                // Cifra il testo o il file
                if (selectedFile != null) {
                    // Logica per cifrare il file (da implementare)
                    pbeAlgorithmFile.Encrypt(selectedFile, password, algorithm);
                    view.setResult("File cifrato: " + selectedFile.getName());
                } else {
                    String encryptedText = pbeAlgorithm.Encrypt(plaintext, password, algorithm);
                    view.setResult("Testo cifrato: " + encryptedText);
                }
            } catch (Exception ex) {
                view.showError("Errore durante la cifratura: " + ex.getMessage());
            }
        }
    }

    // Listener per la decifratura
    class DecryptButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String encryptedText = view.getInputText();
                String password = view.getPassword();
                String algorithm = view.getAlgorithm();
                File selectedFile = view.getSelectedFile();

                // Decifra il testo o il file
                if (selectedFile != null) {
                    // Logica per decifrare il file (da implementare)
                    pbeAlgorithmFile.Decrypt(selectedFile, password, algorithm);
                    view.setResult("File decifrato: " + selectedFile.getName());
                } else {
                    String decryptedText = pbeAlgorithm.Decrypt(encryptedText, password, algorithm);
                    view.setResult("Testo decifrato: " + decryptedText);
                }
            } catch (Exception ex) {
                view.showError("Errore durante la decifratura: " + ex.getMessage());
            }
        }
    }

    // Listener per la selezione del file
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

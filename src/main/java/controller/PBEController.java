package controller;

import model.PBEAlgorithm;
import view.PBEView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PBEController {

    private PBEAlgorithm pbeAlgorithm;
    private PBEView view;

    public PBEController(PBEAlgorithm pbeAlgorithm, PBEView view) {
        this.pbeAlgorithm = pbeAlgorithm;
        this.view = view;

        // Collegare i listener ai pulsanti
        this.view.addEncryptButtonListener(new EncryptButtonListener());
        this.view.addDecryptButtonListener(new DecryptButtonListener());
    }

    // Listener per la cifratura
    class EncryptButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String plaintext = view.getInputText();
                String password = view.getPassword();
                String algorithm = view.getAlgorithm();

                String encryptedText = pbeAlgorithm.Encrypt(plaintext, password, algorithm);
                view.setResult("Testo cifrato: " + encryptedText);
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

                String decryptedText = pbeAlgorithm.Decrypt(encryptedText, password, algorithm);
                view.setResult("Testo decifrato: " + decryptedText);
            } catch (Exception ex) {
                view.showError("Errore durante la decifratura: " + ex.getMessage());
            }
        }
    }
}

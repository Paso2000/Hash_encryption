package controller;

import model.HashAlgorithm;
import model.HashAlgorithmFile;
import model.PBEAlgorithm;
import model.PBEAlgorithmFile;
import org.bouncycastle.util.encoders.Hex;
import view.View;

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

    private HashAlgorithmFile hashAlgorithmFile;
    private HashAlgorithm hashAlgorithm;
    private View view;
    private byte[] result;

    public PBEController(PBEAlgorithm pbeAlgorithm, PBEAlgorithmFile pbeAlgorithmFile, View view, HashAlgorithm hashAlgorithm, HashAlgorithmFile hashAlgorithmFile) {
        this.pbeAlgorithm = pbeAlgorithm;
        this.pbeAlgorithmFile = pbeAlgorithmFile;
        this.hashAlgorithm= hashAlgorithm;
        this.hashAlgorithmFile=hashAlgorithmFile;
        this.view = view;

        // connect listener to button
        this.view.addEncryptButtonListener(new EncryptButtonListener());
        this.view.addDecryptButtonListener(new DecryptButtonListener());
        //this.view.addFileSelectButtonListener(new FileSelectButtonListener());
        this.view.addMessageHashButtonListener(new MessageHashButtonListener());
    }

    class MessageHashButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String plaintext = view.getInputText();
            String value = view.getHashValue();
            String hashFunction = view.getHashAlgorithm();
            System.out.println(plaintext + hashFunction);
            try {
                result = hashAlgorithm.protectMessageHash(plaintext,hashFunction, value);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            view.setResult(Hex.toHexString(result));


        }
    }

    // Cipher controller button
    class EncryptButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String plaintext = view.getInputText();
                String password = view.getHashValue();
                String SymmetricAlgorithm = view.getSymmetricAlgorithm();
                File selectedFile = view.getSelectedFile();
                if (selectedFile != null) {
                    pbeAlgorithmFile.Encrypt(selectedFile, password, SymmetricAlgorithm);
                    view.setResult("Successfully encrypted file: " + selectedFile.getPath() +
                            "\nSize: " + selectedFile.length() + "byte");
                } else {
                    String encryptedText = pbeAlgorithm.Encrypt(plaintext, password, SymmetricAlgorithm);
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
                String password = view.getHashValue();
                String SymmetricAlgorithm = view.getSymmetricAlgorithm();
                File selectedFile = view.getSelectedFile();

                if (selectedFile != null) {
                    pbeAlgorithmFile.Decrypt(selectedFile, password, SymmetricAlgorithm);
                    view.setResult("Successfully encrypted file: " + selectedFile.getPath() +
                            "\nSize: " + selectedFile.length() + "byte");
                } else {
                    String decryptedText = pbeAlgorithm.Decrypt(encryptedText, password, SymmetricAlgorithm);
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

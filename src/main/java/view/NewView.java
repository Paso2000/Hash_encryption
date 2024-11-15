package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class NewView extends JFrame{
        private File selectedFile;
        private JTextArea textArea = new JTextArea();
        private JComboBox<String> comboCifrado;
        private JComboBox<String> comboHash;
        private JMenuItem cipher;
        private JMenuItem decipher;
        private JMenuItem protegerWithHash;
        private JMenuItem verificarHash;
        private JMenuItem protegerMensajeWithHash;
        private JMenuItem verificarMensajeHash;
        private JMenuItem Exit;




    public NewView() {
            // Crea il frame
            JFrame frame = new JFrame("Practice 3 of SRT");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            // Crea la barra dei menu
            JMenuBar menuBar = new JMenuBar();


            // Crea il menu
            JMenu menuFile = new JMenu("File");
            JMenu menuOption = new JMenu("Option");
            menuBar.add(menuFile);
            menuBar.add(menuOption);


            // Crea le voci di menu
            cipher = new JMenuItem("Cipher");
            cipher.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

             decipher = new JMenuItem("Decipher");
            decipher.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));

            protegerWithHash = new JMenuItem("Protect with hash");
            protegerWithHash.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
            protegerWithHash.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Crea un JFileChooser per selezionare un file
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Select the file that has to be protected with hash");

                    // Mostra la finestra di dialogo per la scelta del file
                    int userSelection = fileChooser.showOpenDialog(frame);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        // Ottieni il file selezionato
                        File fileToHash = fileChooser.getSelectedFile();
                        JOptionPane.showMessageDialog(frame, "Selected File: " + fileToHash.getAbsolutePath(), "Selected File", JOptionPane.INFORMATION_MESSAGE);

                        // Qui puoi implementare la logica per calcolare l'hash del file selezionato
                    }
                }
            });

            verificarHash = new JMenuItem("Verify hash");
            verificarHash.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

             protegerMensajeWithHash = new JMenuItem("Protect message with hash");
             verificarMensajeHash = new JMenuItem("Verify message hash");

             Exit = new JMenuItem("Exit");
            Exit.addActionListener(e -> System.exit(0));

            // Aggiungi le voci di menu al menu "Fichero"
            menuFile.add(cipher);
            menuFile.add(decipher);
            menuFile.add(protegerWithHash);
            menuFile.add(verificarHash);
            menuFile.add(protegerMensajeWithHash);
            menuFile.add(verificarMensajeHash);
            menuFile.addSeparator(); // Aggiunge una linea di separazione
            menuFile.add(Exit);

            JMenuItem algorithm = new JMenuItem("Algorithm");
            algorithm.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Crea e mostra il dialogo di configurazione
                    JDialog dialog = new JDialog(frame, "Opciones", true);
                    dialog.setLayout(new GridLayout(4, 1, 10, 10));
                    dialog.setSize(300, 200);
                    dialog.setLocationRelativeTo(frame);

                    // Componenti per Algoritmo Cifrado
                    JLabel labelCifrado = new JLabel("Algoritmo Cifrado");
                    comboCifrado = new JComboBox<>(new String[] {
                            "PBEWithMD5AndDES", "PBEWithMD5AndTripleDES", "PBEWithSHA1AndDESede", "PBEWithSHA1AndRC2_40"
                    });

                    // Componenti per Algoritmo Hash/HMac
                    JLabel labelHash = new JLabel("Algoritmo Hash/HMac");
                    comboHash = new JComboBox<>(new String[] {
                            "MD2", "MD5", "SHA-1", "SHA-256", "SHA-384", "SHA-512",
                            "HmacMD5", "HmacSHA1", "HmacSHA256", "HmacSHA384", "HmacSHA512"
                    });

                    // Aggiungi i componenti al dialogo
                    dialog.add(labelCifrado);
                    dialog.add(comboCifrado);
                    dialog.add(labelHash);
                    dialog.add(comboHash);

                    // Rendi visibile il dialogo
                    dialog.setVisible(true);
                }
            });
            menuOption.add(algorithm);

            textArea.setLineWrap(true); // Permette l'andata a capo automatica
            textArea.setWrapStyleWord(true); // Andata a capo sui confini delle parole

            // Aggiungi la JTextArea a uno JScrollPane per abilitarne lo scorrimento
            JScrollPane scrollPane = new JScrollPane(textArea);
            frame.add(scrollPane, BorderLayout.CENTER);


            // Aggiungi la barra dei menu al frame
            frame.setJMenuBar(menuBar);

            // Rendi visibile il frame
            frame.setVisible(true);


        }
    public File getSelectedFile() {
        return selectedFile;
    }

    // Methods to select a file
    public void setSelectedFile(File file) {
        this.selectedFile = file;
    }
    public String getInputText() {
        return textArea.getText();
    }

   // public String getPassword() {
    //  return passwordField.getText();
    //}

    public String getSymmetricAlgorithm() {
        return (String) comboCifrado.getSelectedItem();
    }

    public String getHashAlgorithm(){return (String) comboHash.getSelectedItem(); }


    // Methods to view output
    public void setResult(String result) {
        textArea.setText(result);
    }

    // button listener
    public void addEncryptButtonListener(ActionListener listener) {
        cipher.addActionListener(listener);
    }

    public void addDecryptButtonListener(ActionListener listener) {
        decipher.addActionListener(listener);
    }
    public void addMessageHashButtonListener(ActionListener listener) {
        protegerMensajeWithHash.addActionListener(listener);
    }

   // public void addFileSelectButtonListener(ActionListener listener) {
    //    fileSelectButton.addActionListener(listener);
    //}

    // Methods to view error message
    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
}

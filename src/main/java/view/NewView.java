package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class NewView {



        public static void main(String[] args) {
            // Crea il frame
            JFrame frame = new JFrame("Practice 3 of SRT");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            // Crea la barra dei menu
            JMenuBar menuBar = new JMenuBar();


            // Crea il menu
            JMenu menuFichero = new JMenu("File");
            JMenu menuOption = new JMenu("Option");
            menuBar.add(menuFichero);
            menuBar.add(menuOption);


            // Crea le voci di menu
            JMenuItem cifrar = new JMenuItem("Cipher");
            cifrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

            JMenuItem descifrar = new JMenuItem("Decipher");
            descifrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));

            JMenuItem protegerConHash = new JMenuItem("Protect with hash");
            protegerConHash.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));

            JMenuItem verificarHash = new JMenuItem("Verify hash");
            verificarHash.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

            JMenuItem protegerMensajeConHash = new JMenuItem("Protect message with hash");
            JMenuItem verificarMensajeHash = new JMenuItem("Verify message hash");

            JMenuItem salir = new JMenuItem("Exit");
            salir.addActionListener(e -> System.exit(0));

            // Aggiungi le voci di menu al menu "Fichero"
            menuFichero.add(cifrar);
            menuFichero.add(descifrar);
            menuFichero.add(protegerConHash);
            menuFichero.add(verificarHash);
            menuFichero.add(protegerMensajeConHash);
            menuFichero.add(verificarMensajeHash);
            menuFichero.addSeparator(); // Aggiunge una linea di separazione
            menuFichero.add(salir);

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
                    JComboBox<String> comboCifrado = new JComboBox<>(new String[] {
                            "PBEWithMD5AndDES", "PBEWithMD5AndTripleDES", "PBEWithSHA1AndDESede", "PBEWithSHA1AndRC2_40"
                    });

                    // Componenti per Algoritmo Hash/HMac
                    JLabel labelHash = new JLabel("Algoritmo Hash/HMac");
                    JComboBox<String> comboHash = new JComboBox<>(new String[] {
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

            JTextArea textArea = new JTextArea();
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
}

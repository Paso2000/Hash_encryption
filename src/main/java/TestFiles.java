import utils.PBEAlgorithmFile;
import java.io.File;

public class TestFiles {
    public static void main(String[] args) {
        try {
            // Crea un'istanza della classe PBEAlgorithmFile
            PBEAlgorithmFile pbeFile = new PBEAlgorithmFile();

            // Percorso del file PDF da crittografare
            File pdfFile = new File("C:/Users/giamm/Desktop/Tema 1.pdf");  // Sostituisci con il percorso del tuo file PDF

            // Specifica la password e l'algoritmo di crittografia
            String password = "yourpassword";  // Sostituisci con la tua password
            String algorithm = "PBEWithMD5AndDES";  // Sostituisci con l'algoritmo che desideri usare

            // Crittografa il file PDF
            System.out.println("Inizio crittografia del file...");
            pbeFile.Encrypt(pdfFile, password, algorithm);
            System.out.println("File crittografato con successo!");

            // Percorso del file crittografato (stesso percorso del PDF ma con estensione .CIF)
            File encryptedFile = new File(pdfFile.getParent() + File.separator + pdfFile.getName().replaceFirst("[.][^.]+$", "") + ".CIF");

            // Decifra il file crittografato
            System.out.println("Inizio decifratura del file...");
            pbeFile.Decrypt(encryptedFile, password, algorithm);
            System.out.println("File decifrato con successo!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

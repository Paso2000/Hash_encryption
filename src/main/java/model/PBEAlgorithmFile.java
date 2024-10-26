package utils;

import org.bouncycastle.jcajce.io.CipherOutputStream;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class PBEAlgorithmFile {
    private int iterationCount = 100;
    private byte[] salt = Hex.decode("0102030405060708");

    // Metodo per crittografare un file e salvarlo come .CIF
    public void Encrypt(File input, String passwd, String algorithm) throws Exception {
        Path inputPath = input.toPath();
        byte[] fileBytes = Files.readAllBytes(inputPath);

        // Generazione della chiave segreta dalla password
        PBEKeySpec pbeKeySpec = new PBEKeySpec(passwd.toCharArray());
        SecretKeyFactory keyFact = SecretKeyFactory.getInstance(algorithm);
        SecretKey pbeKey = keyFact.generateSecret(pbeKeySpec);

        // Creazione della configurazione del cifrario
        PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, iterationCount);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);

        // Creazione del percorso per il file cifrato con estensione .CIF
        String encryptedFilePath = input.getParent() + File.separator + input.getName().replaceFirst("[.][^.]+$", "") + ".CIF";
        File encryptedFile = new File(encryptedFilePath);

        try (FileOutputStream fileOut = new FileOutputStream(encryptedFile);
             CipherOutputStream cOut = new CipherOutputStream(fileOut, cipher)) {
            cOut.write(fileBytes);
        }

        System.out.println("File cifrato salvato come: " + encryptedFile.getAbsolutePath());
    }

    // Metodo per decifrare un file .CIF
    public void Decrypt(File encryptedInput, String passwd, String algorithm) throws Exception {
        Path encryptedPath = encryptedInput.toPath();
        byte[] encryptedBytes = Files.readAllBytes(encryptedPath);

        // Generazione della chiave segreta dalla password
        PBEKeySpec pbeKeySpec = new PBEKeySpec(passwd.toCharArray());
        SecretKeyFactory keyFact = SecretKeyFactory.getInstance(algorithm);
        SecretKey pbeKey = keyFact.generateSecret(pbeKeySpec);

        // Creazione della configurazione del cifrario
        PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, iterationCount);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);

        // Creazione del percorso per il file decifrato
        String decryptedFilePath = encryptedInput.getParent() + File.separator + encryptedInput.getName().replaceFirst("[.][^.]+$", "") + "_decrypted.pdf";
        File decryptedFile = new File(decryptedFilePath);

        try (FileOutputStream fileOut = new FileOutputStream(decryptedFile);
             CipherInputStream cIn = new CipherInputStream(new ByteArrayInputStream(encryptedBytes), cipher)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = cIn.read(buffer)) != -1) {
                fileOut.write(buffer, 0, bytesRead);
            }

            System.out.println("File decifrato salvato come: " + decryptedFile.getAbsolutePath());
        }
    }
}

package model;

import org.bouncycastle.jcajce.io.CipherOutputStream;
import utils.Header;
import utils.Options;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Base64;

public class HashAlgorithmFile {
    public void hashFileEncrypt(File input, String passwd, String algorithm) throws Exception {
        Path inputPath = input.toPath();
        byte[] fileBytes = Files.readAllBytes(inputPath);

        // Generating symmetric key for the chosen algorithm


        // creating the path for the file .CIF
        String encryptedFilePath = input.getParent() + File.separator + input.getName().replaceFirst("[.][^.]+$", "") + ".CIF";
        File encryptedFile = new File(encryptedFilePath);
        //creating the header with the correspondents data
        InputStream inputStream = new ByteArrayInputStream(fileBytes);

        // Creazione dell'istanza MessageDigest per SHA-256
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.update(passwd.getBytes());
        Header header = new Header(Options.OP_SIGNED, Options.OP_NONE_ALGORITHM, algorithm, digest.digest());
        // Creazione di un DigestInputStream per calcolare l'hash durante la lettura
        try (DigestInputStream digestInputStream = new DigestInputStream(inputStream, digest)) {
            byte[] buffer = new byte[4096];
            while (digestInputStream.read(buffer) != -1) {

            }
            FileOutputStream fileOut = new FileOutputStream(encryptedFile);
            fileOut.write(fileBytes);
            header.save(fileOut);
            fileOut.close();

            System.out.println("File cifrato salvato come: " + encryptedFile.getAbsolutePath());
        }
    }
    /*public void hashVerifyFile(File encryptedInput, String passwd, String algorithm) throws Exception {
        Path encryptedPath = encryptedInput.toPath();
        byte[] encryptedBytes = Files.readAllBytes(encryptedPath);

        // Generating symmetric key for the chosen algorithm


        // Creating the path for the decrypted file
        String decryptedFilePath = encryptedInput.getParent() + File.separator + encryptedInput.getName().replaceFirst("[.][^.]+$", "") + "_decrypted.cla";
        File decryptedFile = new File(decryptedFilePath);

        //create the file outputStream
        try (FileOutputStream fileOut = new FileOutputStream(decryptedFile);
             //create the CipherInputStream using the cipher and an ByteArrayInputStream that contains encryptedBytes
             DigestInputStream cIn = new DigestInputStream(new FileInputStream(decryptedFile), digest)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            //writes every byte on the buffer decrypting it
            while ((bytesRead = cIn.read(buffer)) != -1) {
                fileOut.write(buffer, 0, bytesRead);
            }
            //for debugging
            //System.out.println("Decrypted file path: " + decryptedFile.getAbsolutePath());
        }
    }*/

}

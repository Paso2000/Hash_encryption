package model;

import org.apache.commons.io.output.CountingOutputStream;
import org.bouncycastle.jcajce.io.CipherOutputStream;
import org.bouncycastle.util.encoders.Hex;
import utils.Header;
import utils.Options;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Objects;

public class HashAlgorithmFile {
    private int iterationCount = 100;
    private byte[] salt = Hex.decode("0102030405060708");

    private long bytetoDelete;

    private int macLength = 64;
    SecretKey macKey = new SecretKeySpec(
            Hex.decode(
                    "2ccd85dfc8d18cb5d84fef4b19855469" +
                            "9fece6e8692c9147b0da983f5b7bd413"), "HmacSHA256");
    public void hashFileEncrypt(File input, String password, String algorithm) throws Exception {
        Path inputPath = input.toPath();
        byte[] fileBytes = Files.readAllBytes(inputPath);
        // Generating symmetric key for the chosen algorithm

        // creating the path for the file .CIF
        String encryptedFilePath = input.getParent() + File.separator + input.getName().replaceFirst("[.][^.]+$", "") + ".CIF";
        File encryptedFile = new File(encryptedFilePath);
        if (Objects.equals(algorithm, "HmacMD5") || Objects.equals(algorithm, "HmacSHA1") || Objects.equals(algorithm, "HmacSHA256") || Objects.equals(algorithm, "HmacSHA384") || Objects.equals(algorithm, "HmacSHA512")) {
            try {
                Mac mac = Mac.getInstance(algorithm, "BC");
                SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, macLength);
                SecretKey secretKey = skf.generateSecret(spec);

                mac.init(secretKey);
                mac.init(macKey);
                try (ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes))
                {byte[] buffer = new byte[4096]; // Buffer per la lettura a blocchi
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        // Aggiorna il MAC con i dati letti
                        mac.update(buffer, 0, bytesRead);
                    }
                }

                // Ottieni il MAC finale
                byte[] macResult = mac.doFinal();
                Header header = new Header(Options.OP_SIGNED, Options.OP_NONE_ALGORITHM, algorithm, macResult);
                ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
                CountingOutputStream outputStream = new CountingOutputStream(arrayOut);
                header.save(outputStream);
                bytetoDelete = outputStream.getByteCount();
                outputStream.write(fileBytes);
                outputStream.close();
                System.out.println(outputStream.getByteCount());
                // Stampa il risultato del MAC in formato esadecimale

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            //creating the header with the correspondents data
            InputStream inputStream = new ByteArrayInputStream(fileBytes);

            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(password.getBytes());
            // Creazione di un DigestInputStream per calcolare l'hash durante la lettura
            try (DigestInputStream digestInputStream = new DigestInputStream(inputStream, digest)) {
                byte[] buffer = new byte[4096];
                while (digestInputStream.read(buffer) != -1) {

                }
                Header header = new Header(Options.OP_SIGNED, Options.OP_NONE_ALGORITHM, algorithm, digest.digest());
                FileOutputStream fileOut = new FileOutputStream(encryptedFile);
                CountingOutputStream outputStream = new CountingOutputStream(fileOut);
                header.save(outputStream);
                System.out.println(outputStream.getByteCount());
                outputStream.write(fileBytes);
                outputStream.close();
                System.out.println("File cifrato salvato come: " + encryptedFile.getAbsolutePath());
            }
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

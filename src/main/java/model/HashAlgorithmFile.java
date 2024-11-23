package model;

import org.apache.commons.io.output.CountingOutputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import utils.Header;
import utils.Options;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Security;
import java.util.Arrays;

public class HashAlgorithmFile {
    private long bytetoDelete;
    private HashAlgorithm hashAlgorithm = new HashAlgorithm();

    public String[] hashFileEncrypt(File input, String algorithm, String password) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Path inputPath = input.toPath();
        byte[] fileBytes = Files.readAllBytes(inputPath);
        // Generating symmetric key for the chosen algorithm

        // creating the path for the file .hsh
        String encryptedFilePath = input.getParent() + File.separator + input.getName().replaceFirst("[.][^.]+$", "") + ".hsh";
        File encryptedFile = new File(encryptedFilePath);
       byte[] hash = hashAlgorithm.hashBytes(fileBytes, algorithm, password);
       Header header = new Header(Options.OP_SIGNED, Options.OP_NONE_ALGORITHM, algorithm, hash);
       System.out.println(Hex.toHexString(header.getData()));
       FileOutputStream fileOut = new FileOutputStream(encryptedFile);
       CountingOutputStream outputStream = new CountingOutputStream(fileOut);
       header.save(outputStream);
       bytetoDelete=outputStream.getByteCount();
       System.out.println(outputStream.getByteCount());
       outputStream.write(fileBytes);
       outputStream.close();
       System.out.println("File cifrato salvato come: " + encryptedFile.getAbsolutePath());
       return new String[] {Hex.toHexString(hash),algorithm};
    }
    public String[] hashVerifyFile(File encryptedInput, String algorithm , String password) throws Exception {
        Path encryptedPath = encryptedInput.toPath();
        byte[] bytes = Files.readAllBytes(encryptedPath);
        byte[] fileBytes = Arrays.copyOfRange(bytes, (int) bytetoDelete, bytes.length);
        String hash = Hex.toHexString(hashAlgorithm.hashBytes(fileBytes,algorithm,password));
        Header header = new Header();
        String VerifyedFilePath = encryptedInput.getParent() + File.separator + encryptedInput.getName().replaceFirst("[.][^.]+$", "") + "_decrypted.cla";
        File VerifyedFile = new File(VerifyedFilePath);
        // Generating symmetric key for the chosen algorithm
        if (bytes.length > bytetoDelete) {
            try (FileOutputStream fileOut = new FileOutputStream(VerifyedFile);
                 //create the CipherInputStream using the cipher and an ByteArrayInputStream that contains bytes
                 ByteArrayInputStream cIn = new ByteArrayInputStream(bytes)) {
                header.load(cIn);
                String strHeaderHash = Hex.toHexString(header.getData());
                byte[] buffer = new byte[4096];
                int bytesRead;
                //writes every byte on the buffer decrypting it
                while ((bytesRead = cIn.read(buffer)) != -1) {
                    fileOut.write(buffer, 0, (bytesRead));
                }
                return new String[]{strHeaderHash, hash};
            }
        }else{
                    System.out.println("I dati sono meno di 30 byte, non è possibile rimuovere.");
                }
                return new String[]{("File not accepted")};
            }
        }

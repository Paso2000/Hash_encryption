package utils;

import org.bouncycastle.crypto.io.InvalidCipherTextIOException;
import org.bouncycastle.jcajce.io.CipherInputStream;
import org.bouncycastle.jcajce.io.CipherOutputStream;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.io.Streams;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Base64;
import java.nio.file.Files;


public class PBEAlgorithmFile {
    private int iterationCount = 100;
    private byte[] salt = Hex.decode("0102030405060708");


    public void Encrypt(File input, String passwd, String algorithm) throws Exception {

        Path path = input.toPath();
        // Leggi il contenuto del file e restituisci come array di byte
        byte[] fileByte=  Files.readAllBytes(path);
        PBEKeySpec pbeKeySpec = new PBEKeySpec(passwd.toCharArray());
        SecretKeyFactory keyFact = SecretKeyFactory.getInstance(algorithm);
        SecretKey pbeKey = keyFact.generateSecret(pbeKeySpec);

        // Usa PBEParameterSpec (con salt e iteration count)
        PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, iterationCount);

        // Inizializza il cipher con padding PKCS5Padding
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);

        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        CipherOutputStream cOut = new CipherOutputStream(bOut, cipher);
        cOut.write(fileByte);
        cOut.close();

        Header header= new Header(Options.OP_SYMMETRIC_CIPHER,algorithm,Options.OP_NONE_ALGORITHM, bOut.toByteArray());
        header.save(cOut);
    }

    public void Decrypt(File encryptedInput, String passwd, String algorithm) throws Exception {
        try {
            Path path = encryptedInput.toPath();
            // Leggi il contenuto del file e restituisci come array di byte
            byte[] fileByte=  Files.readAllBytes(path);
            // Decodifica l'input cifrato da base64
            //byte[] encryptedBytes = Base64.getDecoder().decode(encryptedInput);

            PBEKeySpec pbeKeySpec = new PBEKeySpec(passwd.toCharArray());
            SecretKeyFactory keyFact = SecretKeyFactory.getInstance(algorithm);
            SecretKey pbeKey = keyFact.generateSecret(pbeKeySpec);

            // Usa lo stesso PBEParameterSpec (con salt e iteration count)
            PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, iterationCount);

            // Inizializza il cipher per la decifratura con padding PKCS5Padding
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);

            ByteArrayInputStream bIn = new ByteArrayInputStream(fileByte);
            CipherInputStream cIn = new CipherInputStream(bIn, cipher);
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            Streams.pipeAll(cIn, bOut);
            cIn.close();
            Header header= new Header(Options.OP_SYMMETRIC_CIPHER,algorithm,Options.OP_NONE_ALGORITHM, bOut.toByteArray());
            header.load(cIn);
        } catch (IllegalArgumentException e) {
            throw new Exception("Errore nella decodifica Base64 o nei parametri di decifratura.", e);
        } catch (InvalidCipherTextIOException e) {
            throw new Exception("Errore nella decifratura: testo cifrato modificato o parametri errati.", e);
        }
    }

}

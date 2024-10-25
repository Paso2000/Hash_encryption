package utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.bouncycastle.crypto.io.InvalidCipherTextIOException;
import org.bouncycastle.jcajce.io.CipherInputStream;
import org.bouncycastle.jcajce.io.CipherOutputStream;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.io.Streams;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.util.Base64;

/**
 * A simple example of PBE mode.
 */
public class PBEAlgorithm
{
    private int iterationCount = 100;
    private byte[] salt = Hex.decode("0102030405060708");
    private Cipher cipher;
    private SecretKey pbeKey;
    PBEParameterSpec pbeParamSpec;

        public void Test(String input){
            byte[] inpu = Hex.decode("a0a1a2a3a4a5a6a7a0a1a2a3a4a5a6a7");
            // Genera un IV fisso (o casuale ma riutilizzabile)
            System.out.println("input    : " + Hex.toHexString(inpu));

        }

    public String Encrypt(String input, String passwd, String algorithm) throws Exception {
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
        cOut.write(input.getBytes(StandardCharsets.UTF_8));
        cOut.close();

        return Base64.getEncoder().encodeToString(bOut.toByteArray());
    }

    public String Decrypt(String encryptedInput, String passwd, String algorithm) throws Exception {
        try {
            // Decodifica l'input cifrato da base64
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedInput);

            PBEKeySpec pbeKeySpec = new PBEKeySpec(passwd.toCharArray());
            SecretKeyFactory keyFact = SecretKeyFactory.getInstance(algorithm);
            SecretKey pbeKey = keyFact.generateSecret(pbeKeySpec);

            // Usa lo stesso PBEParameterSpec (con salt e iteration count)
            PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, iterationCount);

            // Inizializza il cipher per la decifratura con padding PKCS5Padding
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);

            ByteArrayInputStream bIn = new ByteArrayInputStream(encryptedBytes);
            CipherInputStream cIn = new CipherInputStream(bIn, cipher);
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            Streams.pipeAll(cIn, bOut);
            cIn.close();

            return new String(bOut.toByteArray(), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            throw new Exception("Errore nella decodifica Base64 o nei parametri di decifratura.", e);
        } catch (InvalidCipherTextIOException e) {
            throw new Exception("Errore nella decifratura: testo cifrato modificato o parametri errati.", e);
        }
    }

}





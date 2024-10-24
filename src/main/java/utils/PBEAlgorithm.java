package utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

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
            System.out.println("input    : " + Hex.toHexString(inpu));

        }

        public String Encrypt(String input, String passwd, String algorithm) throws Exception {
            PBEKeySpec pbeKeySpec = new PBEKeySpec(passwd.toCharArray());
            //choose the algorithm for creating the key
            SecretKeyFactory keyFact = SecretKeyFactory.getInstance(algorithm);
            //generate a secretKey using the passwd
            pbeKey = keyFact.generateSecret(pbeKeySpec);
            //create an object that contain aleatory parameter like salt and IC
            pbeParamSpec = new PBEParameterSpec(salt, iterationCount);
            //choose the algotithm for the cipher
            cipher = Cipher.getInstance(algorithm);
            //create the iv parameter for the Stream
            cipher.init(Cipher.ENCRYPT_MODE, pbeKey);
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            CipherOutputStream cOut = new CipherOutputStream(bOut, cipher);
            cOut.write(input.getBytes(StandardCharsets.UTF_8));
            cOut.close();
            return Hex.toHexString(bOut.toByteArray());
        }

    public String Decrypt(String input, String passwd, String algorithm) throws Exception {
        PBEKeySpec pbeKeySpec = new PBEKeySpec(passwd.toCharArray());
        //choose the algorithm for creating the key
        SecretKeyFactory keyFact = SecretKeyFactory.getInstance(algorithm);
        //generate a secretKey using the passwd
        pbeKey = keyFact.generateSecret(pbeKeySpec);
        //create an object that contain aleatory parameter like salt and IC
        pbeParamSpec = new PBEParameterSpec(salt, iterationCount);
        //choose the algotithm for the cipher
        cipher = Cipher.getInstance(algorithm);
        //create the iv parameter for the Stream
        AlgorithmParameters ivParams = cipher.getParameters();

        cipher.init(Cipher.DECRYPT_MODE, pbeKey, ivParams);
        // decrypt the cipher text
        byte[] ciphertextWithSalt = Base64.getDecoder().decode(input);

        ByteArrayInputStream bIn = new ByteArrayInputStream(ciphertextWithSalt);

        CipherInputStream cIn = new CipherInputStream(bIn, cipher);

        byte[] decrypted = Streams.readAll(cIn);

        return Hex.toHexString(decrypted);

    }
}





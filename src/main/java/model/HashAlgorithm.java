package model;

//Estendere la pratica precedente, per proteggere i file utilizzando un algoritmo
// di digest (Hash, MAC, HMac). La pratica consente di calcolare una sintesi del
// contenuto del file con diversi algoritmi, che possono essere scelti dall'utente
// tramite il parametro corrispondente. Al file di output viene aggiunta un'intestazione,
// in cui sono memorizzate le informazioni necessarie per poter riconoscere il file e verificarne l'integrità.
// In altre parole, l'applicazione applica una sorta di vaccino al file. Per evitare la contraffazione del riepilogo,
// viene utilizzato un valore segreto che deve essere fornito dall'utente; questo valore segreto viene utilizzato come
// SEGRETO CONDIVISO o come passwd per generare una chiave segreta, a seconda dell'algoritmo scelto.

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;
import utils.Header;
import utils.Options;
import org.apache.commons.io.output.CountingOutputStream;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

import static utils.JCAUtils.createDigestCalculator;

public class HashAlgorithm {

    private int iterationCount = 100;
    private byte[] salt = Hex.decode("0102030405060708");

    private long bytetoDelete;

    private int macLength = 64;

    SecretKey macKey = new SecretKeySpec(
            Hex.decode(
                    "2ccd85dfc8d18cb5d84fef4b19855469" +
                            "9fece6e8692c9147b0da983f5b7bd413"), "HmacSHA256");
    public String protectMessageHash(String input, String algorithm, String password) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
            byte[] macResult = this.hashBytes(input.getBytes(), algorithm, password);
            Header header = new Header(Options.OP_SIGNED, Options.OP_NONE_ALGORITHM, algorithm, macResult);
            ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
            CountingOutputStream outputStream = new CountingOutputStream(arrayOut);
            header.save(outputStream);
            bytetoDelete = outputStream.getByteCount();
            outputStream.write(input.getBytes());
            outputStream.close();
            System.out.println(outputStream.getByteCount());
            return Base64.getEncoder().encodeToString(arrayOut.toByteArray());
            // Stampa il risultato del MAC in formato esadecimale
    }

    public String verifyHashMessage(String datas, String hashFunction, String password) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        try {
            byte[] data = Base64.getDecoder().decode(datas);
            if (data.length > bytetoDelete) {
                ByteArrayInputStream IStream = new ByteArrayInputStream(data);
                Header header = new Header();
                header.load(IStream);
                System.out.println(Base64.getEncoder().encodeToString(header.getData()));
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                outputStream.write(data, (int) bytetoDelete, (int) (data.length - bytetoDelete));
                outputStream.close();

                System.out.println("Dati scritti nel file senza gli ultimi 34 byte.");
                return outputStream.toString();
            } else {
                System.out.println("I dati sono meno di 30 byte, non è possibile rimuovere.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public byte[] hashBytes(byte[] fileBytes, String algorithm, String password) throws NoSuchAlgorithmException, IOException, NoSuchProviderException, InvalidKeySpecException, InvalidKeyException {
        if (Objects.equals(algorithm, "HmacMD5") || Objects.equals(algorithm, "HmacSHA1") || Objects.equals(algorithm, "HmacSHA256") || Objects.equals(algorithm, "HmacSHA384") || Objects.equals(algorithm, "HmacSHA512")) {
            Security.addProvider(new BouncyCastleProvider());
            Mac mac = Mac.getInstance(algorithm, "BC");
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, macLength);
            SecretKey secretKey = skf.generateSecret(spec);
            mac.init(secretKey);
            mac.init(macKey);
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes)) {
                byte[] buffer = new byte[1024]; // Buffer per la lettura a blocchi
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    // Aggiorna il MAC con i dati letti
                    mac.update(buffer, 0, bytesRead);
                }
            }
            // Ottieni il MAC finale
            return mac.doFinal();
        }else{

            InputStream inputStream = new ByteArrayInputStream(fileBytes);
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.update(password.getBytes());
        // Creazione di un DigestInputStream per calcolare l'hash durante la lettura
        try (DigestInputStream digestInputStream = new DigestInputStream(inputStream, digest)) {
            byte[] buffer = new byte[4096];
            while (digestInputStream.read(buffer) != -1) {

            }
            return digest.digest();
        }
        }
    }
}


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

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.Security;
import java.util.Base64;
import java.util.Objects;

import static utils.JCAUtils.createDigestCalculator;

public class HashAlgorithm {

    private int iterationCount = 100;
    private byte[] salt = Hex.decode("0102030405060708");

    private int macLength = 64;

    SecretKey macKey = new SecretKeySpec(
            Hex.decode(
                    "2ccd85dfc8d18cb5d84fef4b19855469" +
                            "9fece6e8692c9147b0da983f5b7bd413"), "HmacSHA256");
    public byte[] protectMessageHash(String input, String algorithm, String password) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        if(Objects.equals(algorithm, "HmacMD5") || Objects.equals(algorithm, "HmacSHA1") || Objects.equals(algorithm, "HmacSHA256") || Objects.equals(algorithm, "HmacSHA384") || Objects.equals(algorithm, "HmacSHA512")) {
            Mac mac = Mac.getInstance(algorithm, "BC");
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(),salt,iterationCount,macLength);
            SecretKey secretKey = skf.generateSecret(spec);

            mac.init(secretKey);
            mac.init(macKey);

            mac.update(password.getBytes());

            return mac.doFinal();

        }else {
            InputStream inputStream = new ByteArrayInputStream(input.getBytes());

            // Creazione dell'istanza MessageDigest per SHA-256
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(password.getBytes());
            Header header = new Header(Options.OP_SIGNED, Options.OP_NONE_ALGORITHM, algorithm, digest.digest());
            // Creazione di un DigestInputStream per calcolare l'hash durante la lettura
            try ( DigestInputStream digestInputStream = new DigestInputStream(inputStream, digest)) {
                byte[] buffer = new byte[1024];
                while (digestInputStream.read(buffer) != -1) {

                }
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                outputStream.write(input.getBytes());
                header.save(outputStream);
                outputStream.close();
                return Base64.getEncoder().encode(outputStream.toByteArray());            }
        }
    }

    public String verifyHashMessage(byte[] data) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        try {
            // Creazione di un ByteArrayOutputStream per scrivere i dati

            // Scrittura di dati di esempio

            // Conversione a byte array per manipolazione
            // Verifica se ci sono almeno 30 byte da rimuovere
            if (data.length > 30) {
                // Creazione di un nuovo array senza gli ultimi 30 byte
                byte[] truncatedData = new byte[data.length - 30];
                System.arraycopy(data, 0, truncatedData, 0, data.length - 30);

                // Scrittura dei dati troncati in un altro OutputStream (ad esempio un file)
                OutputStream fileOutputStream = new FileOutputStream("output.dat");
                fileOutputStream.write(truncatedData);
                fileOutputStream.close();

                System.out.println("Dati scritti nel file senza gli ultimi 30 byte.");
            } else {
                System.out.println("I dati sono meno di 30 byte, non è possibile rimuovere.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.bouncycastle.util.encoders.Hex;

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


    public PBEAlgorithm(String passwd, String algorithm) throws Exception {
        PBEKeySpec  pbeKeySpec = new PBEKeySpec(passwd.toCharArray());
        SecretKeyFactory    keyFact = SecretKeyFactory.getInstance(algorithm);
        pbeKey = keyFact.generateSecret(pbeKeySpec);
        pbeParamSpec = new PBEParameterSpec(salt, iterationCount);
        cipher = Cipher.getInstance(algorithm);


    }

        public String Encrypt(String input) throws Exception{
        cipher.init(Cipher.ENCRYPT_MODE,pbeKey,pbeParamSpec);
        //System.out.println("input    : " + input);
        byte[] output = cipher.doFinal(Hex.encode(input.getBytes()));
        //System.out.println("encrypted: " + Hex.toHexString(output));
        return Hex.toHexString(output);
}


        //byte[] input = Hex.decode("a0a1a2a3a4a5a6a7a0a1a2a3a4a5a6a7"
         //       + "a0a1a2a3a4a5a6a7a0a1a2a3a4a5a6a7");

        public String Decrypt(String input) throws Exception{
        cipher.init(Cipher.DECRYPT_MODE,pbeKey,pbeParamSpec);
        return Hex.toHexString(cipher.doFinal(Hex.decodeStrict(input)));
        }
}


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class EncryptionManager {
    public SecretKey generateKey(char[] password, byte[] salt, int iterationCount, String algorithm) throws Exception {
        KeySpec pbeKeySpec = new PBEKeySpec(password);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
        return keyFactory.generateSecret(pbeKeySpec);
    }

    public byte[] encryptData(SecretKey secretKey, byte[] data, byte[] salt, int iterationCount, String algorithm) throws Exception {
        PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, iterationCount);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, pbeParamSpec);
        return cipher.doFinal(data);
    }

    public byte[] decryptData(SecretKey secretKey, byte[] encryptedData, byte[] salt, int iterationCount, String algorithm) throws Exception {
        PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, iterationCount);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, pbeParamSpec);
        return cipher.doFinal(encryptedData);
    }
}

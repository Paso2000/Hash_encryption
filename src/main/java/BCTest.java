import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class BCTest {
    public static void main(String[] args) {
        // Aggiungi il provider BouncyCastle alla JVM
        Security.addProvider(new BouncyCastleProvider());

        // Controlla se il provider è stato aggiunto
        if (Security.getProvider("BC") != null) {
            System.out.println("BouncyCastle è stato importato correttamente!");
        } else {
            System.out.println("Errore: BouncyCastle non è stato importato.");
        }
    }
}

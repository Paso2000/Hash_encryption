import controller.PBEController;
import model.HashAlgorithm;
import model.HashAlgorithmFile;
import model.PBEAlgorithm;
import model.PBEAlgorithmFile;
import view.PBEView;

public class MainTest {
    public static void main(String[] args) {
        PBEAlgorithm pbe = new PBEAlgorithm();
        PBEAlgorithmFile pbeFile = new PBEAlgorithmFile();
        HashAlgorithm hashAlgorithm = new HashAlgorithm();
        HashAlgorithmFile hashAlgorithmFile = new HashAlgorithmFile();
        PBEView view = new PBEView();
        PBEController controller = new PBEController(pbe, pbeFile, view,hashAlgorithm, hashAlgorithmFile);
    }
}
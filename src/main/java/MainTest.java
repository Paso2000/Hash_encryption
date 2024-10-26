import controller.PBEController;
import model.PBEAlgorithm;
import utils.PBEAlgorithmFile;
import view.PBEView;

public class MainTest {
    public static void main(String[] args) {
        PBEAlgorithm pbe = new PBEAlgorithm();
        PBEAlgorithmFile pbeFile = new PBEAlgorithmFile();
        PBEView view = new PBEView();
        PBEController controller = new PBEController(pbe, pbeFile, view);
    }
}
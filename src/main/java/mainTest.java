import controller.PBEController;
import model.PBEAlgorithm;
import view.PBEView;

public class mainTest {
    public static void main(String[] args) {
        PBEAlgorithm model = new PBEAlgorithm();
        PBEView view = new PBEView(); // Cambiato per la GUI
        PBEController controller = new PBEController(model, view);
    }
}
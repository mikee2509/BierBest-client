import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeController {
    private Stage root;

    public HomeController() {
    }

    public void setStage(Stage root) {
        this.root = root;
    }

    public void searchBeers(ActionEvent actionEvent) {
        //VBox vBox = (VBox) root.getScene().lookup("#search_results");

    }
}

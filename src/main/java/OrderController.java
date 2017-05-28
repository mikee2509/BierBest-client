import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OrderController {

    @FXML
    public Label messageSubject;
    @FXML
    public TextField quantity;


    private Stage stage;

    public OrderController() {
        Platform.runLater(() -> {
            quantity.requestFocus();
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void init(String beerName) {
        messageSubject.setText(beerName + " - price");
    }

    public void sendMessage(ActionEvent actionEvent) {
        //TODO
        stage.close();
    }

    public void discardMessage(ActionEvent actionEvent) {
        stage.close();
    }
}

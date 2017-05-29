import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    public TextField username;

    private Stage root;
    public static User currentUser;


    public LoginController() {
        currentUser = new User();
        Platform.runLater(() -> {
            username.requestFocus();
        });
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setStage(Stage root) {
        this.root = root;
    }

    public void browseBeers(ActionEvent actionEvent) throws Exception {
        currentUser.setUserName(username.getText());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scene_home.fxml"));
        Scene homeScene = new Scene(fxmlLoader.load());
        HomeController controller = fxmlLoader.getController();
        controller.setStage(root);
        root.setScene(homeScene);
        root.centerOnScreen();
        //controller.searchBeers(null);
    }
}

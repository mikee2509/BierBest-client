import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {
    private Stage root;

    public LoginController() {
    }

    public void setStage(Stage root){
        this.root = root;
    }

    public void browseBeers(ActionEvent actionEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scene_home.fxml"));
        Scene homeScene = new Scene(fxmlLoader.load());
        //homeScene.getStylesheets().add(getClass().getResource("style_home.css").toExternalForm());
        HomeController controller = fxmlLoader.getController();
        controller.setStage(root);
        root.setScene(homeScene);
        root.centerOnScreen();
        //controller.searchBeers(null);
    }
}

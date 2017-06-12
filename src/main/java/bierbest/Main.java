package bierbest;

import bierbest.scenes.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static final String SERVER_ADDRESS = "127.0.0.1";
    public static final int PORT = 4488;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scenes/scene_login.fxml"));
        Scene loginScene = new Scene(fxmlLoader.load());
        LoginController controller = fxmlLoader.getController();
        controller.setStage(primaryStage);
        primaryStage.setTitle("BierBest");
        primaryStage.setScene(loginScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}

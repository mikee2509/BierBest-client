package bierbest;

import bierbest.scenes.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javassist.bytecode.stackmap.TypeData;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger LOGGER = Logger.getLogger(TypeData.ClassName.class.getName());
    public static final String SERVER_ADDRESS = "127.0.0.1";
    public static final int PORT = 4488;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        loadLoginScene(primaryStage);
    }

    public static void loadLoginScene(Stage root) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("scenes/scene_login.fxml"));
            Scene loginScene = new Scene(fxmlLoader.load());
            LoginController controller = fxmlLoader.getController();
            controller.setStage(root);
            root.setTitle("BierBest");
            root.setScene(loginScene);
            root.setResizable(false);
            root.show();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error loading login scene");
        }
    }
}

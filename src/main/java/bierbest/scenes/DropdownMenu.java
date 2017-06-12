package bierbest.scenes;

import bierbest.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javassist.bytecode.stackmap.TypeData;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DropdownMenu {
    private static final Logger LOGGER = Logger.getLogger(TypeData.ClassName.class.getName());

    public static ContextMenu getInstance(Stage root){
        MenuItem orders = new MenuItem("   Orders   ");
        MenuItem logout = new MenuItem("   Log Out   ");
        orders.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HomeController.class.getResource("scene_cart.fxml"));
                Scene cartScene = new Scene(fxmlLoader.load());
                CartController controller = fxmlLoader.getController();
                controller.setRoot(root);
                controller.setPrevScene(root.getScene());
                root.setScene(cartScene);
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Error loading cart scene");
            }
        });
        logout.setOnAction(event -> {
            Main.loadLoginScene(root);
            root.centerOnScreen();
        });
        return new ContextMenu(orders, logout);
    }

    public static ContextMenu getCartInstance(Stage root) {
        MenuItem logout = new MenuItem("   Log Out   ");
        logout.setOnAction(event -> {
            Main.loadLoginScene(root);
            root.centerOnScreen();
        });
        return new ContextMenu(logout);
    }
}

package bierbest.scenes;

import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CartController {

    @FXML
    public Label username;
    @FXML
    public ImageView contextMenuArrow;

    private Stage root;
    private Scene prevScene;
    private ContextMenu contextMenu;

    public void setRoot(Stage root) {
        this.root = root;
    }

    public void setPrevScene(Scene prevScene) {
        this.prevScene = prevScene;
    }

    public void showContextMenu(MouseEvent mouseEvent) {
        if(contextMenu == null) {
            contextMenu = DropdownMenu.getCartInstance(root);
            username.setContextMenu(contextMenu);
        }
        contextMenu.show(contextMenuArrow, Side.BOTTOM, -100, 15);
    }

    public void goBack(MouseEvent mouseEvent) {
        root.setScene(prevScene);
    }
}

package bierbest.scenes;

import bierbest.api.ApiBeer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.LinkedHashMap;

public class DetailsController {
    @FXML
    public Label username;
    @FXML
    public ImageView contextMenuArrow;
    @FXML
    private ImageView beerImage;
    @FXML
    private Label beerName;
    @FXML
    private Label beerStyle;
    @FXML
    private Label beerAvailability;
    @FXML
    private Label beerAbv;
    @FXML
    private Label beerIbu;
    @FXML
    private Text beerDescription;

    private Stage root;
    private Scene homeScene;
    private ApiBeer apiBeer;
    private ContextMenu contextMenu;

    public void setStage(Stage root) {
        this.root = root;
    }

    public void setHomeScene(Scene homeScene) {
        this.homeScene = homeScene;
    }

    public void passBeerProperties(ApiBeer apiBeer, String name, String style, String abv, String description) {
        this.apiBeer = apiBeer;
        beerName.setText(name);
        beerStyle.setText(style);
        beerAbv.setText(abv);
        beerDescription.setText(description);
        username.setText(LoginController.currentUser.getUsername());

        double ibu = this.apiBeer.getIbu();
        if (ibu < 0) {
            beerIbu.setText("Unknown");
            beerIbu.setStyle("-fx-font-style: italic;");
        } else {
            beerIbu.setText(String.valueOf(ibu));
        }

        String desc = this.apiBeer.getDescription();
        if (desc != null) {
            beerDescription.setText(desc);
        } else {
            beerDescription.setText("No description provided");
            beerDescription.setStyle("-fx-font-style: italic;");
        }

        LinkedHashMap<String, String> icons = this.apiBeer.getLabels();
        String imgurl = icons == null ? null : icons.get("large");
        beerImage.setImage(new Image(imgurl == null ? "bierbest/images/placeholder_icon_large.png" : imgurl));

        LinkedHashMap<String, String> availability = this.apiBeer.getAvailable();
        String avail = availability == null ? null : availability.get("description");
        if (avail != null) {
            beerAvailability.setText(avail);
        } else {
            beerAvailability.setText("Unknown availability");
            beerAvailability.setStyle("-fx-font-style: italic;");
        }

    }

    public void goBackToResults(MouseEvent mouseEvent) {
        root.setScene(homeScene);
    }


    public void askForPrice(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(root.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scene_order.fxml"));
        stage.setScene(new Scene(fxmlLoader.load()));
        OrderController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.init(beerName.getText(), apiBeer.getId());
        stage.setTitle("Ask for price");
        stage.show();
    }

    public void showContextMenu(MouseEvent mouseEvent) {
        if(contextMenu == null) {
            contextMenu = DropdownMenu.getInstance(root);
            username.setContextMenu(contextMenu);
        }
        contextMenu.show(contextMenuArrow, Side.BOTTOM, -100, 15);
    }
}

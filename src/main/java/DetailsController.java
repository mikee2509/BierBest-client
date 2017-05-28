import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.LinkedHashMap;

public class DetailsController {
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
    private Scene prevScene;
    private BeerInfo beerInfo;

    public void setStage(Stage root) {
        this.root = root;
    }

    public void setPrevScene(Scene prevScene) {
        this.prevScene = prevScene;
    }

    public void passBeerProperties(BeerInfo beer, String name, String style, String abv, String description) {
        beerInfo = beer;
        beerName.setText(name);
        beerStyle.setText(style);
        beerAbv.setText(abv);
        beerDescription.setText(description);

        int ibu = beerInfo.getIbu();
        if(ibu < 0) {
            beerIbu.setText("Unknown");
            beerIbu.setStyle("-fx-font-style: italic;");
        } else {
            beerIbu.setText(String.valueOf(ibu));
        }

        String desc = beerInfo.getDescription();
        if(desc != null) {
            beerDescription.setText(desc);
        } else {
            beerDescription.setText("No description provided");
            beerDescription.setStyle("-fx-font-style: italic;");
        }

        LinkedHashMap<String, String> icons = beerInfo.getLabels();
        String imgurl = icons == null ? null : icons.get("large");
        beerImage.setImage(new Image(imgurl == null ? "img/placeholder_icon_large.png" : imgurl));

        LinkedHashMap<String, String> availability = beerInfo.getAvailable();
        String avail = availability == null ? null : availability.get("description");
        if(avail != null) {
            beerAvailability.setText(avail);
        } else {
            beerAvailability.setText("Unknown availability");
            beerAvailability.setStyle("-fx-font-style: italic;");
        }

    }

    public void goBackToResults(MouseEvent mouseEvent) {
        root.setScene(prevScene);
    }


}

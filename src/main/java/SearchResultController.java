import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SearchResultController {
    @FXML
    private Label beerName;
    @FXML
    private Label beerStyle;
    @FXML
    private Label beerAbv;
    @FXML
    private Label beerDescription;
    @FXML
    private ImageView beerIcon;

    public void setBeerNameText(String text) {
        beerName.setText(text);
    }

    public void setBeerStyleText(String style) {
        if(style != null) {
            beerStyle.setText(style);
        } else {
            beerStyle.setText("No style provided");
            beerStyle.setStyle("-fx-font-style: italic;");
        }
    }

    public void setBeerAbvText(double abv) {
        if(abv < 0) {
            beerAbv.setText("Unknown");
            beerAbv.setStyle("-fx-font-style: italic;");
        } else {
            beerAbv.setText(String.valueOf(abv));
        }
    }

    public void setBeerDescriptionText(String desc) {
        if(desc != null) {
            desc = desc.replaceAll("\\r\\n|\\r|\\n", " ");
            beerDescription.setText(desc);
        } else {
            beerDescription.setText("No description provided");
            beerDescription.setStyle("-fx-font-style: italic;");
        }
    }

    public void setBeerIconImage(Image icon) {
        beerIcon.setImage(icon);
    }
}

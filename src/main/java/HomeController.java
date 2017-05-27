import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class HomeController {
    private Stage root;

    @FXML
    private VBox searchResults;

    public HomeController() {
    }

    public void setStage(Stage root) {
        this.root = root;
    }

    public void searchBeers(ActionEvent actionEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fragment_search_result.fxml"));
        AnchorPane singleResult = (AnchorPane) fxmlLoader.load();
        ImageView beerIcon = (ImageView) singleResult.lookup("#beerIcon");
        beerIcon.setImage(new Image("img/testimg.png"));
        searchResults.getChildren().add(singleResult);
        Region margin = new Region();
        margin.setMinHeight(10);
        searchResults.getChildren().add(margin);


    }
}

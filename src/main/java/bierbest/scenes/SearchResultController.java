package bierbest.scenes;

import bierbest.api.ApiBeer;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.LinkedHashMap;

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

    private Stage root;
    private ApiBeer apiBeer;
    private HomeController rootController;
    private Pane detailsPane;

    public void setStage(Stage root) {
        this.root = root;
    }

    public void setRootController(HomeController rootController) {
        this.rootController = rootController;
    }

    public void init(ApiBeer apiBeer) {
        this.apiBeer = apiBeer;
        LinkedHashMap<String, String> icons = this.apiBeer.getLabels();
        String imgurl = icons == null ? null : icons.get("icon");
        beerIcon.setImage(new Image(imgurl == null ? "bierbest/images/placeholder_icon.png" : imgurl));

        beerName.setText(this.apiBeer.getName());

        LinkedHashMap<String, Object> style = this.apiBeer.getStyle();
        String styleName = style == null ? null : (String) style.get("name");
        if (styleName != null) {
            beerStyle.setText(styleName);
        } else {
            beerStyle.setText("No style provided");
            beerStyle.setStyle("-fx-font-style: italic;");
        }

        double abv = this.apiBeer.getAbv();
        if (abv < 0) {
            beerAbv.setText("Unknown");
            beerAbv.setStyle("-fx-font-style: italic;");
        } else {
            beerAbv.setText(String.valueOf(abv) + "%");
        }

        String desc = this.apiBeer.getDescription();
        if (desc != null) {
            desc = desc.replaceAll("\\r\\n|\\r|\\n", " ");
            beerDescription.setText(desc);
        } else {
            beerDescription.setText("No description provided");
            beerDescription.setStyle("-fx-font-style: italic;");
        }
    }

    public void viewDetails(MouseEvent mouseEvent) throws Exception {
        if (rootController.getOperationInProgress().compareAndSet(false, true)) {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scene_details.fxml"));
                    Pane pane = fxmlLoader.load();
                    DetailsController controller = fxmlLoader.getController();
                    controller.setStage(root);
                    controller.setPrevScene(root.getScene());
                    controller.passBeerProperties(apiBeer, beerName.getText(), beerStyle.getText(), beerAbv.getText(), beerDescription.getText());
                    detailsPane = pane;
                    return null;
                }
            };

            task.setOnSucceeded(event -> {
                root.setScene(new Scene(detailsPane));
                rootController.getHomeBorderPane().setBottom(null);
                rootController.getOperationInProgress().set(false);
            });

            ProgressBar progressBar = new ProgressBar(-1.0);
            progressBar.prefWidthProperty().bind(rootController.getHomeBorderPane().widthProperty());
            rootController.getHomeBorderPane().setBottom(progressBar);

            Thread thread = new Thread(task);
            thread.start();
        }
    }
}

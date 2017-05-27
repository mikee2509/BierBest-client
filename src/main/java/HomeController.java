import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;


public class HomeController {
    private Stage root;

    @FXML
    private VBox searchResults;

    private APISearchResult apiResult;

    public ArrayList<AnchorPane> resultPanes;

    public HomeController() {
    }

    public void setStage(Stage root) {
        this.root = root;
    }

    public void searchBeers(ActionEvent actionEvent) throws Exception {
        resultPanes = new ArrayList<>();

        Task<Void> task = new Task<Void>() {
            @Override protected Void call() throws Exception {
                ObjectMapper mapper = new ObjectMapper();
                String uri = "C:\\Users\\Michał\\IdeaProjects\\BierBest-client\\src\\main\\resources\\query_zywiec.txt";
                apiResult = mapper.readValue(new File(uri), APISearchResult.class);
                System.out.println(apiResult);

                try {
                    for(BeerInfo beer : apiResult.getData()){
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fragment_search_result.fxml"));
                        AnchorPane pane = (AnchorPane) fxmlLoader.load();
                        SearchResultController controller = fxmlLoader.getController();

                        LinkedHashMap<String, String> icons = beer.getLabels();
                        String imgurl = icons == null ? null : icons.get("icon");
                        controller.setBeerIconImage(new Image(imgurl == null ? "img/placeholder_icon.png" : imgurl));
                        controller.setBeerNameText(beer.getName());
                        controller.setBeerStyleText((String) beer.getStyle().get("name"));
                        controller.setBeerAbvText(beer.getAbv());
                        controller.setBeerDescriptionText(beer.getDescription());

                        resultPanes.add(pane);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            for (AnchorPane pane: resultPanes) {
                searchResults.getChildren().add(pane);
                Region margin = new Region();
                margin.setMinHeight(10);
                searchResults.getChildren().add(margin);
            }
        });

        System.out.println(resultPanes);
        Thread thread = new Thread(task);
        thread.start();
    }
}

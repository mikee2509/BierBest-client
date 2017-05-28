import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class HomeController {
    private Stage root;

    @FXML
    private VBox searchResults;
    @FXML
    private TextField searchField;
    @FXML
    private BorderPane rootBorderPane;

    private APISearchResult apiResult;
    public ArrayList<AnchorPane> resultPanes;

    public HomeController() {
    }

    public void setStage(Stage root) {
        this.root = root;
    }

    public void searchBeers(ActionEvent actionEvent) throws Exception {
        resultPanes = new ArrayList<>();
        searchResults.getChildren().clear();

        Task<Void> task = new Task<Void>() {
            @Override protected Void call() throws Exception {
                String query = searchField.getText();
                ObjectMapper mapper = new ObjectMapper();
                apiResult = mapper.readValue(getHTML("http://api.brewerydb.com/v2/" +
                        "search?type=beer&key=a19fcef43297aa840f8c63a0e1fb1023&q=" + URLEncoder.encode(query, "UTF-8")),
                        APISearchResult.class);

//                String uri = "C:\\Users\\Michał\\IdeaProjects\\BierBest-client\\src\\main\\resources\\query_ale.txt";
//                apiResult = mapper.readValue(new File(uri), APISearchResult.class);

                System.out.println(apiResult);

                try {
                    int numOfResults = apiResult.getData().size();
                    int current = 1;
                    for(BeerInfo beer : apiResult.getData()){
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fragment_search_result.fxml"));
                        AnchorPane pane = fxmlLoader.load();
                        SearchResultController controller = fxmlLoader.getController();

                        LinkedHashMap<String, String> icons = beer.getLabels();
                        String imgurl = icons == null ? null : icons.get("icon");
                        controller.setBeerIconImage(new Image(imgurl == null ? "img/placeholder_icon.png" : imgurl));
                        controller.setBeerNameText(beer.getName());
                        LinkedHashMap<String, Object> style = beer.getStyle();
                        String styleName = style == null ? null : (String) style.get("name");
                        controller.setBeerStyleText(styleName);
                        controller.setBeerAbvText(beer.getAbv());
                        controller.setBeerDescriptionText(beer.getDescription());

                        resultPanes.add(pane);
                        updateProgress(current, numOfResults);
                        ++current;
                    }
                } catch (Exception e) {
                    System.out.println("Exception when processing search results: " + e);
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
            rootBorderPane.setBottom(null);
        });

        ProgressBar progressBar = new ProgressBar(-1.0);
        progressBar.prefWidthProperty().bind(rootBorderPane.widthProperty());
        progressBar.progressProperty().bind(task.progressProperty());
        rootBorderPane.setBottom(progressBar);

        Thread thread = new Thread(task);
        thread.start();
    }

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}

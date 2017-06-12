package bierbest.scenes;

import bierbest.api.ApiBeer;
import bierbest.api.ApiSearchResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javassist.bytecode.stackmap.TypeData;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HomeController {
    private static final Logger LOGGER = Logger.getLogger(TypeData.ClassName.class.getName());

    @FXML
    public Label username;
    @FXML
    public ImageView contextMenuArrow;
    @FXML
    private VBox searchResults;
    @FXML
    private TextField searchField;
    @FXML
    private BorderPane homeBorderPane;

    private Stage root;
    private ApiSearchResult apiResult;
    private ContextMenu contextMenu;
    private LinkedList<AnchorPane> resultsPanes;
    private LinkedList<SearchResultController> resultsControllers;
    private AtomicBoolean operationInProgress;

    public HomeController() {
        operationInProgress = new AtomicBoolean(false);
    }

    public void initialize() {
        searchField.requestFocus();
        username.setText(LoginController.getCurrentUser().getUsername());
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

    public void setStage(Stage root) {
        this.root = root;
    }

    public AtomicBoolean getOperationInProgress() {
        return operationInProgress;
    }

    public BorderPane getHomeBorderPane() {
        return homeBorderPane;
    }

    public void searchBeers(ActionEvent actionEvent) {
        if (operationInProgress.compareAndSet(false, true)) {
            final String query = searchField.getText();
            if (StringUtils.isBlank(query)) {

                operationInProgress.set(false);
                return;
            }
            resultsPanes = new LinkedList<>();
            resultsControllers = new LinkedList<>();
            searchResults.getChildren().clear();


            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    String q = query;
                    q = StringUtils.stripAccents(q);
                    ObjectMapper mapper = new ObjectMapper();
                    apiResult = mapper.readValue(getHTML("http://api.brewerydb.com/v2/" +
                                    "search?type=beer&key=a19fcef43297aa840f8c63a0e1fb1023&q=" + URLEncoder.encode(q, "UTF-8")),
                            ApiSearchResult.class);

                    if (apiResult.getTotalResults() == 0) {
                        return false;
                    }

                    try {
                        int numOfResults = apiResult.getData().size();
                        int current = 1;
                        for (ApiBeer beer : apiResult.getData()) {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fragment_search_result.fxml"));
                            AnchorPane pane = fxmlLoader.load();
                            SearchResultController controller = fxmlLoader.getController();
                            controller.setStage(root);
                            controller.init(beer);
                            resultsPanes.add(pane);
                            resultsControllers.add(controller);
                            updateProgress(current, numOfResults);
                            ++current;
                        }
                    } catch (Exception e) {
                        LOGGER.log(Level.WARNING, "Exception while processing search results");
                        e.printStackTrace();
                    }
                    return true;
                }
            };

            task.setOnSucceeded(event -> {
                if (task.getValue()) {
                    for (AnchorPane pane : resultsPanes) {
                        searchResults.getChildren().add(pane);
                        Region margin = new Region();
                        margin.setMinHeight(10);
                        searchResults.getChildren().add(margin);
                    }
                    for (SearchResultController controller : resultsControllers) {
                        controller.setRootController(this);
                    }
                } else {
                    Label label = new Label("No results found for \"" + query + "\"");
                    label.setStyle("-fx-font-style: italic; -fx-font-size: 20; " +
                            "-fx-font-family: \"Source Sans Pro\"; -fx-text-fill: white;");
                    label.setPadding(new Insets(15, 15, 15, 15));
                    searchResults.getChildren().add(label);
                }
                homeBorderPane.setBottom(null);
                operationInProgress.set(false);
            });

            ProgressBar progressBar = new ProgressBar(-1.0);
            progressBar.prefWidthProperty().bind(homeBorderPane.widthProperty());
            progressBar.progressProperty().bind(task.progressProperty());
            homeBorderPane.setBottom(progressBar);

            Thread thread = new Thread(task);
            thread.start();
        }
    }

    public void showContextMenu(MouseEvent mouseEvent) {
        if(contextMenu == null) {
            contextMenu = DropdownMenu.getInstance(root);
            username.setContextMenu(contextMenu);
        }
        contextMenu.show(contextMenuArrow, Side.BOTTOM, -100, 15);
    }
}

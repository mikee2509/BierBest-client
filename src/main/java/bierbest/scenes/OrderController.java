package bierbest.scenes;

import bierbest.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OrderController {

    @FXML
    public Label messageSubject;
    @FXML
    public TextField quantity;
    @FXML
    public TextArea message;
    @FXML
    public BorderPane orderPane;

    private String beerName;
    private String beerId;
    private Stage stage;

    public OrderController() {
        Platform.runLater(() -> {
            quantity.requestFocus();
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void init(String beerName, String beerId) {
        this.beerName = beerName;
        this.beerId = beerId;
        messageSubject.setText(beerName + " - price");
    }

    public void sendMessage(ActionEvent actionEvent) {
        orderPane.setBottom(null);
        Order order = new Order();
        String quant = quantity.getText();
        if (StringUtils.isNumeric(quant)) {
            order.setQuantity(Integer.parseInt(quant));
        } else {
            Label label = new Label("Quantity must be a number");
            label.setStyle("-fx-font-style: italic; -fx-font-size: 20; " +
                    "-fx-font-family: \"Source Sans Pro\"; -fx-text-fill: white;");
            label.setPadding(new Insets(5, 15, 5, 25));
            VBox vBox = new VBox(label);
            vBox.setStyle("-fx-background-color: red");
            orderPane.setBottom(vBox);
            return;
        }
        order.setBeerId(beerId);
        order.setBeerName(beerName);
        order.setMessage(message.getText());

        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(order);
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("orders.txt", true)));
            out.print(json);
            out.close();
        } catch (IOException e) {
            System.out.println("Error saving order: " + e);
        }

        stage.close();
    }

    public void discardMessage(ActionEvent actionEvent) {
        stage.close();
    }
}

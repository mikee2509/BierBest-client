package bierbest.scenes;

import bierbest.Main;
import bierbest.api.ApiBeer;
import bierbest.model.BeerInfo;
import bierbest.model.OrderModel;
import bierbest.model.Request;
import bierbest.model.ServerConnection;
import bierbest.model.payloads.MessageAction;
import bierbest.model.payloads.OrderData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Date;
import java.util.prefs.Preferences;

public class OrderController {

    @FXML
    public Label messageSubject;
    @FXML
    public TextField quantity;
    @FXML
    public TextArea message;
    @FXML
    public BorderPane orderPane;
    @FXML
    public VBox errorVbox;

    private String beerName;
    private String imageUrl;
    private Stage stage;

    public OrderController() {
    }

    public void initialize() {
        quantity.requestFocus();
        quantity.textProperty().addListener((ov, oldValue, newValue) -> {
            errorVbox.setVisible(false);
            if (quantity.getText().length() > 5) {
                String s = quantity.getText().substring(0, 5);
                quantity.setText(s);
            }
            if (!newValue.matches("\\d*")) {
                quantity.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void init(ApiBeer apiBeer) {
        this.beerName = apiBeer.getName();
        if(apiBeer.getLabels() != null) {
            this.imageUrl = apiBeer.getLabels().get("icon");
        }
        messageSubject.setText(beerName + " - price");
    }

    public void sendMessage(ActionEvent actionEvent) {
        OrderModel order = new OrderModel();
        String quant = quantity.getText();
        if (!quant.isEmpty()) {
            order.setQuantity(Integer.parseInt(quant));
        } else {
            errorVbox.setVisible(true);
            quantity.requestFocus();
            return;
        }

        BeerInfo beerInfo = new BeerInfo();
        beerInfo.setName(beerName);
        beerInfo.setURL(message.getText());
        beerInfo.setImgURL(imageUrl);
        order.setBeerInfo(beerInfo);
        order.setStatusClientSide("new");
        order.setDate(new Date());
        order.setClient(LoginController.getCurrentUser());
        Preferences preferences = Preferences.userRoot().node(LoginController.class.getName());
        Request request = new Request(preferences.get(LoginController.USERNAME_ID, ""),
                preferences.get(LoginController.PASSWORD_ID, ""), MessageAction.ADD_ORDER, new OrderData(order));

        ServerConnection connection = new ServerConnection(Main.SERVER_ADDRESS, Main.PORT);
        connection.addSingleRequest(request);
        Thread thread = new Thread(connection);
        thread.start();
        stage.close();
    }

    public void discardMessage(ActionEvent actionEvent) {
        stage.close();
    }
}

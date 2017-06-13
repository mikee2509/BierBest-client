package bierbest.scenes;

import bierbest.Main;
import bierbest.model.*;
import bierbest.model.payloads.MessageAction;
import bierbest.model.payloads.OrderData;
import bierbest.model.payloads.Orders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javassist.bytecode.stackmap.TypeData;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class CartController {
    private static final Logger LOGGER = Logger.getLogger(TypeData.ClassName.class.getName());

    @FXML
    public Label username;
    @FXML
    public ImageView contextMenuArrow;
    @FXML
    public TableColumn beerColumn;
    @FXML
    public TableColumn quantityColumn;
    @FXML
    public TableColumn priceColumn;
    @FXML
    public TableColumn statusColumn;
    @FXML
    public TableColumn decisionColumn;
    @FXML
    public TableView<CartTableModel> ordersTable;

    private Stage root;
    private Scene prevScene;
    private ContextMenu contextMenu;
    private Preferences preferences;
    private Orders clientOrders;
    private ObservableList<CartTableModel> ordersData;

    public void setRoot(Stage root) {
        this.root = root;
    }

    public void setPrevScene(Scene prevScene) {
        this.prevScene = prevScene;
    }

    public void initialize() {
        beerColumn.setCellValueFactory(new PropertyValueFactory<CartTableModel, String>("beerName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<CartTableModel, Integer>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<CartTableModel, String>("priceOffered"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<CartTableModel, String>("orderStatus"));
        decisionColumn.setCellValueFactory(new PropertyValueFactory<CartController, String>("decision"));
        preferences = Preferences.userRoot().node(LoginController.class.getName());
        loadOrders();
    }

    private void loadOrders() {
        Request request = new Request(preferences.get(LoginController.USERNAME_ID, ""),
                preferences.get(LoginController.PASSWORD_ID, ""), MessageAction.GET_CLIENT_ORDERS, null);
        ServerConnection connection = new ServerConnection(Main.SERVER_ADDRESS, Main.PORT);
        connection.addSingleRequest(request);

        connection.setOnSucceeded(event -> {
            ArrayList<Response> responses = connection.getValue();
            if (responses.get(0).getResponseCode() == Response.SUCCESS) {
                clientOrders = (Orders) responses.get(0).getPayload();
                ordersData = FXCollections.observableArrayList();
                for (OrderModel o : clientOrders.getOrders()) {
                    ordersData.add(new CartTableModel(o.getBeerInfo().getName(),
                            o.getQuantity() == null ? null : o.getQuantity().toString(),
                            o.getBeerInfo().getPriceString(), o.getStatusShopSide(), o.getStatusClientSide()));
                }
                ordersTable.setItems(ordersData);
                LOGGER.log(Level.INFO, "Successfully retrieved client orders from server");
            } else if (responses.get(0).getResponseCode() == Response.DENIED) {
                LOGGER.log(Level.WARNING, "Access to the server denied");
            }
        });

        Thread thread = new Thread(connection);
        thread.start();
    }

    public void showContextMenu(MouseEvent mouseEvent) {
        if (contextMenu == null) {
            contextMenu = DropdownMenu.getCartInstance(root);
            username.setContextMenu(contextMenu);
        }
        contextMenu.show(contextMenuArrow, Side.BOTTOM, -100, 15);
    }

    public void goBack(MouseEvent mouseEvent) {
        root.setScene(prevScene);
    }

    public void acceptPrice(ActionEvent actionEvent) {
        updateClientStatus("accept");
    }

    public void refresh(ActionEvent actionEvent) {
        loadOrders();
    }

    public void rejectPrice(ActionEvent actionEvent) {
        updateClientStatus("reject");
    }

    private void updateClientStatus(String status) {
        if (ordersTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String shopStatus = ordersTable.getSelectionModel().getSelectedItem().getOrderStatus();
        if (shopStatus.equals("waiting for offer") || shopStatus.equals("sent") || shopStatus.equals("rejected")) {
            return;
        }
        int orderIndex = ordersTable.getSelectionModel().getSelectedIndex();
        OrderModel orderModel = clientOrders.getOrders().get(orderIndex);
        orderModel.setStatusClientSide(status);
        Request request = new Request(preferences.get(LoginController.USERNAME_ID, ""),
                preferences.get(LoginController.PASSWORD_ID, ""), MessageAction.UPDATE_ORDER_STATUS, new OrderData(orderModel));
        ServerConnection connection = new ServerConnection(Main.SERVER_ADDRESS, Main.PORT);
        connection.addSingleRequest(request);
        connection.setOnSucceeded(event -> {
            loadOrders();
        });

        Thread thread = new Thread(connection);
        thread.start();
    }
}

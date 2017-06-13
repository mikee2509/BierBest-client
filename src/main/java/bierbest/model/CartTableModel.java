package bierbest.model;

import javafx.beans.property.SimpleStringProperty;

public class CartTableModel {
    private final SimpleStringProperty beerName;
    private final SimpleStringProperty quantity;
    private final SimpleStringProperty priceOffered;
    private final SimpleStringProperty orderStatus;
    private final SimpleStringProperty decision;

    public CartTableModel(String beerName, String quantity, String priceOffered, String orderStatus, String decision) {
        this.beerName = new SimpleStringProperty(beerName);
        this.quantity = new SimpleStringProperty(quantity == null ? "unknown" : quantity);
        String price;
        if (priceOffered == null) {
            price = "";
        } else if (priceOffered.equals("0.00")) {
            price = "";
        } else {
            price = priceOffered;
        }
        this.priceOffered = new SimpleStringProperty(price);
        String status;
        if (orderStatus == null) {
            status = "unknown";
        } else if (orderStatus.isEmpty()) {
            status = "waiting for offer";
        } else {
            status = orderStatus;
        }
        this.orderStatus = new SimpleStringProperty(status);
        String dec;
        if (decision == null) {
            dec = "";
        } else if (decision.equals("new")) {
            dec = "";
        } else {
            dec = decision;
        }
        this.decision = new SimpleStringProperty(dec);
    }

    public String getBeerName() {
        return beerName.get();
    }

    public SimpleStringProperty beerNameProperty() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName.set(beerName);
    }

    public String getQuantity() {
        return quantity.get();
    }

    public SimpleStringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public String getPriceOffered() {
        return priceOffered.get();
    }

    public SimpleStringProperty priceOfferedProperty() {
        return priceOffered;
    }

    public void setPriceOffered(String priceOffered) {
        this.priceOffered.set(priceOffered);
    }

    public String getOrderStatus() {
        return orderStatus.get();
    }

    public SimpleStringProperty orderStatusProperty() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus.set(orderStatus);
    }

    public String getDecision() {
        return decision.get();
    }

    public SimpleStringProperty decisionProperty() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision.set(decision);
    }
}

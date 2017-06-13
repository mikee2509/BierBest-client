package bierbest.model.payloads;

import bierbest.model.OrderModel;

import java.io.Serializable;
import java.util.List;

public class Orders extends Payload implements Serializable {
    private static final long serialVersionUID = 106L;
    public List<OrderModel> orders;

    public Orders(List<OrderModel> orders) {
        this.orders = orders;
    }

    public List<OrderModel> getOrders() {
        return orders;
    }
}

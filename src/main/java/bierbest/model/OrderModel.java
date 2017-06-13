package bierbest.model;

import java.io.Serializable;
import java.util.Date;

public class OrderModel implements Serializable {
    private static final long serialVersionUID = 107L;

    protected Integer id;
    protected ClientModel client;
    protected Date date;
    protected String statusClientSide;
    protected String statusShopSide;
    protected BeerInfo beerInfo;
    protected Integer quantity;

    @Override
    public String toString() {
        return "OrderModel{" +
                "id=" + id +
                ", client=" + client +
                ", date=" + date +
                ", statusClientSide='" + statusClientSide + '\'' +
                ", statusShopSide='" + statusShopSide + '\'' +
                ", beerInfo=" + beerInfo +
                ", quantity=" + quantity +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isIdNull() {
        return id == null;
    }

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatusClientSide() {
        return statusClientSide;
    }

    public void setStatusClientSide(String statusClientSide) {
        this.statusClientSide = statusClientSide;
    }

    public String getStatusShopSide() {
        return statusShopSide;
    }

    public void setStatusShopSide(String statusShopSide) {
        this.statusShopSide = statusShopSide;
    }

    public BeerInfo getBeerInfo() {
        return beerInfo;
    }

    public void setBeerInfo(BeerInfo beerInfo) {
        this.beerInfo = beerInfo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}


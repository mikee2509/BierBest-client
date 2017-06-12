package bierbest.model;

import java.io.Serializable;

public class BeerInfo implements Serializable {
    private static final long serialVersionUID = 104L;

    protected String name;
    protected String priceString;
    protected String URL;
    protected String imgURL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriceString() {
        return priceString;
    }

    public void setPriceString(String priceString) {
        if (!priceString.matches("^\\d{1,}[,|.]\\d\\d?$")) {
            throw new RuntimeException("invalid price format");
        }
        priceString = priceString.replace(',', '.');
        this.priceString = priceString;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}


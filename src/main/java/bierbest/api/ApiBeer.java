package bierbest.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedHashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiBeer {
    private String id;
    private String name;
    private String description;
    private double abv = -1;
    private double ibu = -1.0;
    LinkedHashMap<String, String> labels;
    LinkedHashMap<String, String> available;
    LinkedHashMap<String, Object> style;

    @Override
    public String toString() {
        return "\nApiBeer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", abv=" + abv +
                ", ibu=" + ibu +
                ", labels=" + labels +
                ", available=" + available +
                ", style=" + style +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAbv() {
        return abv;
    }

    public void setAbv(double abv) {
        this.abv = abv;
    }

    public double getIbu() {
        return ibu;
    }

    public void setIbu(double ibu) {
        this.ibu = ibu;
    }

    public LinkedHashMap<String, String> getLabels() {
        return labels;
    }

    public void setLabels(LinkedHashMap<String, String> labels) {
        this.labels = labels;
    }

    public LinkedHashMap<String, String> getAvailable() {
        return available;
    }

    public void setAvailable(LinkedHashMap<String, String> available) {
        this.available = available;
    }

    public LinkedHashMap<String, Object> getStyle() {
        return style;
    }

    public void setStyle(LinkedHashMap<String, Object> style) {
        this.style = style;
    }
}

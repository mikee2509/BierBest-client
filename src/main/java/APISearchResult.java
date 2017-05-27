import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APISearchResult {
    private int currentPage;
    private int numberOfPages;
    private int totalResults;
    private List<BeerInfo> data;

    @Override
    public String toString() {
        return "\nAPISearchResult{" +
                "currentPage=" + currentPage +
                ", numberOfPages=" + numberOfPages +
                ", totalResults=" + totalResults +
                ", data=" + data +
                '}';
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<BeerInfo> getData() {
        return data;
    }

    public void setData(List<BeerInfo> data) {
        this.data = data;
    }
}

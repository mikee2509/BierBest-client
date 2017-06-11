package bierbest.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiSearchResult {
    private int currentPage;
    private int numberOfPages;
    private int totalResults;
    private List<ApiBeer> data;

    @Override
    public String toString() {
        return "\nApiSearchResult{" +
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

    public List<ApiBeer> getData() {
        return data;
    }

    public void setData(List<ApiBeer> data) {
        this.data = data;
    }
}

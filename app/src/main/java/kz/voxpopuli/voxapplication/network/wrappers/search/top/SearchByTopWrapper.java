package kz.voxpopuli.voxapplication.network.wrappers.search.top;

/**
 * Created by user on 16.04.15.
 */
import com.google.gson.annotations.Expose;

public class SearchByTopWrapper {

    @Expose
    private SearchResponse searchResponse;

    /**
     *
     * @return
     * The searchResponse
     */
    public SearchResponse getSearchResponse() {
        return searchResponse;
    }

    /**
     *
     * @param searchResponse
     * The searchResponse
     */
    public void setSearchResponse(SearchResponse searchResponse) {
        this.searchResponse = searchResponse;
    }

    public SearchByTopWrapper withSearchResponse(SearchResponse searchResponse) {
        this.searchResponse = searchResponse;
        return this;
    }

}

package kz.voxpopuli.voxapplication.network.wrappers.search.string;

/**
 * Created by user on 15.04.15.
 */
import com.google.gson.annotations.Expose;

public class SearchByStringWrapper {

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

    public SearchByStringWrapper withSearchResponse(SearchResponse searchResponse) {
        this.searchResponse = searchResponse;
        return this;
    }

}

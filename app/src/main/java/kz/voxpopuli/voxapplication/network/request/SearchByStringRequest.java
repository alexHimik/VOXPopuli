package kz.voxpopuli.voxapplication.network.request;

import com.android.volley.Response;

import java.util.Map;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.search.string.SearchByStringWrapper;

/**
 * Created by user on 15.04.15.
 */
public class SearchByStringRequest extends JsonForGsonRequest<SearchByStringWrapper> {

    public static final String SEARCH_STRING_PARAM = "searchString";
    public static final String PAGE_PARAM = "page";

    public SearchByStringRequest(Map<String, String> params, Response.ErrorListener errorListener) {
        super(VoxProviderUrls.SEARCH_REQUEST, params, SearchByStringWrapper.class, null, errorListener);
    }
}

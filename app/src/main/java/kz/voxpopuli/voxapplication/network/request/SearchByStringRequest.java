package kz.voxpopuli.voxapplication.network.request;

import com.android.volley.Response;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.search.string.SearchByStringWrapper;

/**
 * Created by user on 15.04.15.
 */
public class SearchByStringRequest extends GsonRequest<SearchByStringWrapper> {

    public SearchByStringRequest(String searchString, Response.ErrorListener errorListener) {
        super(Method.POST, VoxProviderUrls.SEARCH_REQUEST, SearchByStringWrapper.class,
                null, errorListener);
    }
}

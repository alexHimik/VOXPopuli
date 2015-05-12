package kz.voxpopuli.voxapplication.network.request;

import android.content.Context;

import com.android.volley.Response;

import java.util.Map;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.search.top.SearchByTopWrapper;

/**
 * Created by user on 15.04.15.
 */
public class SearchByTopRequest extends JsonForGsonRequest<SearchByTopWrapper> {

    public static final String TOP_MATERIALS_PARAM = "top_materials";

    public SearchByTopRequest(Context context, Map<String, String> params, Response.ErrorListener errorListener) {
        super(context, VoxProviderUrls.SEARCH_REQUEST, params, SearchByTopWrapper.class, null,
                errorListener, true);
    }
}

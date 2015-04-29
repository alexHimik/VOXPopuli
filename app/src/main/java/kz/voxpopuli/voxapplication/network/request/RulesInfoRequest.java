package kz.voxpopuli.voxapplication.network.request;

import com.android.volley.Response;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.info.InfoDataWrapper;

/**
 * Created by user on 29.04.15.
 */
public class RulesInfoRequest extends GsonRequest<InfoDataWrapper> {

    public RulesInfoRequest(Response.ErrorListener errorListener) {
        super(Method.GET, VoxProviderUrls.RULES_INFO_REQUEST, InfoDataWrapper.class,
                null, errorListener);
    }
}

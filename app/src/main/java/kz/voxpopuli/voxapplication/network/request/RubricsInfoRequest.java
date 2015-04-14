package kz.voxpopuli.voxapplication.network.request;

import com.android.volley.Response;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.rubrics.RubricsDataWrapper;
import kz.voxpopuli.voxapplication.network.wrappers.rubrics.RubricsInfo;

/**
 * Created by user on 10.04.15.
 */
public class RubricsInfoRequest extends GsonRequest<RubricsDataWrapper> {

    public RubricsInfoRequest(Response.ErrorListener errorListener) {
        super(Method.GET, VoxProviderUrls.RUBRICS_INFO, RubricsDataWrapper.class, null, errorListener);
    }
}

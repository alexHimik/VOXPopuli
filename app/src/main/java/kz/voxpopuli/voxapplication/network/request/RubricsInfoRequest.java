package kz.voxpopuli.voxapplication.network.request;

import android.content.Context;

import com.android.volley.Response;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.rubrics.RubricsDataWrapper;

/**
 * Created by user on 10.04.15.
 */
public class RubricsInfoRequest extends GsonRequest<RubricsDataWrapper> {

    public RubricsInfoRequest(Context context, Response.ErrorListener errorListener) {
        super(context, Method.GET, VoxProviderUrls.RUBRICS_INFO_REQUEST, RubricsDataWrapper.class,
                null, errorListener, true);
    }
}

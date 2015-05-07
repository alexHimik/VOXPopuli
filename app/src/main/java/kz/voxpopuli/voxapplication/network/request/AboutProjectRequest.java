package kz.voxpopuli.voxapplication.network.request;

import android.content.Context;

import com.android.volley.Response;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.info.InfoDataWrapper;

/**
 * Created by user on 29.04.15.
 */
public class AboutProjectRequest extends GsonRequest<InfoDataWrapper> {
    public AboutProjectRequest(Context context, Response.ErrorListener errorListener) {
        super(context, Method.GET, VoxProviderUrls.ABOUT_INFO_REQUEST, InfoDataWrapper.class,
                null, errorListener);
    }
}

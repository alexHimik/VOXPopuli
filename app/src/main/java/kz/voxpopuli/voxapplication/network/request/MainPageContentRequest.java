package kz.voxpopuli.voxapplication.network.request;

import android.content.Context;

import com.android.volley.Response;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.mpage.MainPageDataWrapper;

/**
 * Created by user on 10.04.15.
 */
public class MainPageContentRequest extends GsonRequest<MainPageDataWrapper> {

    public MainPageContentRequest(Context context, Response.ErrorListener errorListener) {
        super(context, Method.GET, VoxProviderUrls.MAIN_PAGE_CONTENT_REQUEST,
                MainPageDataWrapper.class, null, errorListener, true);
    }
}

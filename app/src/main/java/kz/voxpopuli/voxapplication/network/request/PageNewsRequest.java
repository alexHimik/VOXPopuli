package kz.voxpopuli.voxapplication.network.request;

import com.android.volley.Response;
import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.pnews.PageNewsWrapper;

public class PageNewsRequest extends GsonRequest<PageNewsWrapper> {

    public PageNewsRequest(Response.ErrorListener errorListener) {
        super(Method.GET, VoxProviderUrls.PAGE_NEWS,
                PageNewsWrapper.class, null, errorListener);
    }
}

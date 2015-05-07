package kz.voxpopuli.voxapplication.network.request;

import android.content.Context;

import com.android.volley.Response;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.tag.TagDataWrapper;

/**
 * Created by user on 27.04.15.
 */
public class TagInfoRequest extends GsonRequest<TagDataWrapper> {

    public TagInfoRequest(Context context, String tagId, Response.ErrorListener errorListener) {
        super(context, Method.GET, VoxProviderUrls.TAG_INFO_REQUEST.replaceAll(
                VoxProviderUrls.TAG_IDENTIFIER, tagId), TagDataWrapper.class, null,
                errorListener);
    }
}

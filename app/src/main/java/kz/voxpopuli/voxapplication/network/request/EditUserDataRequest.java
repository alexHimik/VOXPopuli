package kz.voxpopuli.voxapplication.network.request;

import android.content.Context;

import com.android.volley.Response;

import java.util.Map;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.udata.EditUserDataWrapper;

/**
 * Created by user on 24.04.15.
 */
public class EditUserDataRequest extends JsonForGsonRequest<EditUserDataWrapper> {

    public EditUserDataRequest(Context context, Map<String, String> params, Response.ErrorListener errorListener) {
        super(context, VoxProviderUrls.USER_DATA_EDIT_REQUEST, params, EditUserDataWrapper.class,
                null, errorListener);
    }
}

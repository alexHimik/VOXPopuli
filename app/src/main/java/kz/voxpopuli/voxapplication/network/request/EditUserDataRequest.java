package kz.voxpopuli.voxapplication.network.request;

import com.android.volley.Response;

import java.util.Map;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.udata.EditUserDataWrapper;

/**
 * Created by user on 24.04.15.
 */
public class EditUserDataRequest extends JsonForGsonRequest<EditUserDataWrapper> {

    public EditUserDataRequest(Map<String, String> params, Response.ErrorListener errorListener) {
        super(VoxProviderUrls.USER_DATA_EDIT_REQUEST, params, EditUserDataWrapper.class,
                null, errorListener);
    }
}

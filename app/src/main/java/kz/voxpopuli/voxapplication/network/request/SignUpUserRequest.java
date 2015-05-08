package kz.voxpopuli.voxapplication.network.request;

import android.content.Context;

import com.android.volley.Response;

import java.util.Map;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.udata.EditUserDataWrapper;

/**
 * Created by user on 27.04.15.
 */
public class SignUpUserRequest extends JsonForGsonRequest<EditUserDataWrapper> {
    public SignUpUserRequest(Context context, Map<String, String> params, Response.ErrorListener errorListener) {
        super(context, VoxProviderUrls.SIGN_UP_USER_REQUEST, params, EditUserDataWrapper.class, null, errorListener);
    }
}

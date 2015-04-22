package kz.voxpopuli.voxapplication.network.request;

import com.android.volley.Response;

import java.util.Map;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.udata.UserDataWrapper;

/**
 * Created by user on 21.04.15.
 */
public class SignInRequest extends JsonForGsonRequest<UserDataWrapper> {

    public SignInRequest(Map<String, String> params, Response.ErrorListener errorListener) {
        super(VoxProviderUrls.SIGN_IN_REQUEST, params, UserDataWrapper.class, null, errorListener);
    }
}

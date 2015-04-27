package kz.voxpopuli.voxapplication.network.request;

import com.android.volley.Response;

import java.util.Map;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.udata.UserData;

/**
 * Created by user on 27.04.15.
 */
public class SignUpUserRequest extends JsonForGsonRequest<UserData> {
    public SignUpUserRequest(Map<String, String> params, Response.ErrorListener errorListener) {
        super(VoxProviderUrls.SIGN_UP_USER_REQUEST, params, UserData.class, null, errorListener);
    }
}

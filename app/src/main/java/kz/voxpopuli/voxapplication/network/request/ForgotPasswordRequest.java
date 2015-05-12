package kz.voxpopuli.voxapplication.network.request;


import android.content.Context;

import com.android.volley.Response;

import java.util.Map;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.ForgotPasswordWrapper;

public class ForgotPasswordRequest extends JsonForGsonRequest<ForgotPasswordWrapper> {
    public ForgotPasswordRequest(Context context, Map<String, String> params, Response.ErrorListener errorListener) {
        super(context, VoxProviderUrls.FORGOT_PAS_REQUEST, params, ForgotPasswordWrapper.class, null,
                errorListener, true);
    }
}

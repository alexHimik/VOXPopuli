package kz.voxpopuli.voxapplication.network.request;

import android.content.Context;

import com.android.volley.Response;

import java.util.Map;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.udata.EditUserDataWrapper;

/**
 * Created by user on 05.05.15.
 */
public class TransferSocialDataRequest extends JsonForGsonRequest<EditUserDataWrapper> {

    public TransferSocialDataRequest(Context context, Map<String, String> params, Response.ErrorListener errorListener) {
        super(context, VoxProviderUrls.SEND_SOCIAL_USER_DATA_REQUEST, params,
                EditUserDataWrapper.class, null, errorListener, true);
    }
}

package kz.voxpopuli.voxapplication.network.request;

import android.content.Context;

import com.android.volley.Response;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.comments.user.UserCommentsWrapper;

/**
 * Created by user on 23.04.15.
 */
public class UserCommentsRequest extends GsonRequest<UserCommentsWrapper> {

    public UserCommentsRequest(Context context, String userId, Response.ErrorListener errorListener) {
        super(context, Method.GET, VoxProviderUrls.USER_COMMENTS_REQUEST.replaceAll(
                VoxProviderUrls.USER_ID_IDENTIFIER, userId),
                UserCommentsWrapper.class, null, errorListener);
    }
}

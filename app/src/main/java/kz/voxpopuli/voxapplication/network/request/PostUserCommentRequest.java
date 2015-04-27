package kz.voxpopuli.voxapplication.network.request;

import com.android.volley.Response;

import java.util.Map;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.comments.PostUserCommentWrapper;

/**
 * Created by user on 27.04.15.
 */
public class PostUserCommentRequest extends JsonForGsonRequest<PostUserCommentWrapper> {
    public PostUserCommentRequest(Map<String, String> params, Response.ErrorListener errorListener) {
        super(VoxProviderUrls.POST_ARTICLE_COMMENT_REQUEST, params, PostUserCommentWrapper.class,
                null, errorListener);
    }
}

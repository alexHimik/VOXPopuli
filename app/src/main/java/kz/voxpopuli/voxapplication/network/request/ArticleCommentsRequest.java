package kz.voxpopuli.voxapplication.network.request;

import android.content.Context;

import com.android.volley.Response;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.comments.article.ArticleCommentsWrapper;

/**
 * Created by user on 27.04.15.
 */
public class ArticleCommentsRequest extends GsonRequest<ArticleCommentsWrapper> {
    public ArticleCommentsRequest(Context context, String articleId, String page, Response.ErrorListener errorListener) {
        super(context, Method.GET, VoxProviderUrls.ARTICLE_COMMENTS_REQUEST.replaceAll(
                VoxProviderUrls.ARTICLE_IDENTIFIER, articleId).replaceAll(
                VoxProviderUrls.RUBRIC_PAGE_IDENTIFIER, page), ArticleCommentsWrapper.class,
                null, errorListener, true);
    }
}

package kz.voxpopuli.voxapplication.network.request;

import android.content.Context;

import com.android.volley.Response;

import java.util.Map;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.article.ArticleDataWrapper;

/**
 * Created by user on 23.04.15.
 */
public class ArticleContentRequest extends GsonRequest<ArticleDataWrapper> {

    public ArticleContentRequest(Context context, String articleId, Response.ErrorListener errorListener) {
        super(context, Method.GET, VoxProviderUrls.NEWS_PAGE_DATA_REQUEST.replaceAll(
                VoxProviderUrls.NEW_IDENTIFIER, articleId), ArticleDataWrapper.class, null,
                errorListener, true);
    }
}

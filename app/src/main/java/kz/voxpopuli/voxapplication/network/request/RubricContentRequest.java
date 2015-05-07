package kz.voxpopuli.voxapplication.network.request;

import android.content.Context;

import com.android.volley.Response;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.rubrics.RubricContentWrapper;

/**
 * Created by user on 14.04.15.
 */
public class RubricContentRequest extends GsonRequest<RubricContentWrapper> {

    public RubricContentRequest(Context context, String rubricId, int page, Response.ErrorListener errorListener) {
        super(context, Method.GET, VoxProviderUrls.RUBRIC_CONTENT_REQUEST.replaceAll(
                VoxProviderUrls.RUBRIC_ID_IDENTIFIER, rubricId).replaceAll(
                VoxProviderUrls.RUBRIC_PAGE_IDENTIFIER, String.valueOf(page)),
                RubricContentWrapper.class, null, errorListener);
    }
}

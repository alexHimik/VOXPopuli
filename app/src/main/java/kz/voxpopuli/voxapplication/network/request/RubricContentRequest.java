package kz.voxpopuli.voxapplication.network.request;

import com.android.volley.Response;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.rubrics.RubricContentWrapper;

/**
 * Created by user on 14.04.15.
 */
public class RubricContentRequest extends GsonRequest<RubricContentWrapper> {

    public RubricContentRequest(String rubricId, Response.ErrorListener errorListener) {
        super(Method.GET, VoxProviderUrls.RUBRIC_CONTENT.replaceAll(
                VoxProviderUrls.RUBRIC_ID_IDENTIFIER, rubricId),
                RubricContentWrapper.class, null, errorListener);
    }
}

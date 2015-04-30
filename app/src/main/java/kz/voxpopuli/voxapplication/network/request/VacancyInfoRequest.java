package kz.voxpopuli.voxapplication.network.request;

import com.android.volley.Response;

import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.info.InfoDataWrapper;

/**
 * Created by user on 29.04.15.
 */
public class VacancyInfoRequest extends GsonRequest<InfoDataWrapper> {
    public VacancyInfoRequest(Response.ErrorListener errorListener) {
        super(Method.GET, VoxProviderUrls.VACANCY_INFO_REQUEST, InfoDataWrapper.class,
                null, errorListener);
    }
}

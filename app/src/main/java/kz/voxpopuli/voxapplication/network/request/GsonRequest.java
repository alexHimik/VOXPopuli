package kz.voxpopuli.voxapplication.network.request;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.Html;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import de.greenrobot.event.EventBus;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.dialog.TransparentProgressDialog;
import kz.voxpopuli.voxapplication.events.ErrorEvent;
import kz.voxpopuli.voxapplication.network.util.ServerErrorContainer;

/**
 * Created by user on 31.03.15.
 */
public class GsonRequest<T> extends Request<T> {

    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;

    private TransparentProgressDialog progress;

    public GsonRequest(Context context, int method, String url, Class<T> clazz,
                       Map<String, String> headers, Response.ErrorListener errorListener, boolean userProgress) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        setRetryPolicy(new DefaultRetryPolicy(JsonForGsonRequest.NETWORK_TIMEOUT_LIMIT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if(userProgress) {
            progress = new TransparentProgressDialog(context, R.drawable.spinner_white);
            progress.setCancelable(false);
            progress.show();
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers)).replaceAll("&quot;", "'");
            if(json.contains(JsonForGsonRequest.ERROR_KEY)) {
                JsonForGsonRequest.ServerErrorWrapper error = gson.fromJson(json,
                        JsonForGsonRequest.ServerErrorWrapper.class);
                ErrorEvent errorEvent = new ErrorEvent(ServerErrorContainer.getErrorMessage(
                        error.getError()), Integer.parseInt(error.getError()));
                EventBus.getDefault().post(errorEvent);

                if(progress != null) {
                    progress.dismiss();
                    progress = null;
                }
                return null;
            }
            return Response.success(
                    gson.fromJson(Html.fromHtml(json).toString(), clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            if(progress != null) {
                progress.dismiss();
                progress = null;
            }
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            if(progress != null) {
                progress.dismiss();
                progress = null;
            }
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        if(progress != null) {
            progress.dismiss();
            progress = null;
        }
        EventBus.getDefault().post(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers == null ? super.getHeaders() : headers;
    }
}

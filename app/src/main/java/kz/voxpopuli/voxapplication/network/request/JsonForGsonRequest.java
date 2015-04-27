package kz.voxpopuli.voxapplication.network.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import kz.voxpopuli.voxapplication.events.ErrorEvent;
import kz.voxpopuli.voxapplication.network.util.ServerErrorContainer;

/**
 * Created by user on 15.04.15.
 */
public class JsonForGsonRequest<T> extends Request<T> {

    /** Charset for request. */
    private static final String PROTOCOL_CHARSET = "utf-8";

    /** Content type for request. */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/x-www-form-urlencoded; charset=%s", PROTOCOL_CHARSET);

    public static final String ERROR_KEY = "error";

    protected final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private Map<String, String> params;

    public JsonForGsonRequest(String url, Map<String, String> params,
                 Class<T> clazz, Map<String, String> headers, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);

        this.clazz = clazz;
        this.headers = headers;
        this.params = params;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            if(json.contains(ERROR_KEY)) {
                ServerErrorWrapper error = gson.fromJson(json, ServerErrorWrapper.class);
                ErrorEvent errorEvent = new ErrorEvent(ServerErrorContainer.getErrorMessage(
                        error.getError()), Integer.parseInt(error.getError()));
                EventBus.getDefault().post(errorEvent);
                return null;
            }
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        if(response != null) {
            EventBus.getDefault().post(response);
        }
    }

    @Override
    protected Map<String, String> getPostParams() throws AuthFailureError {
        return getParams();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : new HashMap<String, String>();
    }

    @Override
    protected String getParamsEncoding() {
        return PROTOCOL_CHARSET;
    }

    @Override
    protected String getPostParamsEncoding() {
        return getParamsEncoding();
    }

    /**
     * @deprecated Use {@link #getBodyContentType()}.
     */
    @Override
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers == null ? super.getHeaders() : headers;
    }

    public class ServerErrorWrapper {
        private String error;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
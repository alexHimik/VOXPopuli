package kz.voxpopuli.voxapplication.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by user on 24.03.15.
 */
public class VolleyNetworkProvider {

    private static VolleyNetworkProvider instance;
    private static Context context;

    private RequestQueue requestQueue;


    private VolleyNetworkProvider(Context ctn) {
    }

    public static synchronized VolleyNetworkProvider getInstance(Context ctn) {
        if(instance == null) {
            instance = new VolleyNetworkProvider(ctn);
        }
        context = ctn;
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context, new OkHttpStack(new OkHttpClient()));
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}

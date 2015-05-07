package kz.voxpopuli.voxapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.android.volley.Request;
import com.devspark.robototextview.widget.RobotoTextView;

import de.greenrobot.event.EventBus;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.AboutProjectRequest;
import kz.voxpopuli.voxapplication.network.request.RulesInfoRequest;
import kz.voxpopuli.voxapplication.network.request.VacancyInfoRequest;
import kz.voxpopuli.voxapplication.network.wrappers.info.InfoDataWrapper;

/**
 * Created by user on 29.04.15.
 */
public class InfoFragment extends BaseFragment {

    public static final String TAG = "InfoFragment";
    public static final int FRAGMENT_ID = 100501;

    public static final String INFO_TITLE_KEY = "info_title";
    public static final String REQUEST_KEY = "request";

    private final String HTML_BODY = "<html><body>%content%</body></html>";

    private WebView webView;
    private String title;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle data = getArguments();
        title = data.getString(INFO_TITLE_KEY);
        Request request = getRequest(data.getInt(REQUEST_KEY));
        VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(request);
        webView = (WebView)inflater.inflate(R.layout.info_fragment_layout, container, false);
        View customBar = getActionBarCustomView(inflater);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        initViews();
        return webView;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void initActionBarItems() {
        rightBarItem.setVisibility(View.INVISIBLE);
        leftbarItem.setBackgroundResource(R.drawable.vox_ic_white_arrow);
        ((RobotoTextView)centerBatItem).setText(title);
        leftItemTouch.setOnClickListener(clickListener);
    }

    private void initViews() {
        webView.getSettings().setJavaScriptEnabled(true);
    }

    public void onEvent(InfoDataWrapper infoDataWrapper) {
        webView.loadDataWithBaseURL(null, HTML_BODY.replaceAll("%content%",
                infoDataWrapper.getArticle().getContent().get(0).getData()), "text/html", "en_US","");
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().onBackPressed();
        }
    };

    private Request getRequest(int type) {
        switch(type) {
            case 0: {
                return new RulesInfoRequest(getActivity(), (MainActivity)getActivity());
            }
            case 1: {
                return new VacancyInfoRequest(getActivity(), (MainActivity)getActivity());
            }
            case 2: {
                return new AboutProjectRequest(getActivity(), (MainActivity)getActivity());
            }
            default: {
                return null;
            }
        }
    }
}

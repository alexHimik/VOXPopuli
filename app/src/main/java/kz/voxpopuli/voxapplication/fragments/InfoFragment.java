package kz.voxpopuli.voxapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.devspark.robototextview.widget.RobotoTextView;

import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.RulesInfoRequest;
import kz.voxpopuli.voxapplication.network.wrappers.info.InfoDataWrapper;

/**
 * Created by user on 29.04.15.
 */
public class InfoFragment extends BaseFragment {

    public static final String TAG = "InfoFragment";
    public static final int FRAGMENT_ID = 100501;

    public static final String INFO_TITLE_KEY = "info_title";

    private WebView container;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(
                new RulesInfoRequest((MainActivity)getActivity()));
        View customBar = getActionBarCustomView(inflater);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        container = (WebView)inflater.inflate(R.layout.info_fragment_layout, null);
        initViews();
        return container;
    }

    @Override
    public void initActionBarItems() {
        rightBarItem.setVisibility(View.INVISIBLE);
        leftbarItem.setBackgroundResource(R.drawable.vox_ic_white_arrow);
        String title = getArguments().getString(INFO_TITLE_KEY);
        ((RobotoTextView)centerBatItem).setText(title);
    }

    private void initViews() {
        container.getSettings().setJavaScriptEnabled(true);
    }

    public void onEvent(InfoDataWrapper infoDataWrapper) {

    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }
}

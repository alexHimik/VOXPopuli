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

public class VacanciesFragment extends BaseFragment {
    public static final String TAG = "VacanciesFragment";
    public static final int FRAGMENT_ID = 123;
    WebView mWebView;
    private View parent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View customBar = super.getActionBarCustomView(inflater);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(customBar);

        parent = inflater.inflate(R.layout.about, container, false);
        mWebView = (WebView) parent.findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.voxpopuli.kz/job.html");
        return parent;
    }


    @Override
    public void initActionBarItems() {
        rightBarItem.setVisibility(View.INVISIBLE);
        leftItemTouch.setOnClickListener(barClickListener);
        ((RobotoTextView)centerBatItem).setText(getString(R.string.vacans_title));
        leftbarItem.setBackgroundResource(R.drawable.vox_ic_white_arrow);
    }

    private View.OnClickListener barClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).onBackPressed();
        }
    };

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }
}

package kz.voxpopuli.voxapplication.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.List;

import de.greenrobot.event.EventBus;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.adapter.PageAdapter;
import kz.voxpopuli.voxapplication.events.CategorySelectedEvent;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.ArticleContentRequest;
import kz.voxpopuli.voxapplication.network.wrappers.article.Article;
import kz.voxpopuli.voxapplication.network.wrappers.article.ArticleDataWrapper;
import kz.voxpopuli.voxapplication.network.wrappers.article.Author;
import kz.voxpopuli.voxapplication.network.wrappers.article.Content;
import kz.voxpopuli.voxapplication.network.wrappers.article.Similar;
import kz.voxpopuli.voxapplication.network.wrappers.article.Tag;

public class About extends BaseFragment {
    public static final String TAG = "About";
    public static final int FRAGMENT_ID = 121;
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
        mWebView.loadUrl("http://www.voxpopuli.kz/about.html");
        return parent;
    }


    @Override
    public void initActionBarItems() {
        rightBarItem.setVisibility(View.INVISIBLE);
        leftItemTouch.setOnClickListener(barClickListener);
        ((RobotoTextView)centerBatItem).setText(getString(R.string.about_title));
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

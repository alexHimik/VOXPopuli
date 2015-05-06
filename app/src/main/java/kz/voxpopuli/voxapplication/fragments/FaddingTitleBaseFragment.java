package kz.voxpopuli.voxapplication.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import kz.voxpopuli.voxapplication.R;

/**
 * Created by user on 26.03.15.
 */
public abstract class FaddingTitleBaseFragment extends Fragment implements BackStackDataDescriber {

    protected FadingActionBarHelper mFadingHelper;
    protected SwipyRefreshLayout swipyRefreshLayout;
    protected Bundle mArguments;
    protected ImageView faddingHeader;
    protected ListView fragmentList;

    protected View leftbarItem;
    protected View centerBatItem;
    protected View rightBarItem;
    protected View leftItemTouch;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mArguments = getArguments();
        mFadingHelper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.default_action_bar_color)
                .headerLayout(R.layout.fadding_fragment_header)
                .contentLayout(R.layout.fadding_fragment_content);
        mFadingHelper.initActionBar(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActionBarCustomView(inflater);
        View view = mFadingHelper.createView(inflater);
        swipyRefreshLayout = (SwipyRefreshLayout)view.findViewById(R.id.activity_main_swipe_refresh_layout);
        faddingHeader = (ImageView)view.findViewById(R.id.image_header);
        fragmentList = (ListView)view.findViewById(android.R.id.list);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mFadingHelper != null) {
            mFadingHelper.resetActionBarAlfa();
        }
    }

    public View getActionBarCustomView(LayoutInflater inflater) {
        RelativeLayout barLayout = (RelativeLayout)inflater.inflate(R.layout.action_bar_header,
                null);
        leftbarItem = barLayout.findViewById(R.id.left_drawer_item);
        rightBarItem = barLayout.findViewById(R.id.right_drawer_item);
        centerBatItem = barLayout.findViewById(R.id.action_bar_title);
        leftItemTouch = barLayout.findViewById(R.id.left_drawer_item_touch);
        initActionBarItems();
        return barLayout;
    }

    public abstract void initActionBarItems();
}

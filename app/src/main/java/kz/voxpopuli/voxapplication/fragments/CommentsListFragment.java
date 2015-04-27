package kz.voxpopuli.voxapplication.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.devspark.robototextview.widget.RobotoTextView;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.adapter.CommentsListAdapter;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.ArticleCommentsRequest;
import kz.voxpopuli.voxapplication.network.wrappers.comments.article.ArcticleComment;
import kz.voxpopuli.voxapplication.network.wrappers.comments.article.ArticleCommentsWrapper;
import kz.voxpopuli.voxapplication.tools.UserInfoTools;

public class CommentsListFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener {

    public static final String TAG = "CommentsListFragment";
    public static final int FRAGMENT_ID = 200;

    private CommentsListAdapter commentsAdapter;
    private List<ArcticleComment> comments = new LinkedList<>();

    private ListView lv;
    private ImageView send;
    private ImageView iv;
    private SwipyRefreshLayout refreshLayout;

    private int page = 1;
    private String articleId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View parent = inflater.inflate(R.layout.comments_list, container,false);
        initViews(parent);
        Bundle data = getArguments();
        articleId = data.getString(NewsPageFragment.ARTICLE_KEY);
        VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(
                new ArticleCommentsRequest(articleId, String.valueOf(page),
                        (MainActivity)getActivity()));
        return parent;
    }

    @Override
    public void initActionBarItems() {
        rightBarItem.setVisibility(View.INVISIBLE);
        leftbarItem.setOnClickListener(barClickListener);
        leftbarItem.setBackgroundResource(R.drawable.vox_ic_white_arrow);
        ((RobotoTextView)centerBatItem).setText(getString(R.string.article_comments_screen_header));
    }

    @Override
    public View getActionBarCustomView(LayoutInflater inflater) {
        View customBar = super.getActionBarCustomView(inflater);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        return customBar;
    }

    private void initViews(View parent) {
        lv = (ListView) parent.findViewById(R.id.lv);
        commentsAdapter = new CommentsListAdapter(this, comments);
        send = (ImageView) parent.findViewById(R.id.send);

        refreshLayout = (SwipyRefreshLayout)parent.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.vox_red),
                getActivity().getResources().getColor(R.color.vox_red),
                getActivity().getResources().getColor(R.color.vox_red));

        send.setOnClickListener(barClickListener);
        lv.setAdapter(commentsAdapter);
        iv = (ImageView) parent.findViewById(R.id.imv_com_b);

        UserInfoTools uit = new UserInfoTools();
        BitmapPool pool = Glide.get(getActivity()).getBitmapPool();
        Glide.with(this).load(uit.getUserAvatarUrl(getActivity())).bitmapTransform(
                new CropCircleTransformation(pool)).into(iv);
    }

    private View.OnClickListener barClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.right_drawer_item) {
                ((MainActivity)getActivity()).onBackPressed();
            } else if(v.getId() == R.id.send) {

            }
        }
    };

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

    public void onEvent(ArticleCommentsWrapper wrapper) {
        if(wrapper.getArcticleComments().size() > 0) {
            comments.addAll(wrapper.getArcticleComments());
            commentsAdapter.notifyDataSetChanged();

        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection swipyRefreshLayoutDirection) {
        if(swipyRefreshLayoutDirection == SwipyRefreshLayoutDirection.BOTTOM) {
            if(page < 10) {
                page = page + 1;
                ArticleCommentsRequest request = new ArticleCommentsRequest(articleId, String.valueOf(page),
                        (MainActivity)getActivity());
                VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(request);
            } else {
                refreshLayout.setRefreshing(false);
            }
        }
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

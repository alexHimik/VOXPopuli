package kz.voxpopuli.voxapplication.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.devspark.robototextview.widget.RobotoEditText;
import com.devspark.robototextview.widget.RobotoTextView;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.adapter.CommentsListAdapter;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.ArticleCommentsRequest;
import kz.voxpopuli.voxapplication.network.request.PostUserCommentRequest;
import kz.voxpopuli.voxapplication.network.request.UserCommentsRequest;
import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.comments.PostUserCommentWrapper;
import kz.voxpopuli.voxapplication.network.wrappers.comments.article.ArcticleComment;
import kz.voxpopuli.voxapplication.network.wrappers.comments.article.ArticleCommentsWrapper;
import kz.voxpopuli.voxapplication.tools.DialogTools;
import kz.voxpopuli.voxapplication.tools.MD5Hasher;
import kz.voxpopuli.voxapplication.tools.TextInputsValidators;
import kz.voxpopuli.voxapplication.tools.UserInfoTools;

public class CommentsListFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener {

    public static final String TAG = "CommentsListFragment";
    public static final int FRAGMENT_ID = 200;

    private CommentsListAdapter commentsAdapter;
    private List<ArcticleComment> comments = new LinkedList<>();

    private ListView lv;
    private ImageView send;
    private ImageView iv;
    private RobotoEditText comment;
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
        comment = (RobotoEditText)parent.findViewById(R.id.rt);

        refreshLayout = (SwipyRefreshLayout)parent.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.vox_red),
                getActivity().getResources().getColor(R.color.vox_red),
                getActivity().getResources().getColor(R.color.vox_red));

        send.setOnClickListener(barClickListener);
        lv.setAdapter(commentsAdapter);
        iv = (ImageView) parent.findViewById(R.id.imv_com_b);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Bundle bnd = new Bundle();
                bnd.putString("AUTHOR_ID",comments.get(position).getAuthorId());
                bnd.putString("AUTHOR_NAME",comments.get(position).getAuthorName());
                bnd.putString("AUTHOR_AVATAR",comments.get(position).getAuthorAvatar());

                ((MainActivity)getActivity()).handleFragmentSwitching(UserProfileFragment.FRAGMENT_ID,
                        bnd);
//                VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(new UserCommentsRequest(
//                        comments.get(position).getAuthorId(), ((MainActivity)getActivity())));
            }
        });

        UserInfoTools uit = new UserInfoTools();
        BitmapPool pool = Glide.get(getActivity()).getBitmapPool();
        Glide.with(this).load(uit.getUserAvatarUrl(getActivity())).bitmapTransform(
                new CropCircleTransformation(pool)).into(iv);
    }

    private View.OnClickListener barClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.left_drawer_item) {
                ((MainActivity)getActivity()).onBackPressed();
            } else if(v.getId() == R.id.send) {
                postUserComment();
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

    public void onEvent(PostUserCommentWrapper postUserCommentWrapper) {
        if(postUserCommentWrapper != null && "OK".equals(postUserCommentWrapper.getResult())) {
            comment.setText("");
            VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(
                    new ArticleCommentsRequest(articleId, String.valueOf(page),
                            (MainActivity)getActivity()));
        }
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

    private void postUserComment() {
        if(!TextInputsValidators.isInputEmpty(comment)) {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("id", articleId);
            params.put("user_id", UserInfoTools.getUSerId(getActivity()));
            params.put("parent_id", "0");
            params.put("text", comment.getText().toString());
            params.put("", VoxProviderUrls.SALT);

            String signature = MD5Hasher.getHash(params);
            params.remove("");
            params.put("signature", signature);
            VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(
                    new PostUserCommentRequest(params, (MainActivity)getActivity()));
        } else {
            DialogTools.showInfoDialog(getActivity(), getString(R.string.error_dialog_title),
                    getString(R.string.empty_field_alarm));
        }
    }
}

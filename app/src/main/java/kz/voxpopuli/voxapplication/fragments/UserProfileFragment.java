package kz.voxpopuli.voxapplication.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.devspark.robototextview.widget.RobotoTextView;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.adapter.UserProfileCommentsAdapter;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.UserCommentsRequest;
import kz.voxpopuli.voxapplication.network.wrappers.comments.user.Comment;
import kz.voxpopuli.voxapplication.network.wrappers.comments.user.UserCommentsWrapper;
import kz.voxpopuli.voxapplication.tools.UserInfoTools;

public class UserProfileFragment extends FaddingTitleBaseFragment {

    public static final String TAG = "UserProfileFragment";
    public static final int FRAGMENT_ID = 111;

    private ImageView bluredBack;
    private ImageView userAvatar;
    private RobotoTextView userName;

    private List<Comment> comments = new LinkedList<>();
    private UserProfileCommentsAdapter adapter;
    private String authorAvatar, authorName, authorId;
    private Boolean authorRead = true;

    private View createdFragmentLayout;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mArguments = getArguments();
        mFadingHelper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.default_action_bar_color)
                .headerLayout(R.layout.user_profile_header)
                .contentLayout(R.layout.user_profile_fragment_layout);
        mFadingHelper.initActionBar(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle data = getArguments();
        if (data != null){
            authorAvatar = data.getString("AUTHOR_AVATAR");
            authorName = data.getString("AUTHOR_NAME");
            authorId = data.getString("AUTHOR_ID");
            authorRead = false;
        } else {
            authorAvatar = UserInfoTools.getUserAvatarUrl(getActivity());
            authorName = UserInfoTools.getUserFirstName(getActivity()) + " " +
                    UserInfoTools.getUserLastName(getActivity());
            authorId = UserInfoTools.getUSerId(getActivity());
            authorRead = true;
        }

        View customBar = getActionBarCustomView(inflater);

        if(createdFragmentLayout == null) {
            createdFragmentLayout = mFadingHelper.createView(inflater);
        }

        mFadingHelper.resetActionBarAlfa();
        ((MainActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(
                mFadingHelper.getmActionBarBackgroundDrawable());

        fragmentList = (ListView)createdFragmentLayout.findViewById(android.R.id.list);
        bluredBack = (ImageView)createdFragmentLayout.findViewById(R.id.user_avatar_back);
        userAvatar = (ImageView)createdFragmentLayout.findViewById(R.id.user_avatar);
        userName = (RobotoTextView)createdFragmentLayout.findViewById(R.id.user_name);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(customBar);

        initViews();
        return createdFragmentLayout;
    }

    @Override
    public void onStart() {
Log.d("QWERT","Start");
        super.onStart();
        EventBus.getDefault().register(this);
Log.d("QWERT","Volley");
        VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(new UserCommentsRequest(
               getActivity(), authorId, ((MainActivity)getActivity())));
Log.d("QWERT","Volley 1111");
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void initActionBarItems() {
        leftbarItem.setBackgroundResource(R.drawable.vox_ic_white_arrow);
        leftItemTouch.setOnClickListener(clickListener);
        rightBarItem.setBackgroundResource(R.drawable.vox_ic_white_edit);

        if(authorRead) {
            rightBarItem.setOnClickListener(clickListener);
        } else {
            rightBarItem.setVisibility(View.INVISIBLE);
        }

        ((RobotoTextView)centerBatItem).setText("");
    }

    private void initViews() {
        BitmapPool pool = Glide.get(getActivity()).getBitmapPool();
        Glide.with(this).load(authorAvatar).centerCrop().
                bitmapTransform(new BlurTransformation(getActivity(), pool, 7)).into(bluredBack);
        Glide.with(this).load(authorAvatar).
                centerCrop().bitmapTransform(new CropCircleTransformation(pool)).into(userAvatar);

        userName.setText(authorName);
        adapter = new UserProfileCommentsAdapter(getActivity(), comments);
        fragmentList.setAdapter(adapter);
        fragmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position>0) {
                    Bundle bnd = new Bundle();
                    bnd.putString(NewsPageFragment.ARTICLE_KEY, comments.get(position - 1).getId());
                    ((MainActivity) getActivity()).handleFragmentSwitching(NewsPageFragment.FRAGMENT_ID,
                            bnd);
                }

            }
        });
    }

    public void onEvent(UserCommentsWrapper data) {
        comments.add(new Comment());
Log.d("QWERT","11111");
        if(data.getUserData() != null) {
            if(!comments.isEmpty()) {
                comments.clear();
            }
Log.d("QWERT","Name="+data.getUserData().getFirstName());
            comments.addAll(data.getUserData().getComments());
Log.d("QWERT","LL="+comments.size());
        }
        adapter.notifyDataSetChanged();
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
            if(v.getId() == R.id.left_drawer_item_touch) {
                ((MainActivity)getActivity()).onBackPressed();
            } else if(v.getId() == R.id.right_drawer_item) {
                ((MainActivity)getActivity()).getSupportFragmentManager().popBackStack();
                ((MainActivity)getActivity()).handleFragmentSwitching(
                        EditUserProfileFragment.FRAGMENT_ID, null);
            }
        }
    };
}

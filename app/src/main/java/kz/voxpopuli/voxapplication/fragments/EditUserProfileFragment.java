package kz.voxpopuli.voxapplication.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.devspark.robototextview.widget.RobotoEditText;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import de.greenrobot.event.EventBus;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.EditUserDataRequest;
import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.udata.EditUserDataWrapper;
import kz.voxpopuli.voxapplication.tools.MD5Hasher;
import kz.voxpopuli.voxapplication.tools.UserInfoTools;

/**
 * Created by user on 23.04.15.
 */
public class EditUserProfileFragment extends BaseFragment {

    public static final String TAG = "EditUserProfileFragment";
    public static final int FRAGMENT_ID = 150;

    private ImageButton avatarChangeBtn;
    private ImageView avatar;
    private RobotoEditText userName;
    private RobotoEditText userSurname;
    private RobotoEditText userNick;
    private RelativeLayout changePassword;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.user_profile_edit_fragment_layout, container, false);
        initViews(parent);
        View customBar = getActionBarCustomView(inflater);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setCustomView(customBar);

        return parent;
    }

    @Override
    public View getActionBarCustomView(LayoutInflater inflater) {
        RelativeLayout barLayout = (RelativeLayout)inflater.inflate(R.layout.settings_action_bar_header,
                null);
        leftbarItem = barLayout.findViewById(R.id.left_drawer_item);
        rightBarItem = barLayout.findViewById(R.id.right_drawer_item);
        centerBatItem = barLayout.findViewById(R.id.action_bar_title);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.vox_white)));
        initActionBarItems();
        return barLayout;
    }

    @Override
    public void initActionBarItems() {
        leftbarItem.setOnClickListener(clickListener);
        rightBarItem.setOnClickListener(clickListener);
        ((RobotoTextView)centerBatItem).setText(getString(R.string.edit_profile_screen_header_text));
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

    private void initViews(View parent) {
        avatarChangeBtn = (ImageButton)parent.findViewById(R.id.avatar_change_button);
        changePassword = (RelativeLayout)parent.findViewById(R.id.edit_profile_change_passw_place);
        avatar = (ImageView)parent.findViewById(R.id.user_avatar);
        userName = (RobotoEditText)parent.findViewById(R.id.profile_edit_name_input);
        userSurname = (RobotoEditText)parent.findViewById(R.id.profile_edit_surname_input);
        userNick = (RobotoEditText)parent.findViewById(R.id.profile_edit_nick_input);

        avatarChangeBtn.setOnClickListener(clickListener);
        changePassword.setOnClickListener(clickListener);

        prefilData();
    }

    private void saveUserChanges() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("id", UserInfoTools.getUSerId(getActivity()));
        params.put("password", UserInfoTools.getUserPassword(getActivity()));
        params.put("firstName", userName.getText().toString());
        params.put("lastName", userSurname.getText().toString());
        //params.put("image", "");
        params.put("", VoxProviderUrls.SALT);

        String signature = MD5Hasher.getHash(params);
        params.remove("");

        params.put("signature", signature);
        VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(
                new EditUserDataRequest(params, (MainActivity)getActivity()));
    }

    public void onEvent(EditUserDataWrapper editUserDataWrapper) {
        if(editUserDataWrapper != null) {
            UserInfoTools.saveUserFirstName(getActivity(),
                    editUserDataWrapper.getUserData().getFirstName());
            UserInfoTools.saveUserLastName(getActivity(),
                    editUserDataWrapper.getUserData().getLastName());
            UserInfoTools.saveUserId(getActivity(),
                    editUserDataWrapper.getUserData().getId());
            Toast.makeText(getActivity(), getString(R.string.user_data_saved_msg),
                    Toast.LENGTH_SHORT).show();
            restartPreviousFragment();
        }
    }

    private void restartPreviousFragment() {
        ((MainActivity)getActivity()).getSupportFragmentManager().popBackStack();
        ((MainActivity)getActivity()).handleFragmentSwitching(UserProfileFragment.FRAGMENT_ID, null);
    }

    private void prefilData() {
        String name = UserInfoTools.getUserFirstName(getActivity());
        if(name != null) {
            userName.setText(name);
        }

        String surname = UserInfoTools.getUserLastName(getActivity());
        if(surname != null) {
            userSurname.setText(surname);
        }

        String nick = UserInfoTools.getUserNick(getActivity());
        if(nick != null) {
            userNick.setText(nick);
        }

        String url = UserInfoTools.getUserAvatarUrl(getActivity());
        if(url != null) {
            BitmapPool pool = Glide.get(getActivity()).getBitmapPool();
            Glide.with(this).load(UserInfoTools.getUserAvatarUrl(getActivity())).
                    centerCrop().bitmapTransform(new CropCircleTransformation(pool)).into(avatar);
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

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.left_drawer_item) {
                restartPreviousFragment();
            } else if(v.getId() == R.id.right_drawer_item) {
                saveUserChanges();
            } else if(v.getId() == R.id.avatar_change_button) {

            } else if(v.getId() == R.id.edit_profile_change_passw_place) {
                ((MainActivity)getActivity()).handleFragmentSwitching(
                        ChangePasswordFragment.FRAGMENT_ID, null);
            }
        }
    };
}

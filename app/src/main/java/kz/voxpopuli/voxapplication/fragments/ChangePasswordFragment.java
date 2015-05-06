package kz.voxpopuli.voxapplication.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.devspark.robototextview.widget.RobotoEditText;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.LinkedHashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.EditUserDataRequest;
import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.udata.EditUserDataWrapper;
import kz.voxpopuli.voxapplication.tools.MD5Hasher;
import kz.voxpopuli.voxapplication.tools.UserInfoTools;

/**
 * Created by user on 24.04.15.
 */
public class ChangePasswordFragment extends BaseFragment {

    public static final String TAG = "";
    public static final int FRAGMENT_ID = 160;

    private RobotoEditText oldPassword;
    private RobotoEditText newPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.change_password_fragment_layout, container, false);
        initViews(parent);
        View customBar = getActionBarCustomView(inflater);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        return parent;
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
    public View getActionBarCustomView(LayoutInflater inflater) {
        RelativeLayout barLayout = (RelativeLayout)inflater.inflate(R.layout.settings_action_bar_header,
                null);
        leftbarItem = barLayout.findViewById(R.id.left_drawer_item);
        rightBarItem = barLayout.findViewById(R.id.right_drawer_item);
        centerBatItem = barLayout.findViewById(R.id.action_bar_title);
        leftItemTouch = barLayout.findViewById(R.id.left_drawer_item_touch);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.vox_white)));
        initActionBarItems();
        return barLayout;
    }

    @Override
    public void initActionBarItems() {
        leftItemTouch.setOnClickListener(clickListener);
        rightBarItem.setOnClickListener(clickListener);
        ((RobotoTextView)centerBatItem).setText(getString(R.string.password_change_screen_header));
    }

    private void initViews(View parent) {
        oldPassword = (RobotoEditText)parent.findViewById(R.id.change_password_old_input);
        newPassword = (RobotoEditText)parent.findViewById(R.id.change_password_new_input);
    }

    private void updateUserPassword() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("id", UserInfoTools.getUSerId(getActivity()));
        params.put("password", newPassword.getText().toString());
        params.put("firstName", UserInfoTools.getUserFirstName(getActivity()));
        params.put("lastName", UserInfoTools.getUserLastName(getActivity()));
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
            getActivity().onBackPressed();
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
            if(v.getId() == R.id.left_drawer_item_touch) {
                getActivity().onBackPressed();
            } else if(v.getId() == R.id.right_drawer_item) {
                updateUserPassword();
            }
        }
    };
}

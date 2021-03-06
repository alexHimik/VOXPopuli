package kz.voxpopuli.voxapplication.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.devspark.robototextview.widget.RobotoEditText;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.LinkedHashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.ArticleCommentsRequest;
import kz.voxpopuli.voxapplication.network.request.ForgotPasswordRequest;
import kz.voxpopuli.voxapplication.network.request.SignUpUserRequest;
import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.ForgotPasswordWrapper;
import kz.voxpopuli.voxapplication.network.wrappers.comments.PostUserCommentWrapper;
import kz.voxpopuli.voxapplication.tools.DialogTools;
import kz.voxpopuli.voxapplication.tools.MD5Hasher;

/**
 * Created by user on 21.04.15.
 */
public class ForgotPasswordFragment extends BaseFragment {

    public static final String TAG = "ForgotPasswordFragment";
    public static final int FRAGMENT_ID = 106;

    private RobotoEditText emailInput;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.forgot_password_fragment_layout, container, false);
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
    public void initActionBarItems() {
        leftItemTouch.setOnClickListener(clickListener);
        rightBarItem.setOnClickListener(clickListener);
        ((RobotoTextView)centerBatItem).setText(getString(
                R.string.password_restore_screen_header_text));
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

    private void initViews(View parent) {
        emailInput = (RobotoEditText)parent.findViewById(R.id.restore_password_email_input);
    }

    private void doLoginReset() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("email", emailInput.getText().toString());
        params.put("", VoxProviderUrls.SALT);
        String signature = MD5Hasher.getHash(params);
        params.remove("");
        params.put("signature", signature);

        VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(
                new ForgotPasswordRequest(getActivity(), params, (MainActivity) getActivity()));
    }

    public void onEvent(ForgotPasswordWrapper forgotdWrapper) {
        if(forgotdWrapper != null && "OK".equals(forgotdWrapper.getResult())) {
            DialogTools.showInfoDialog(getActivity(), getString(R.string.error_dialog_title),
                    getString(R.string.forgot_password));
        }
        else DialogTools.showInfoDialog(getActivity(), getString(R.string.error_dialog_title),
                getString(R.string.forgot_no_email));
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
                doLoginReset();
            }
        }
    };
}

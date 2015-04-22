package kz.voxpopuli.voxapplication.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.devspark.robototextview.widget.RobotoEditText;
import com.devspark.robototextview.widget.RobotoTextView;
import com.github.gorbin.asne.facebook.FacebookSocialNetwork;
import com.github.gorbin.asne.googleplus.GooglePlusSocialNetwork;
import com.github.gorbin.asne.twitter.TwitterSocialNetwork;
import com.github.gorbin.asne.vk.VkSocialNetwork;

import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.tools.SocialNetworkUtils;

/**
 * Created by user on 21.04.15.
 */
public class RegistrationFragment extends BaseFragment {

    public static final String TAG = "RegistrationFragment";
    public static final int FRAGMENT_ID = 107;

    private RobotoEditText emailInput;
    private RobotoEditText nameInput;
    private RobotoEditText surnameInput;
    private RobotoEditText passwirdInput;
    private RobotoTextView passwordHideHint;
    private ImageButton fbSocial;
    private ImageButton twSocial;
    private ImageButton gpSocial;
    private ImageButton vkSocial;
    private ImageButton passwordHideBtn;

    private SocialNetworkUtils socialNetworkUtils;

    private boolean showPassword = true;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.registration_fragment_layout, container, false);
        initViews(parent);
        View customBar = getActionBarCustomView(inflater);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        socialNetworkUtils = new SocialNetworkUtils();
        socialNetworkUtils.initSocialManager(this);
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
        ((RobotoTextView)centerBatItem).setText(getString(R.string.registration_screen_header_text));
    }

    private void initViews(View parent) {
        emailInput = (RobotoEditText)parent.findViewById(R.id.registration_email_input);
        nameInput = (RobotoEditText)parent.findViewById(R.id.registration_name_input);
        surnameInput = (RobotoEditText)parent.findViewById(R.id.registration_surname_input);
        passwirdInput = (RobotoEditText)parent.findViewById(R.id.registration_password_input);
        fbSocial = (ImageButton)parent.findViewById(R.id.login_facebook);
        twSocial = (ImageButton)parent.findViewById(R.id.login_twitter);
        gpSocial = (ImageButton)parent.findViewById(R.id.login_google);
        vkSocial = (ImageButton)parent.findViewById(R.id.login_vk);
        passwordHideBtn = (ImageButton)parent.findViewById(R.id.login_password_hider);
        passwordHideHint = (RobotoTextView)parent.findViewById(R.id.login_password_hider_hint);

        fbSocial.setOnClickListener(clickListener);
        twSocial.setOnClickListener(clickListener);
        gpSocial.setOnClickListener(clickListener);
        vkSocial.setOnClickListener(clickListener);
        passwordHideBtn.setOnClickListener(clickListener);
    }

    private void togglePasswordVisibility() {
        if(showPassword) {
            passwordHideBtn.setBackgroundResource((R.drawable.ic_red_checkbox));
            passwordHideHint.setText(getString(R.string.show_password_string));
            passwirdInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            passwordHideBtn.setBackgroundResource((R.drawable.ic_grey_checkbox));
            passwordHideHint.setText(getString(R.string.hide_password_string));
            passwirdInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        showPassword = !showPassword;
    }

    private void doUserregistration() {

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
                getActivity().onBackPressed();
            } else if(v.getId() == R.id.right_drawer_item) {
                doUserregistration();
            } else if(v.getId() == R.id.login_facebook) {
                socialNetworkUtils.requestUserLogin(FacebookSocialNetwork.ID);
            } else if(v.getId() == R.id.login_twitter) {
                socialNetworkUtils.requestUserLogin(TwitterSocialNetwork.ID);
            } else if(v.getId() == R.id.login_google) {
                socialNetworkUtils.requestUserLogin(GooglePlusSocialNetwork.ID);
            } else if(v.getId() == R.id.login_vk) {
                socialNetworkUtils.requestUserLogin(VkSocialNetwork.ID);
            } else if(v.getId() == R.id.login_password_hider) {
                togglePasswordVisibility();
            }
        }
    };
}

package kz.voxpopuli.voxapplication.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestFutureTarget;
import com.devspark.robototextview.widget.RobotoEditText;
import com.devspark.robototextview.widget.RobotoTextView;
import com.github.gorbin.asne.facebook.FacebookSocialNetwork;
import com.github.gorbin.asne.googleplus.GooglePlusSocialNetwork;
import com.github.gorbin.asne.twitter.TwitterSocialNetwork;
import com.github.gorbin.asne.vk.VkSocialNetwork;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import de.greenrobot.event.EventBus;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.events.PersonInformationEvent;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.SignInRequest;
import kz.voxpopuli.voxapplication.network.request.TransferSocialDataRequest;
import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.udata.EditUserDataWrapper;
import kz.voxpopuli.voxapplication.network.wrappers.udata.UserDataWrapper;
import kz.voxpopuli.voxapplication.tools.DialogTools;
import kz.voxpopuli.voxapplication.tools.ImageUtils;
import kz.voxpopuli.voxapplication.tools.MD5Hasher;
import kz.voxpopuli.voxapplication.tools.SocialNetworkUtils;
import kz.voxpopuli.voxapplication.tools.TextInputsValidators;
import kz.voxpopuli.voxapplication.tools.UserInfoTools;

/**
 * Created by user on 20.04.15.
 */
public class LoginFragment extends BaseFragment {

    public static final String TAG = "LoginFragment";
    public static final int FRAGMENT_ID = 105;

    private RobotoTextView passwRecoveryHint;
    private RobotoTextView passwordHideHint;
    private RobotoTextView forgotPasswordLink;
    private RobotoEditText loginInput;
    private RobotoEditText passwordInput;
    private ImageButton fbSocial;
    private ImageButton twSocial;
    private ImageButton gpSocial;
    private ImageButton vkSocial;
    private ImageButton passwordHideBtn;
    private Button createAccountBtn;

    private SocialNetworkUtils socialNetworkUtils;

    private boolean showPassword = true;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.login_fragment_layout, container, false);
        initViews(parent);
        View customBar = getActionBarCustomView(inflater);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        return parent;
    }

    @Override
    public void onResume() {
        super.onResume();
        socialNetworkUtils = SocialNetworkUtils.getInstance(this);
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
        //socialNetworkUtils.disconectConnectedNetworks();
    }

    @Override
    public View getActionBarCustomView(LayoutInflater inflater) {
        RelativeLayout barLayout = (RelativeLayout)inflater.inflate(R.layout.settings_action_bar_header,
                null);
        leftbarItem = barLayout.findViewById(R.id.left_drawer_item);
        rightBarItem = barLayout.findViewById(R.id.right_drawer_item);
        centerBatItem = barLayout.findViewById(R.id.action_bar_title);
        leftItemTouch = barLayout.findViewById(R.id.left_drawer_item);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.vox_white)));
        initActionBarItems();
        return barLayout;
    }

    @Override
    public void initActionBarItems() {
        leftItemTouch.setOnClickListener(clickListener);
        rightBarItem.setOnClickListener(clickListener);
        ((RobotoTextView)centerBatItem).setText(getString(R.string.login_screen_header_text));
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }

    private void initViews(View parent) {
        passwRecoveryHint = (RobotoTextView)parent.findViewById(R.id.login_password_recover_hint);
        loginInput = (RobotoEditText)parent.findViewById(R.id.login_login_input);
        passwordInput = (RobotoEditText)parent.findViewById(R.id.login_password_input);
        fbSocial = (ImageButton)parent.findViewById(R.id.login_facebook);
        twSocial = (ImageButton)parent.findViewById(R.id.login_twitter);
        gpSocial = (ImageButton)parent.findViewById(R.id.login_google);
        vkSocial = (ImageButton)parent.findViewById(R.id.login_vk);
        createAccountBtn = (Button)parent.findViewById(R.id.login_create_account_btn);
        passwordHideBtn = (ImageButton)parent.findViewById(R.id.login_password_hider);
        passwordHideHint = (RobotoTextView)parent.findViewById(R.id.login_password_hider_hint);
        forgotPasswordLink = (RobotoTextView)parent.findViewById(R.id.login_forgot_password);

        fbSocial.setOnClickListener(clickListener);
        twSocial.setOnClickListener(clickListener);
        gpSocial.setOnClickListener(clickListener);
        vkSocial.setOnClickListener(clickListener);
        createAccountBtn.setOnClickListener(clickListener);
        passwordHideBtn.setOnClickListener(clickListener);
        forgotPasswordLink.setOnClickListener(clickListener);
    }

    private void doLogin() {
        if(TextInputsValidators.isInputEmpty(loginInput) ||
                TextInputsValidators.isInputEmpty(passwordInput)) {
            DialogTools.showInfoDialog(getActivity(), getString(R.string.error_dialog_title),
                    getString(R.string.empty_field_alarm));
            return;
        }
        if(!TextInputsValidators.isInputEmail(loginInput)) {
            DialogTools.showInfoDialog(getActivity(), getString(R.string.error_dialog_title),
                    getString(R.string.wrong_email_format_alarm));
            return;
        }
        if(!TextInputsValidators.isInputLengthEnought(passwordInput, 5)) {
            DialogTools.showInfoDialog(getActivity(), getString(R.string.error_dialog_title),
                    getString(R.string.wrong_password_length));
            return;
        }
        Map<String, String> params = new LinkedHashMap<>();
        params.put("email", loginInput.getText().toString());
        params.put("password", passwordInput.getText().toString());
        params.put("", VoxProviderUrls.SALT);
        String signature = MD5Hasher.getHash(params);
        params.remove("");
        params.put("signature", signature);

        VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(new SignInRequest(getActivity(), params,
                ((MainActivity)getActivity())));
    }

    private void togglePasswordVisibility() {
        if(showPassword) {
            passwordHideBtn.setBackgroundResource((R.drawable.ic_red_checkbox));
            passwordHideHint.setText(getString(R.string.show_password_string));
            passwordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            passwordHideBtn.setBackgroundResource((R.drawable.ic_grey_checkbox));
            passwordHideHint.setText(getString(R.string.hide_password_string));
            passwordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        showPassword = !showPassword;
    }

    public void onEvent(final PersonInformationEvent event) {
        if(event.getUserEmail() != null && event.getUserEmail().length() > 0) {
            UserInfoTools.saveUserLogin(getActivity(), event.getUserEmail());
            UserInfoTools.saveUserEmail(getActivity(), event.getUserEmail());
        }
        if(event.getUserName() != null && event.getUserName().contains(" ")) {
            UserInfoTools.saveUserFirstName(getActivity(), event.getUserName().split(" ")[0]);
            UserInfoTools.saveUserLastName(getActivity(), event.getUserName().split(" ")[1]);
        } else {
            UserInfoTools.saveUserFirstName(getActivity(), event.getUserName());
        }
        UserInfoTools.saveUserSocialProvider(getActivity(), event.getSocialNetworkId());
        UserInfoTools.saveUserId(getActivity(), event.getUserId());
        UserInfoTools.saveUserAvatarUrl(getActivity(), event.getUserAvatarUrl());


        Map<String, String> params = new LinkedHashMap<>();
        params.put("provider", String.valueOf(SocialNetworkUtils.asneToVoxProviders.get(
                UserInfoTools.getUserSocialProvider(LoginFragment.this.getActivity()))));
        params.put("social_id", String.valueOf(UserInfoTools.getUserSocialProvider(
                LoginFragment.this.getActivity())));
        params.put("firstName", UserInfoTools.getUserFirstName(LoginFragment.this.getActivity()));
        params.put("lastName", UserInfoTools.getUserLastName(LoginFragment.this.getActivity()));
        params.put("", VoxProviderUrls.SALT);

        String signature = MD5Hasher.getHash(params);
        params.remove("");

        params.put("signature", signature);

            VolleyNetworkProvider.getInstance(LoginFragment.this.getActivity()).addToRequestQueue(
                    new TransferSocialDataRequest(getActivity(), params,
                            (MainActivity)LoginFragment.this.getActivity()));
    }

    //need for social login
    public void onEvent(EditUserDataWrapper editUserDataWrapper) {
        UserInfoTools.saveUserId(getActivity(), editUserDataWrapper.getUserData().getId());
        UserInfoTools.saveUserLoggedInSocially(getActivity(), true);
        ((MainActivity)getActivity()).getSupportFragmentManager().popBackStack();
        ((MainActivity)getActivity()).handleFragmentSwitching(UserProfileFragment.FRAGMENT_ID,
                null);
    }

    public void onEvent(UserDataWrapper data) {
        UserInfoTools.saveUserFirstName(getActivity(), data.getUserData().getFirstName());
        UserInfoTools.saveUserLastName(getActivity(), data.getUserData().getLastName());
        UserInfoTools.saveUserAvatarUrl(getActivity(), data.getUserData().getAvatar());
        UserInfoTools.saveUserLogin(getActivity(), loginInput.getText().toString());
        UserInfoTools.saveUserEmail(getActivity(),loginInput.getText().toString());
        UserInfoTools.saveUserPassword(getActivity(), passwordInput.getText().toString());
        UserInfoTools.saveUserId(getActivity(), data.getUserData().getId());
        UserInfoTools.saveUserNick(getActivity(), data.getUserData().getNick());

        ((MainActivity)getActivity()).handleFragmentSwitching(UserProfileFragment.FRAGMENT_ID,
                null);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.left_drawer_item_touch) {
                getActivity().onBackPressed();
            } else if(v.getId() == R.id.right_drawer_item) {
                doLogin();
            } else if(v.getId() == R.id.login_create_account_btn) {
                ((MainActivity)getActivity()).handleFragmentSwitching(
                        RegistrationFragment.FRAGMENT_ID, null);
            } else if(v.getId() == R.id.login_forgot_password) {
                ((MainActivity)getActivity()).handleFragmentSwitching(
                        ForgotPasswordFragment.FRAGMENT_ID, null);
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

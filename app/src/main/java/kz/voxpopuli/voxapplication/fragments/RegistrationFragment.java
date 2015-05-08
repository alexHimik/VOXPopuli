package kz.voxpopuli.voxapplication.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.events.CategorySelectedEvent;
import kz.voxpopuli.voxapplication.events.PersonInformationEvent;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.SignUpUserRequest;
import kz.voxpopuli.voxapplication.network.request.TransferSocialDataRequest;
import kz.voxpopuli.voxapplication.network.util.VoxProviderUrls;
import kz.voxpopuli.voxapplication.network.wrappers.EditUserDataSocialWrapper;
import kz.voxpopuli.voxapplication.network.wrappers.udata.EditUserDataWrapper;
import kz.voxpopuli.voxapplication.network.wrappers.udata.UserData;
import kz.voxpopuli.voxapplication.tools.DialogTools;
import kz.voxpopuli.voxapplication.tools.MD5Hasher;
import kz.voxpopuli.voxapplication.tools.SocialNetworkUtils;
import kz.voxpopuli.voxapplication.tools.TextInputsValidators;
import kz.voxpopuli.voxapplication.tools.UserInfoTools;

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

    private boolean showPassword = true;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.registration_fragment_layout, container, false);
        initViews(parent);
        View customBar = getActionBarCustomView(inflater);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        return parent;
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
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
        ((RobotoTextView)centerBatItem).setText(getString(R.string.registration_screen_header_text));
    }

    private void initViews(View parent) {
        emailInput = (RobotoEditText)parent.findViewById(R.id.registration_email_input);
        nameInput = (RobotoEditText)parent.findViewById(R.id.registration_name_input);
        surnameInput = (RobotoEditText)parent.findViewById(R.id.registration_surname_input);
        passwirdInput = (RobotoEditText)parent.findViewById(R.id.registration_password_input);
        passwirdInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
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

    private void doUserRegistration() {
        if(TextInputsValidators.isInputEmpty(emailInput) ||
                TextInputsValidators.isInputEmpty(passwirdInput)) {
            DialogTools.showInfoDialog(getActivity(), getString(R.string.error_dialog_title),
                    getString(R.string.empty_field_alarm));
            return;
        }
        if(!TextInputsValidators.isInputEmail(emailInput)) {
            DialogTools.showInfoDialog(getActivity(), getString(R.string.error_dialog_title),
                    getString(R.string.wrong_email_format_alarm));
            return;
        }
        if(!TextInputsValidators.isInputLengthEnought(passwirdInput, 5)) {
            DialogTools.showInfoDialog(getActivity(), getString(R.string.error_dialog_title),
                    getString(R.string.wrong_password_length));
            return;
        }
        if(TextInputsValidators.isInputEmpty(emailInput) ||
                TextInputsValidators.isInputEmpty(nameInput) ||
                TextInputsValidators.isInputEmpty(surnameInput) ||
                TextInputsValidators.isInputEmpty(passwirdInput)) {
            DialogTools.showInfoDialog(getActivity(), getString(R.string.error_dialog_title),
                    getString(R.string.empty_field_alarm));
            return;
        }
            Map<String, String> params = new LinkedHashMap<>();
            params.put("email", emailInput.getText().toString());
            params.put("password", passwirdInput.getText().toString());
            params.put("firstName", nameInput.getText().toString());
            params.put("lastName", surnameInput.getText().toString());
            params.put("", VoxProviderUrls.SALT);

            String signature = MD5Hasher.getHash(params);
            params.remove("");
            params.put("signature", signature);

            VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(
                    new SignUpUserRequest(getActivity(), params, (MainActivity) getActivity()));
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
                UserInfoTools.getUserSocialProvider(RegistrationFragment.this.getActivity()))));
        params.put("social_id", String.valueOf(UserInfoTools.getUserSocialProvider(
                RegistrationFragment.this.getActivity())));
        params.put("firstName", UserInfoTools.getUserFirstName(RegistrationFragment.this.getActivity()));
        params.put("lastName", UserInfoTools.getUserLastName(RegistrationFragment.this.getActivity()));
        params.put("avatar_url", event.getUserAvatarUrl());
        params.put("", VoxProviderUrls.SALT);

        String signature = MD5Hasher.getHash(params);
        params.remove("");

        params.put("signature", signature);

        VolleyNetworkProvider.getInstance(RegistrationFragment.this.getActivity()).addToRequestQueue(
                new TransferSocialDataRequest(getActivity(), params,
                        (MainActivity)RegistrationFragment.this.getActivity()));
    }

    //need for social login
    public void onEvent(EditUserDataSocialWrapper editUserDataWrapper) {
        UserInfoTools.saveUserId(getActivity(), Integer.toString(editUserDataWrapper.getUserData().getId()));
        UserInfoTools.saveUserLoggedInSocially(getActivity(), true);
        ((MainActivity)getActivity()).getSupportFragmentManager().popBackStack();
        ((MainActivity)getActivity()).handleFragmentSwitching(UserProfileFragment.FRAGMENT_ID,
                null);
    }

    public void onEvent(EditUserDataWrapper data) {
        if(data != null) {
            UserInfoTools.saveUserEmail(getActivity(), emailInput.getText().toString());
            UserInfoTools.saveUserId(getActivity(), Integer.toString(data.getUserData().getId()));
            UserInfoTools.saveUserFirstName(getActivity(), data.getUserData().getFirstName());
            UserInfoTools.saveUserLastName(getActivity(), data.getUserData().getLastName());
            UserInfoTools.saveUserPassword(getActivity(), passwirdInput.getText().toString());
            UserInfoTools.saveUserLogin(getActivity(), emailInput.getText().toString());

            CategorySelectedEvent cse = new CategorySelectedEvent();
            cse.setCategoryId(R.id.left_bar_category_all);
            cse.setCategoryName(getString(R.string.category_name_all));
            EventBus.getDefault().post(cse);
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
                doUserRegistration();
            } else if(v.getId() == R.id.login_facebook) {
                CategoryFragment.socialNetworkUtils.requestUserLogin(FacebookSocialNetwork.ID);
            } else if(v.getId() == R.id.login_twitter) {
                CategoryFragment.socialNetworkUtils.requestUserLogin(TwitterSocialNetwork.ID);
            } else if(v.getId() == R.id.login_google) {
                CategoryFragment.socialNetworkUtils.requestUserLogin(GooglePlusSocialNetwork.ID);
            } else if(v.getId() == R.id.login_vk) {
                CategoryFragment.socialNetworkUtils.requestUserLogin(VkSocialNetwork.ID);
            } else if(v.getId() == R.id.login_password_hider) {
                togglePasswordVisibility();
            }
        }
    };
}

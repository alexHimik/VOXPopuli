package kz.voxpopuli.voxapplication.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.devspark.robototextview.widget.RobotoTextView;

import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.tools.UserInfoTools;

/**
 * Created by user on 21.04.15.
 */
public class SettingsFragment extends BaseFragment {

    public static final String TAG = "SettingsFragment";
    public static final int FRAGMENT_ID = 108;

    private RelativeLayout enterPlace;
    private RelativeLayout registerPlace;
    private RelativeLayout versionPlace;
    private RelativeLayout aboutPlace;
    private RelativeLayout rulesPlace;
    private RelativeLayout vacancyPlace;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.settings_fragment_layout, container, false);
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
        rightBarItem.setVisibility(View.INVISIBLE);
        ((RobotoTextView)centerBatItem).setText(getString(R.string.settings_string));
    }

    private void initViews(View parent) {
        enterPlace = (RelativeLayout)parent.findViewById(R.id.settings_enter_place);
        registerPlace = (RelativeLayout)parent.findViewById(R.id.settings_register_place);
        versionPlace = (RelativeLayout)parent.findViewById(R.id.settings_version_place);
        aboutPlace = (RelativeLayout)parent.findViewById(R.id.settings_about_place);
        rulesPlace = (RelativeLayout)parent.findViewById(R.id.settings_rules_place);
        vacancyPlace = (RelativeLayout)parent.findViewById(R.id.settings_vacancy_place);

        enterPlace.setOnClickListener(clickListener);
        registerPlace.setOnClickListener(clickListener);
        versionPlace.setOnClickListener(clickListener);
        aboutPlace.setOnClickListener(clickListener);
        rulesPlace.setOnClickListener(clickListener);
        vacancyPlace.setOnClickListener(clickListener);

        if(UserInfoTools.isUserLoggedIn(getActivity())) {
            ((RobotoTextView)enterPlace.getChildAt(0)).setText(
                    getString(R.string.settings_profile_edit_profile));
            RobotoTextView text = ((RobotoTextView)registerPlace.getChildAt(0));
            text.setTextColor(getResources().getColor(R.color.vox_red));
            text.setText(getString(R.string.settings_profile_logout));
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
                getActivity().onBackPressed();
            } else if(v.getId() == R.id.settings_enter_place) {
                if(UserInfoTools.isUserLoggedIn(getActivity())) {
                    ((MainActivity)getActivity()).handleFragmentSwitching(
                            EditUserProfileFragment.FRAGMENT_ID, null);
                } else {
                    ((MainActivity)getActivity()).handleFragmentSwitching(LoginFragment.FRAGMENT_ID,
                            null);
                }
            } else if(v.getId() == R.id.settings_register_place) {
                if(UserInfoTools.isUserLoggedIn(getActivity())) {
                    clearUserData();
                    ((ActionBarActivity)getActivity()).getSupportFragmentManager().popBackStack();
                    ((MainActivity)getActivity()).handleFragmentSwitching(
                            SettingsFragment.FRAGMENT_ID, null);
                } else {
                    ((MainActivity)getActivity()).handleFragmentSwitching(
                            RegistrationFragment.FRAGMENT_ID, null);
                }
            } else if(v.getId() == R.id.settings_version_place) {
                ((MainActivity)getActivity()).handleFragmentSwitching(
                        VersionFragment.FRAGMENT_ID, null);
            } else if(v.getId() == R.id.settings_about_place) {
                ((MainActivity)getActivity()).handleFragmentSwitching(
                        About.FRAGMENT_ID, null);
            } else if(v.getId() == R.id.settings_rules_place) {
                ((MainActivity)getActivity()).handleFragmentSwitching(
                        RulesFragment.FRAGMENT_ID, null);
            } else if(v.getId() == R.id.settings_vacancy_place) {
                ((MainActivity)getActivity()).handleFragmentSwitching(
                        VacanciesFragment.FRAGMENT_ID, null);
            }
        }
    };

    private void clearUserData() {
        UserInfoTools.clearUserData(getActivity());
    }
}

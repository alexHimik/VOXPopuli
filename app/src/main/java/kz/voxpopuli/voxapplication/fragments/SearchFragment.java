package kz.voxpopuli.voxapplication.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;

/**
 * Created by user on 15.04.15.
 */
public class SearchFragment extends BaseFragment {

    public static final String TAG = "SearchFragment";
    public static final int FRAGMENT_ID = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public View getActionBarCustomView(LayoutInflater inflater) {
        View customBar = inflater.inflate(R.layout.search_action_bar, null);
        leftbarItem = customBar.findViewById(R.id.left_drawer_item);
        centerBatItem = customBar.findViewById(R.id.search_input);
        rightBarItem = customBar.findViewById(R.id.right_drawer_item);
        initActionBarItems();
        ((ActionBarActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.vox_white)));
        ((ActionBarActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        return customBar;
    }

    @Override
    public void initActionBarItems() {
        leftbarItem.setOnClickListener(backListener);
        ((EditText)centerBatItem).addTextChangedListener(searchInputListener);
        rightBarItem.setOnClickListener(backListener);
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }

    private void handleClearButtonVisibility(int inputSize) {
        if(inputSize > 0) {
            rightBarItem.setVisibility(View.VISIBLE);
        } else {
            rightBarItem.setVisibility(View.INVISIBLE);
        }
    }

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.right_drawer_item) {
                ((EditText)centerBatItem).setText("");
            } else if(v.getId() == R.id.left_drawer_item) {
                ((MainActivity)getActivity()).onBackPressed();
            }
        }
    };

    private TextWatcher searchInputListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            handleClearButtonVisibility(s.length());
        }
    };
}

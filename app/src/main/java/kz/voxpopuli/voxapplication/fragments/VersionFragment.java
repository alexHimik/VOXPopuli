package kz.voxpopuli.voxapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.devspark.robototextview.widget.RobotoTextView;

import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;

public class VersionFragment extends BaseFragment {
    public static final String TAG = "About";
    public static final int FRAGMENT_ID = 120;
    private View parent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View customBar = super.getActionBarCustomView(inflater);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(customBar);

        parent = inflater.inflate(R.layout.version, container, false);

        return parent;
    }


    @Override
    public void initActionBarItems() {
        rightBarItem.setVisibility(View.INVISIBLE);
        leftbarItem.setOnClickListener(barClickListener);
        ((RobotoTextView)centerBatItem).setText(getString(R.string.version_title));
        leftbarItem.setBackgroundResource(R.drawable.vox_ic_white_arrow);
    }

    private View.OnClickListener barClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).onBackPressed();
        }
    };

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }
}

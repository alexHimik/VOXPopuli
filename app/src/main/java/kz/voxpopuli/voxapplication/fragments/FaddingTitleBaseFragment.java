package kz.voxpopuli.voxapplication.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

import kz.voxpopuli.voxapplication.R;

/**
 * Created by user on 26.03.15.
 */
public abstract class FaddingTitleBaseFragment extends Fragment implements BackStackDataDescriber {

    protected FadingActionBarHelper mFadingHelper;
    protected Bundle mArguments;
    protected ImageView faddingHeader;
    protected ListView fragmentList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mArguments = getArguments();
        mFadingHelper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.ab_background)
                .headerLayout(R.layout.fadding_fragment_header)
                .contentLayout(R.layout.fadding_fragment_content);
        mFadingHelper.initActionBar(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = mFadingHelper.createView(inflater);
        faddingHeader = (ImageView)view.findViewById(R.id.image_header);
        fragmentList = (ListView)view.findViewById(android.R.id.list);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mFadingHelper != null) {
            mFadingHelper.resetActionBarAlfa();
        }
    }
}

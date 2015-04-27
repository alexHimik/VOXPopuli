package kz.voxpopuli.voxapplication.fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import kz.voxpopuli.voxapplication.R;

public class SocialDialogFragment extends DialogFragment implements View.OnClickListener {

    final String LOG_TAG = "myLogs";
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        getDialog().setTitle("Title!");
        getDialog().setTitle(R.string.social_dialog_title);
        View sd = inflater.inflate(R.layout.social_share_layout, null);
        sd.findViewById(R.id.l_facebook).setOnClickListener(this);
        sd.findViewById(R.id.l_tweeter).setOnClickListener(this);
        sd.findViewById(R.id.l_google).setOnClickListener(this);
        sd.findViewById(R.id.l_vkontakt).setOnClickListener(this);
        return sd;
    }

    public void onClick(View v) {
        switch (((LinearLayout) v).getId()) {
            case R.id.l_facebook :
Log.d("ASDF","l_facebook");
                break;
            case R.id.l_tweeter :
Log.d("ASDF","l_tweeter");
                break;
            case R.id.l_google :
Log.d("ASDF","l_google");
                break;
            case R.id.l_vkontakt :
Log.d("ASDF","l_vkontakt");
                break;
        }
        dismiss();
    }

}

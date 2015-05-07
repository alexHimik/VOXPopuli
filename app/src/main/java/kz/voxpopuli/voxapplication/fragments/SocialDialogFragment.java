package kz.voxpopuli.voxapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.gorbin.asne.facebook.FacebookSocialNetwork;
import com.github.gorbin.asne.googleplus.GooglePlusSocialNetwork;
import com.github.gorbin.asne.twitter.TwitterSocialNetwork;
import com.github.gorbin.asne.vk.VkSocialNetwork;

import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.tools.SocialNetworkUtils;

public class SocialDialogFragment extends DialogFragment implements View.OnClickListener {

    final String TAG = "SocialDialog";
    private SocialNetworkUtils socialNetworkUtils;
    private Bundle data;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        data = getArguments();
        getDialog().setTitle(R.string.social_dialog_title);
        View sd = inflater.inflate(R.layout.social_share_layout, null);
        sd.findViewById(R.id.l_facebook).setOnClickListener(this);
        sd.findViewById(R.id.l_twitter).setOnClickListener(this);
        sd.findViewById(R.id.l_google).setOnClickListener(this);
        sd.findViewById(R.id.l_vkontakt).setOnClickListener(this);

        return sd;
    }

    @Override
    public void onResume() {
        super.onResume();
        socialNetworkUtils = SocialNetworkUtils.getInstance(this);
    }

    public void onClick(View v) {
        String link = data.getString("link");
        String title = data.getString("title");
        String descr = data.getString("descr");

        switch (((LinearLayout) v).getId()) {
            case R.id.l_facebook :
                socialNetworkUtils.postArticleLinkToSocial(FacebookSocialNetwork.ID, link, title, descr);
                break;
            case R.id.l_twitter :
                socialNetworkUtils.postArticleLinkToSocial(TwitterSocialNetwork.ID, link, title, descr);
                break;
            case R.id.l_google :
                socialNetworkUtils.postArticleLinkToSocial(GooglePlusSocialNetwork.ID, link, title, descr);
                break;
            case R.id.l_vkontakt :
                socialNetworkUtils.postArticleLinkToSocial(VkSocialNetwork.ID, link, title, descr);
                break;
        }
        dismiss();
    }
}

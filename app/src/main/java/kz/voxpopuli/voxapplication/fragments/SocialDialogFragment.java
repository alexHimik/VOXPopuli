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
    private String link;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        getDialog().setTitle("Title!");
        getDialog().setTitle(R.string.social_dialog_title);
        View sd = inflater.inflate(R.layout.social_share_layout, null);
        sd.findViewById(R.id.l_facebook).setOnClickListener(this);
        sd.findViewById(R.id.l_twitter).setOnClickListener(this);
        sd.findViewById(R.id.l_google).setOnClickListener(this);
        sd.findViewById(R.id.l_vkontakt).setOnClickListener(this);
        socialNetworkUtils = new SocialNetworkUtils();
        socialNetworkUtils.initSocialManager(this);
        return sd;
    }

    public void setLink(String l){link = l;}

    public void onClick(View v) {
        switch (((LinearLayout) v).getId()) {
            case R.id.l_facebook :
Log.d("ASDF","l_facebook");
                socialNetworkUtils.postArticleLinkToSocial(FacebookSocialNetwork.ID, link, "", "");
//                socialNetworkUtils.requestUserLogin(FacebookSocialNetwork.ID);
                break;
            case R.id.l_twitter :
Log.d("ASDF","l_tweeter");
                socialNetworkUtils.postArticleLinkToSocial(TwitterSocialNetwork.ID, link, "", "");
//                socialNetworkUtils.requestUserLogin(TwitterSocialNetwork.ID);
                break;
            case R.id.l_google :
Log.d("ASDF","l_google");
                socialNetworkUtils.postArticleLinkToSocial(GooglePlusSocialNetwork.ID, link, "", "");
//                socialNetworkUtils.requestUserLogin(GooglePlusSocialNetwork.ID);
                break;
            case R.id.l_vkontakt :
Log.d("ASDF","l_vkontakt");
                socialNetworkUtils.postArticleLinkToSocial(VkSocialNetwork.ID, link, "", "");
//                socialNetworkUtils.requestUserLogin(VkSocialNetwork.ID);
                break;
        }
        dismiss();
    }

}

package kz.voxpopuli.voxapplication.tools;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.core.listener.OnLoginCompleteListener;
import com.github.gorbin.asne.core.listener.OnPostingCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestDetailedSocialPersonCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestSocialPersonCompleteListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.github.gorbin.asne.facebook.FacebookSocialNetwork;
import com.github.gorbin.asne.googleplus.GooglePlusSocialNetwork;
import com.github.gorbin.asne.twitter.TwitterSocialNetwork;
import com.github.gorbin.asne.vk.VkSocialNetwork;
import com.vk.sdk.VKScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.events.ErrorEvent;
import kz.voxpopuli.voxapplication.events.PersonInformationEvent;
import kz.voxpopuli.voxapplication.events.SocialNetworkNotConnected;
import kz.voxpopuli.voxapplication.events.SuccessPostToSocialEvent;

/**
 * Created by user on 06.04.15.
 */
public class SocialNetworkUtils implements SocialNetworkManager.OnInitializationCompleteListener,
        OnLoginCompleteListener, OnRequestSocialPersonCompleteListener, OnRequestDetailedSocialPersonCompleteListener {

    public static final String SOCIAL_NETWORK_TAG = "SocialIntegrationMain.SOCIAL_NETWORK_TAG";

    public static final int VK_SOCIAL_NETWORK = 1;
    public static final int FB_SOCIAL_NETWORK = 2;
    public static final int GP_SOCIAL_NETWORK = 3;
    public static final int TW_SOCIAL_NETWORK = 4;

    public static Map<Integer, Integer> asneToVoxProviders = new HashMap<>();

    static {
        asneToVoxProviders.put(VkSocialNetwork.ID, VK_SOCIAL_NETWORK);
        asneToVoxProviders.put(FacebookSocialNetwork.ID, FB_SOCIAL_NETWORK);
        asneToVoxProviders.put(GooglePlusSocialNetwork.ID, GP_SOCIAL_NETWORK);
        asneToVoxProviders.put(TwitterSocialNetwork.ID, TW_SOCIAL_NETWORK);
    }

    private SocialNetworkManager socialNetworkManager;
    private static SocialNetworkUtils instance;

    private SocialNetworkUtils(Fragment fragment) {
        initSocialManager(fragment);
    }

    public static SocialNetworkUtils getInstance(Fragment fragment) {
        if(instance == null) {
            instance = new SocialNetworkUtils(fragment);
        }
        return instance;
    }

    public boolean isSocialManagerInitialized() {
        return socialNetworkManager != null;
    }

    public void requestUserLogin(int socialNetworkId) {
        if(socialNetworkManager != null) {
            SocialNetwork network = socialNetworkManager.getSocialNetwork(socialNetworkId);
            if(network != null && !network.isConnected()) {
                network.requestLogin();
            } else {
                network.requestCurrentPerson();
            }
        }
    }

    public void postArticleLinkToSocial(int socialNetworkId, String link, String title, String message) {
        if(socialNetworkManager != null) {
            SocialNetwork network = socialNetworkManager.getSocialNetwork(socialNetworkId);
            Bundle postParams = new Bundle();
            if(network != null && network.isConnected()) {
                postParams.putString(SocialNetwork.BUNDLE_LINK, link);
                postParams.putString(SocialNetwork.BUNDLE_NAME, title);
                if(socialNetworkId == GooglePlusSocialNetwork.ID) {
                    network.requestPostDialog(postParams, postingComplete);
                } else {
                    network.requestPostLink(postParams, message, postingComplete);
                }
            } else {
                SocialNetworkNotConnected msg = new SocialNetworkNotConnected();
                msg.setNetworkId(socialNetworkId);
                EventBus.getDefault().post(msg);
            }
        }
    }

    @Override
    public void onSocialNetworkManagerInitialized() {
        for (SocialNetwork socialNetwork : socialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);
            socialNetwork.setOnRequestCurrentPersonCompleteListener(this);
            socialNetwork.setOnRequestDetailedSocialPersonCompleteListener(this);
            socialNetwork.setOnPostingLinkCompleteListener(postingComplete);
        }
    }

    @Override
    public void onError(int i, String s, String s2, Object o) {
        EventBus.getDefault().post(new ErrorEvent(s, i));
    }

    @Override
    public void onLoginSuccess(final int i) {
        SocialNetwork network = socialNetworkManager.getSocialNetwork(i);
        if(network != null && network.isConnected()) {
            network.requestCurrentPerson();
        }
    }

    @Override
    public void onRequestSocialPersonSuccess(int i, SocialPerson socialPerson) {
        postSocialPersonInfoEvent(socialPerson, i);
    }

    @Override
    public void onRequestDetailedSocialPersonSuccess(int i, SocialPerson socialPerson) {
        postSocialPersonInfoEvent(socialPerson, i);
    }

    private void initSocialManager(Fragment fragment) {
        socialNetworkManager = (SocialNetworkManager)fragment.getFragmentManager().
                findFragmentByTag(SOCIAL_NETWORK_TAG);
        if(socialNetworkManager == null) {
            socialNetworkManager = new SocialNetworkManager();

            ArrayList<String> fbScope = new ArrayList<>();
            fbScope.addAll(Arrays.asList("public_profile, email, user_location"));

            String[] vkScope = new String[] {
                    VKScope.NOHTTPS,
                    VKScope.STATUS,
                    VKScope.WALL
            };

            FacebookSocialNetwork facebookSocialNetwork = new FacebookSocialNetwork(fragment, fbScope);
            socialNetworkManager.addSocialNetwork(facebookSocialNetwork);

            VkSocialNetwork vkSocialNetwork = new VkSocialNetwork(fragment,
                    fragment.getString(R.string.vk_app_id), vkScope);
            socialNetworkManager.addSocialNetwork(vkSocialNetwork);

            GooglePlusSocialNetwork googlePlusSocialNetwork = new GooglePlusSocialNetwork(fragment);
            socialNetworkManager.addSocialNetwork(googlePlusSocialNetwork);

            TwitterSocialNetwork twitterSocialNetwork = new TwitterSocialNetwork(fragment,
                    fragment.getString(R.string.twitter_consumer_key),
                    fragment.getString(R.string.twitter_consumer_secret),
                    fragment.getString(R.string.vox_url));
            socialNetworkManager.addSocialNetwork(twitterSocialNetwork);

            fragment.getFragmentManager().beginTransaction().add(socialNetworkManager,
                    SOCIAL_NETWORK_TAG).commit();
            socialNetworkManager.setOnInitializationCompleteListener(this);
        } else {
            if(!socialNetworkManager.getInitializedSocialNetworks().isEmpty()) {
                List<SocialNetwork> socialNetworks = socialNetworkManager.getInitializedSocialNetworks();
                for(SocialNetwork socialNetwork : socialNetworks) {
                    socialNetwork.setOnLoginCompleteListener(this);
                    socialNetwork.setOnRequestCurrentPersonCompleteListener(this);
                    socialNetwork.setOnRequestDetailedSocialPersonCompleteListener(this);
                    socialNetwork.setOnPostingLinkCompleteListener(postingComplete);
                }
            }
        }
    }

    private void postSocialPersonInfoEvent(SocialPerson socialPerson, int socialNetworkId) {
        PersonInformationEvent personInformationEvent = new PersonInformationEvent();
        personInformationEvent.setSocialNetworkId(socialNetworkId);
        EventBus.getDefault().post(personInformationEvent.withSocialPerson(socialPerson));
    }

    private OnPostingCompleteListener postingComplete = new OnPostingCompleteListener() {
        @Override
        public void onPostSuccessfully(int socialNetworkID) {
            EventBus.getDefault().post(new SuccessPostToSocialEvent(socialNetworkID));
        }

        @Override
        public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
            EventBus.getDefault().post(new ErrorEvent(errorMessage, socialNetworkID));
        }
    };

    public void disconectConnectedNetworks() {
        for (SocialNetwork socialNetwork : socialNetworkManager.getInitializedSocialNetworks()) {
            if(socialNetwork.isConnected()) {
                socialNetwork.logout();
            }
        }
    }
}

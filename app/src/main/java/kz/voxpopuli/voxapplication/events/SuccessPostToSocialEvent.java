package kz.voxpopuli.voxapplication.events;

/**
 * Created by user on 07.04.15.
 */
public class SuccessPostToSocialEvent {

    private int socialNetworkId;

    public SuccessPostToSocialEvent(int socialNetworkId) {
        this.socialNetworkId = socialNetworkId;
    }

    public int getSocialNetworkId() {
        return socialNetworkId;
    }

    public void setSocialNetworkId(int socialNetworkId) {
        this.socialNetworkId = socialNetworkId;
    }
}

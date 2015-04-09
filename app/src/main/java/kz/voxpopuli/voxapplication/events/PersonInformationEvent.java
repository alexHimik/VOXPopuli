package kz.voxpopuli.voxapplication.events;

import com.github.gorbin.asne.core.persons.SocialPerson;

/**
 * Created by user on 07.04.15.
 */
public class PersonInformationEvent {

    private int socialNetworkId;
    private String userId;
    private String userName;
    private String userAvatarUrl;
    private String userProfileUrl;
    private String userEmail;

    public PersonInformationEvent() {
    }

    public PersonInformationEvent(int socialNetworkId, String userId, String userName, String userAvatarUrl, String userProfileUrl, String userEmail) {
        this.socialNetworkId = socialNetworkId;
        this.userId = userId;
        this.userName = userName;
        this.userAvatarUrl = userAvatarUrl;
        this.userProfileUrl = userProfileUrl;
        this.userEmail = userEmail;
    }

    public int getSocialNetworkId() {
        return socialNetworkId;
    }

    public void setSocialNetworkId(int socialNetworkId) {
        this.socialNetworkId = socialNetworkId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public PersonInformationEvent withSocialPerson(SocialPerson person) {
        this.userId = person.id;
        this.userName = person.name;
        this.userAvatarUrl = person.avatarURL;
        this.userProfileUrl = person.profileURL;
        this.userEmail = person.email;
        return this;
    }
}

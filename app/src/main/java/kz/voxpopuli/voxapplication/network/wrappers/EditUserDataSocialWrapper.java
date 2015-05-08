package kz.voxpopuli.voxapplication.network.wrappers;

import com.google.gson.annotations.Expose;

import kz.voxpopuli.voxapplication.network.wrappers.udata.UserData;

/**
 * Created by user on 08.05.15.
 */
public class EditUserDataSocialWrapper {

    @Expose
    private UserData userData;

    /**
     *
     * @return
     * The userData
     */
    public UserData getUserData() {
        return userData;
    }

    /**
     *
     * @param userData
     * The userData
     */
    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public EditUserDataSocialWrapper withUserData(UserData userData) {
        this.userData = userData;
        return this;
    }
}

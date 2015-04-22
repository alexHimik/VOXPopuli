package kz.voxpopuli.voxapplication.network.wrappers.udata;

/**
 * Created by user on 21.04.15.
 */
import com.google.gson.annotations.Expose;

public class UserDataWrapper {

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

    public UserDataWrapper withUserData(UserData userData) {
        this.userData = userData;
        return this;
    }

}

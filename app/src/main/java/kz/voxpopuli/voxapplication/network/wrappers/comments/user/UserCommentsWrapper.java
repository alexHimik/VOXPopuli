package kz.voxpopuli.voxapplication.network.wrappers.comments.user;

/**
 * Created by user on 23.04.15.
 */
import com.google.gson.annotations.Expose;

public class UserCommentsWrapper {

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

    public UserCommentsWrapper withUserData(UserData userData) {
        this.userData = userData;
        return this;
    }

}

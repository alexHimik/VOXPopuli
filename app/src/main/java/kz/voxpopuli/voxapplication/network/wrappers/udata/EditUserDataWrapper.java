package kz.voxpopuli.voxapplication.network.wrappers.udata;

/**
 * Created by user on 24.04.15.
 */
import com.google.gson.annotations.Expose;

public class EditUserDataWrapper {

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

    public EditUserDataWrapper withUserData(UserData userData) {
        this.userData = userData;
        return this;
    }

}

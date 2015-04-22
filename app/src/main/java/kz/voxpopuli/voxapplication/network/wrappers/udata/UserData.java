package kz.voxpopuli.voxapplication.network.wrappers.udata;

/**
 * Created by user on 21.04.15.
 */
import com.google.gson.annotations.Expose;

public class UserData {

    @Expose
    private String id;
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String nick;
    @Expose
    private String avatar;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public UserData withId(String id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return
     * The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     * The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public UserData withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserData withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     *
     * @return
     * The nick
     */
    public String getNick() {
        return nick;
    }

    /**
     *
     * @param nick
     * The nick
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    public UserData withNick(String nick) {
        this.nick = nick;
        return this;
    }

    /**
     *
     * @return
     * The avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     *
     * @param avatar
     * The avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserData withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

}
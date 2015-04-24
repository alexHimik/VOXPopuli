package kz.voxpopuli.voxapplication.network.wrappers.article;

/**
 * Created by user on 23.04.15.
 */
import com.google.gson.annotations.Expose;

public class Author {

    @Expose
    private String title;
    @Expose
    private String position;
    @Expose
    private String avatar;

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public Author withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     *
     * @return
     * The position
     */
    public String getPosition() {
        return position;
    }

    /**
     *
     * @param position
     * The position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    public Author withPosition(String position) {
        this.position = position;
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

    public Author withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

}

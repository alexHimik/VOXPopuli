package kz.voxpopuli.voxapplication.network.wrappers.article;

/**
 * Created by user on 23.04.15.
 */
import com.google.gson.annotations.Expose;

public class Similar {

    @Expose
    private String id;
    @Expose
    private String title;
    @Expose
    private String image;
    @Expose
    private String postDate;
    @Expose
    private String viwed;
    @Expose
    private String commentsAmount;

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

    public Similar withId(String id) {
        this.id = id;
        return this;
    }

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

    public Similar withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    public Similar withImage(String image) {
        this.image = image;
        return this;
    }

    /**
     *
     * @return
     * The postDate
     */
    public String getPostDate() {
        return postDate;
    }

    /**
     *
     * @param postDate
     * The postDate
     */
    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public Similar withPostDate(String postDate) {
        this.postDate = postDate;
        return this;
    }

    /**
     *
     * @return
     * The viwed
     */
    public String getViwed() {
        return viwed;
    }

    /**
     *
     * @param viwed
     * The viwed
     */
    public void setViwed(String viwed) {
        this.viwed = viwed;
    }

    public Similar withViwed(String viwed) {
        this.viwed = viwed;
        return this;
    }

    /**
     *
     * @return
     * The commentsAmount
     */
    public String getCommentsAmount() {
        return commentsAmount;
    }

    /**
     *
     * @param commentsAmount
     * The commentsAmount
     */
    public void setCommentsAmount(String commentsAmount) {
        this.commentsAmount = commentsAmount;
    }

    public Similar withCommentsAmount(String commentsAmount) {
        this.commentsAmount = commentsAmount;
        return this;
    }

}
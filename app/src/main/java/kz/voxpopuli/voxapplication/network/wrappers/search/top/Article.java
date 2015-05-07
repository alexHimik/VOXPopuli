package kz.voxpopuli.voxapplication.network.wrappers.search.top;

/**
 * Created by user on 27.04.15.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Article {

    @Expose
    private String id;
    @Expose
    private String title;
    @Expose
    private String image;
    @SerializedName("image_mid")
    @Expose
    private String imageMid;
    @Expose
    private String description;
    @Expose
    private String postDate;
    @Expose
    private String viwed;
    @Expose
    private String commentsAmount;
    @Expose
    private String shares;

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

    public Article withId(String id) {
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

    public Article withTitle(String title) {
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

    public Article withImage(String image) {
        this.image = image;
        return this;
    }

    /**
     *
     * @return
     * The imageMid
     */
    public String getImageMid() {
        return imageMid;
    }

    /**
     *
     * @param imageMid
     * The image_mid
     */
    public void setImageMid(String imageMid) {
        this.imageMid = imageMid;
    }

    public Article withImageMid(String imageMid) {
        this.imageMid = imageMid;
        return this;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Article withDescription(String description) {
        this.description = description;
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

    public Article withPostDate(String postDate) {
        this.postDate = postDate;
        return this;
    }

    /**
     *
     * @return
     * The shares
     */
    public String getShares() {
        return shares;
    }

    /**
     *
     * @param shares
     * The shares
     */
    public void setShares(String shares) {
        this.shares = shares;
    }

    public Article withShares(String shares) {
        this.shares = shares;
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

    public Article withViwed(String viwed) {
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

    public Article withCommentsAmount(String commentsAmount) {
        this.commentsAmount = commentsAmount;
        return this;
    }

}
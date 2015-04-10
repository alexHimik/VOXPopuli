package kz.voxpopuli.voxapplication.network.wrappers.mpage;

/**
 * Created by user on 10.04.15.
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
    @SerializedName("image_big")
    @Expose
    private String imageBig;
    @Expose
    private String description;
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

    /**
     *
     * @return
     * The imageBig
     */
    public String getImageBig() {
        return imageBig;
    }

    /**
     *
     * @param imageBig
     * The image_big
     */
    public void setImageBig(String imageBig) {
        this.imageBig = imageBig;
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

}

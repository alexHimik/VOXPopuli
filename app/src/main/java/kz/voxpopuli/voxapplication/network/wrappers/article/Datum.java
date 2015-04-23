package kz.voxpopuli.voxapplication.network.wrappers.article;

/**
 * Created by user on 23.04.15.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @Expose
    private String title;
    @Expose
    private String image;
    @SerializedName("image_mid")
    @Expose
    private String imageMid;

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

    public Datum withTitle(String title) {
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

    public Datum withImage(String image) {
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

    public Datum withImageMid(String imageMid) {
        this.imageMid = imageMid;
        return this;
    }

}

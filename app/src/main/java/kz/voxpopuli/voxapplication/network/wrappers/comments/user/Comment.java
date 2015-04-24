package kz.voxpopuli.voxapplication.network.wrappers.comments.user;

/**
 * Created by user on 23.04.15.
 */
import com.google.gson.annotations.Expose;

public class Comment {

    @Expose
    private String id;
    @Expose
    private String title;
    @Expose
    private String commentDate;
    @Expose
    private String comment;
    @Expose
    private String rateplus;
    @Expose
    private String rateminus;

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

    public Comment withId(String id) {
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

    public Comment withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     *
     * @return
     * The commentDate
     */
    public String getCommentDate() {
        return commentDate;
    }

    /**
     *
     * @param commentDate
     * The commentDate
     */
    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public Comment withCommentDate(String commentDate) {
        this.commentDate = commentDate;
        return this;
    }

    /**
     *
     * @return
     * The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     *
     * @param comment
     * The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Comment withComment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     *
     * @return
     * The rateplus
     */
    public String getRateplus() {
        return rateplus;
    }

    /**
     *
     * @param rateplus
     * The rateplus
     */
    public void setRateplus(String rateplus) {
        this.rateplus = rateplus;
    }

    public Comment withRateplus(String rateplus) {
        this.rateplus = rateplus;
        return this;
    }

    /**
     *
     * @return
     * The rateminus
     */
    public String getRateminus() {
        return rateminus;
    }

    /**
     *
     * @param rateminus
     * The rateminus
     */
    public void setRateminus(String rateminus) {
        this.rateminus = rateminus;
    }

    public Comment withRateminus(String rateminus) {
        this.rateminus = rateminus;
        return this;
    }

}

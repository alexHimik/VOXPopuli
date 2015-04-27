package kz.voxpopuli.voxapplication.network.wrappers.comments.article;

/**
 * Created by user on 27.04.15.
 */
import com.google.gson.annotations.Expose;

public class ArcticleComment {

    @Expose
    private String id;
    @Expose
    private String authorId;
    @Expose
    private String authorName;
    @Expose
    private String authorAvatar;
    @Expose
    private String commentDate;
    @Expose
    private String comment;
    @Expose
    private int level;
    @Expose
    private String parent;
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

    public ArcticleComment withId(String id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return
     * The authorId
     */
    public String getAuthorId() {
        return authorId;
    }

    /**
     *
     * @param authorId
     * The authorId
     */
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public ArcticleComment withAuthorId(String authorId) {
        this.authorId = authorId;
        return this;
    }

    /**
     *
     * @return
     * The authorName
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     *
     * @param authorName
     * The authorName
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public ArcticleComment withAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    /**
     *
     * @return
     * The authorAvatar
     */
    public String getAuthorAvatar() {
        return authorAvatar;
    }

    /**
     *
     * @param authorAvatar
     * The authorAvatar
     */
    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public ArcticleComment withAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
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

    public ArcticleComment withCommentDate(String commentDate) {
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

    public ArcticleComment withComment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     *
     * @return
     * The level
     */
    public int getLevel() {
        return level;
    }

    /**
     *
     * @param level
     * The level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    public ArcticleComment withLevel(int level) {
        this.level = level;
        return this;
    }

    /**
     *
     * @return
     * The parent
     */
    public String getParent() {
        return parent;
    }

    /**
     *
     * @param parent
     * The parent
     */
    public void setParent(String parent) {
        this.parent = parent;
    }

    public ArcticleComment withParent(String parent) {
        this.parent = parent;
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

    public ArcticleComment withRateplus(String rateplus) {
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

    public ArcticleComment withRateminus(String rateminus) {
        this.rateminus = rateminus;
        return this;
    }

}
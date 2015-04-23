package kz.voxpopuli.voxapplication.network.wrappers.article;

/**
 * Created by user on 23.04.15.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Article {

    @Expose
    private String id;
    @Expose
    private String title;
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
    private List<Content> content = new ArrayList<Content>();
    @Expose
    private List<Associatedrubric> associatedrubrics = new ArrayList<Associatedrubric>();
    @Expose
    private List<Tag> tags = new ArrayList<Tag>();
    @Expose
    private List<Author> authors = new ArrayList<Author>();
    @Expose
    private List<Similar> similar = new ArrayList<Similar>();

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

    /**
     *
     * @return
     * The content
     */
    public List<Content> getContent() {
        return content;
    }

    /**
     *
     * @param content
     * The content
     */
    public void setContent(List<Content> content) {
        this.content = content;
    }

    public Article withContent(List<Content> content) {
        this.content = content;
        return this;
    }

    /**
     *
     * @return
     * The associatedrubrics
     */
    public List<Associatedrubric> getAssociatedrubrics() {
        return associatedrubrics;
    }

    /**
     *
     * @param associatedrubrics
     * The associatedrubrics
     */
    public void setAssociatedrubrics(List<Associatedrubric> associatedrubrics) {
        this.associatedrubrics = associatedrubrics;
    }

    public Article withAssociatedrubrics(List<Associatedrubric> associatedrubrics) {
        this.associatedrubrics = associatedrubrics;
        return this;
    }

    /**
     *
     * @return
     * The tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     *
     * @param tags
     * The tags
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Article withTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    /**
     *
     * @return
     * The authors
     */
    public List<Author> getAuthors() {
        return authors;
    }

    /**
     *
     * @param authors
     * The authors
     */
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Article withAuthors(List<Author> authors) {
        this.authors = authors;
        return this;
    }

    /**
     *
     * @return
     * The similar
     */
    public List<Similar> getSimilar() {
        return similar;
    }

    /**
     *
     * @param similar
     * The similar
     */
    public void setSimilar(List<Similar> similar) {
        this.similar = similar;
    }

    public Article withSimilar(List<Similar> similar) {
        this.similar = similar;
        return this;
    }

}

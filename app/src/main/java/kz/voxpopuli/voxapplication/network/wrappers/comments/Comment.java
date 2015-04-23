package kz.voxpopuli.voxapplication.network.wrappers.comments;


import com.google.gson.annotations.Expose;

public class Comment {
    @Expose
    private String id;
    @Expose
    private String author;
    @Expose
    private String avatar;
    @Expose
    private String txt;
    @Expose
    private String datetime;

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
     * The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     *
     * @param author
     * The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    public Comment withAuthor(String author) {
        this.author = author;
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

    public Comment withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    /**
     *
     * @return
     * The txt
     */
    public String getTxt() {
        return txt;
    }

    /**
     *
     * @param txt
     * The txt
     */
    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Comment withTxt(String txt) {
        this.txt = txt;
        return this;
    }

    /**
     *
     * @return
     * The datetime
     */
    public String getDatetime() {
        return datetime;
    }

    /**
     *
     * @param datetime
     * The datetime
     */
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

}

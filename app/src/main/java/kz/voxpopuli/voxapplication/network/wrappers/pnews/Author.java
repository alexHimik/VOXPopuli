package kz.voxpopuli.voxapplication.network.wrappers.pnews;

import com.google.gson.annotations.Expose;

public class Author {

    @Expose
    private String name;
    @Expose
    private String avatar;
    @Expose
    private String post;

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Author withName(String name) {
        this.name = name;
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

    /**
     *
     * @return
     * The post
     */
    public String getPost() {
        return post;
    }

    /**
     *
     * @param post
     * The post
     */
    public void setPost(String post) {
        this.post = post;
    }

    public Author withPost(String post) {
        this.post = post;
        return this;
    }

}
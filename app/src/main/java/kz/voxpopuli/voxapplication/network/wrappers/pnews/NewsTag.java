package kz.voxpopuli.voxapplication.network.wrappers.pnews;

import com.google.gson.annotations.Expose;

public class NewsTag {
    @Expose
    private String id;
    @Expose
    private String title;

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

    public NewsTag withId(String id) {
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

    public NewsTag withTitle(String title) {
        this.title = title;
        return this;
    }
}

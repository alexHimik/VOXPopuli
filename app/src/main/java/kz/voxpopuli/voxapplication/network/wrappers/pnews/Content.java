package kz.voxpopuli.voxapplication.network.wrappers.pnews;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Content {

    @Expose
    private String type;
    @Expose
    private List<String> data;
    @Expose
    private String title;
    @Expose
    private String author;

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public Content withType(String type) {
        this.type = type;
        return this;
    }

    /**
     *
     * @return
     * The data
     */
    public List<String> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<String> data) {
        this.data = data;
    }

    public Content withData(List<String> data) {
        this.data = data;
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

    public Content withTitle(String title) {
        this.title = title;
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

    public Content withAuthor(String author) {
        this.author = author;
        return this;
    }

}
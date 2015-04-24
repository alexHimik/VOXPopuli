package kz.voxpopuli.voxapplication.network.wrappers.article;

/**
 * Created by user on 23.04.15.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Content {

    @Expose
    private String type;
    @Expose
    private List<String> data = new ArrayList<String>();
    @Expose
    private String url;
    @Expose
    private String title;
    @Expose
    private String author;
    @Expose
    private String code;

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
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public Content withUrl(String url) {
        this.url = url;
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

    /**
     *
     * @return
     * The code
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    public Content withCode(String code) {
        this.code = code;
        return this;
    }

}

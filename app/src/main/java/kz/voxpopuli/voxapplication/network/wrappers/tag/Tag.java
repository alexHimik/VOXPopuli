package kz.voxpopuli.voxapplication.network.wrappers.tag;

/**
 * Created by user on 27.04.15.
 */
import com.google.gson.annotations.Expose;

public class Tag {

    @Expose
    private int id;
    @Expose
    private String title;

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    public Tag withId(int id) {
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

    public Tag withTitle(String title) {
        this.title = title;
        return this;
    }

}

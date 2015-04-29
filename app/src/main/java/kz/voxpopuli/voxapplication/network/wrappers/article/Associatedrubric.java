package kz.voxpopuli.voxapplication.network.wrappers.article;

/**
 * Created by user on 23.04.15.
 */
import com.google.gson.annotations.Expose;

public class Associatedrubric {

    @Expose
    private String id;
    @Expose
    private String name;

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

    public Associatedrubric withId(String id) {
        this.id = id;
        return this;
    }

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

    public Associatedrubric withName(String name) {
        this.name = name;
        return this;
    }

}
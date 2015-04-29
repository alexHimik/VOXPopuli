package kz.voxpopuli.voxapplication.network.wrappers.info;

/**
 * Created by user on 29.04.15.
 */
import com.google.gson.annotations.Expose;

public class Content {

    @Expose
    private String type;
    @Expose
    private String data;

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
    public String getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(String data) {
        this.data = data;
    }

    public Content withData(String data) {
        this.data = data;
        return this;
    }

}

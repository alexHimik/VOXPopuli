package kz.voxpopuli.voxapplication.network.wrappers.rubrics;

/**
 * Created by user on 10.04.15.
 */
import com.google.gson.annotations.Expose;

public class RubricsInfo {

    @Expose
    private String id;
    @Expose
    private String title;
    @Expose
    private String image;
    @Expose
    private int main;

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

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The main
     */
    public int getMain() {
        return main;
    }

    /**
     *
     * @param main
     * The main
     */
    public void setMain(int main) {
        this.main = main;
    }

}
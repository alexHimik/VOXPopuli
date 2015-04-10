package kz.voxpopuli.voxapplication.network.wrappers.mpage;

/**
 * Created by user on 10.04.15.
 */
import com.google.gson.annotations.Expose;

public class MainPageDataWrapper {

    @Expose
    private Main main;

    /**
     *
     * @return
     * The main
     */
    public Main getMain() {
        return main;
    }

    /**
     *
     * @param main
     * The main
     */
    public void setMain(Main main) {
        this.main = main;
    }

}

package kz.voxpopuli.voxapplication.network.wrappers.search.top;

/**
 * Created by user on 16.04.15.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

import kz.voxpopuli.voxapplication.network.wrappers.mpage.Article;

public class Group {

    @Expose
    private String name;
    @Expose
    private List<Article> acrticles = new ArrayList<Article>();

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

    public Group withName(String name) {
        this.name = name;
        return this;
    }

    /**
     *
     * @return
     * The acrticles
     */
    public List<Article> getAcrticles() {
        return acrticles;
    }

    /**
     *
     * @param acrticles
     * The acrticles
     */
    public void setAcrticles(List<Article> acrticles) {
        this.acrticles = acrticles;
    }

    public Group withAcrticles(List<Article> acrticles) {
        this.acrticles = acrticles;
        return this;
    }

}

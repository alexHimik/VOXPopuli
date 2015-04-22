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
    private List<Article> articles = new ArrayList<Article>();

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
     * The articles
     */
    public List<Article> getArticles() {
        return articles;
    }

    /**
     *
     * @param articles
     * The articles
     */
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Group withArticles(List<Article> articles) {
        this.articles = articles;
        return this;
    }

}

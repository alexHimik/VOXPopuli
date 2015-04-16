package kz.voxpopuli.voxapplication.network.wrappers.search.string;

/**
 * Created by user on 15.04.15.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

import kz.voxpopuli.voxapplication.network.wrappers.mpage.Article;

public class SearchResponse {

    @Expose
    private List<Article> articles = new ArrayList<Article>();

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

    public SearchResponse withArticles(List<Article> articles) {
        this.articles = articles;
        return this;
    }

}

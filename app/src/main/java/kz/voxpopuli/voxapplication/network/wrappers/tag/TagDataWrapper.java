package kz.voxpopuli.voxapplication.network.wrappers.tag;

/**
 * Created by user on 27.04.15.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

import kz.voxpopuli.voxapplication.network.wrappers.mpage.Article;

public class TagDataWrapper {

    @Expose
    private Tag tag;
    @Expose
    private List<Article> articles = new ArrayList<Article>();

    /**
     *
     * @return
     * The tag
     */
    public Tag getTag() {
        return tag;
    }

    /**
     *
     * @param tag
     * The tag
     */
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public TagDataWrapper withTag(Tag tag) {
        this.tag = tag;
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

    public TagDataWrapper withArticles(List<Article> articles) {
        this.articles = articles;
        return this;
    }

}

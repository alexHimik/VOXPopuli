package kz.voxpopuli.voxapplication.network.wrappers.info;

/**
 * Created by user on 29.04.15.
 */
import com.google.gson.annotations.Expose;

public class InfoDataWrapper {

    @Expose
    private Article article;

    /**
     *
     * @return
     * The article
     */
    public Article getArticle() {
        return article;
    }

    /**
     *
     * @param article
     * The article
     */
    public void setArticle(Article article) {
        this.article = article;
    }

    public InfoDataWrapper withArticle(Article article) {
        this.article = article;
        return this;
    }

}
package kz.voxpopuli.voxapplication.network.wrappers.article;

/**
 * Created by user on 23.04.15.
 */
import com.google.gson.annotations.Expose;

    public class ArticleDataWrapper {

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

    public ArticleDataWrapper withArticle(Article article) {
        this.article = article;
        return this;
    }

}
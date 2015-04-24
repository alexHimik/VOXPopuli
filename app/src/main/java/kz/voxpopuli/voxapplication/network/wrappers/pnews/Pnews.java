package kz.voxpopuli.voxapplication.network.wrappers.pnews;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

import kz.voxpopuli.voxapplication.network.wrappers.mpage.Article;

public class Pnews {

    @Expose
    private Article article;
    @Expose
    private List<Content> content = new ArrayList<Content>();
    @Expose
    private List<NewsTag> tags = new ArrayList<NewsTag>();
    @Expose
    private Author author;
    @Expose
    private List<Article> similar = new ArrayList<Article>();

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

    public Pnews withArticle(Article article) {
        this.article = article;
        return this;
    }

    /**
     *
     * @return
     * The content
     */
    public List<Content> getContent() {
        return content;
    }

    /**
     *
     * @param content
     * The content
     */
    public void setContent(List<Content> content) {
        this.content = content;
    }

    public Pnews withContent(List<Content> content) {
        this.content = content;
        return this;
    }

    /**
     *
     * @return
     * The tags
     */
    public List<NewsTag> getTags() {
        return tags;
    }

    /**
     *
     * @param tags
     * The tags
     */
    public void setTags(List<NewsTag> tags) {
        this.tags = tags;
    }

    public Pnews withTags(List<NewsTag> tags) {
        this.tags = tags;
        return this;
    }

    /**
     *
     * @return
     * The author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     *
     * @param author
     * The author
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    public Pnews withAuthor(Author author) {
        this.author = author;
        return this;
    }

    /**
     *
     * @return
     * The similar
     */
    public List<Article> getSimilar() {
        return similar;
    }

    /**
     *
     * @param similar
     * The similar
     */
    public void setSimilar(List<Article> similar) {
        this.similar = similar;
    }

    public Pnews withSimilar(List<Article> similar) {
        this.similar = similar;
        return this;
    }

}
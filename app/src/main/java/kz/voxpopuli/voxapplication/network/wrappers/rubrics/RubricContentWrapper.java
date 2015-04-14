package kz.voxpopuli.voxapplication.network.wrappers.rubrics;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

import kz.voxpopuli.voxapplication.network.wrappers.mpage.Article;

public class RubricContentWrapper {

    @Expose
    private String rubricId;
    @Expose
    private String rubricImage;
    @Expose
    private List<Article> articles = new ArrayList<Article>();

    /**
     *
     * @return
     * The rubricId
     */
    public String getRubricId() {
        return rubricId;
    }

    /**
     *
     * @param rubricId
     * The rubricId
     */
    public void setRubricId(String rubricId) {
        this.rubricId = rubricId;
    }

    /**
     *
     * @return
     * The rubricImage
     */
    public String getRubricImage() {
        return rubricImage;
    }

    /**
     *
     * @param rubricImage
     * The rubricImage
     */
    public void setRubricImage(String rubricImage) {
        this.rubricImage = rubricImage;
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

}

package kz.voxpopuli.voxapplication.network.wrappers.mpage;

/**
 * Created by user on 10.04.15.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Main {

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
}

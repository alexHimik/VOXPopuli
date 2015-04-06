package kz.voxpopuli.voxapplication.model;

/**
 * Created by user on 27.03.15.
 */
public class SimpleListItemModel {

    private String title;
    private String url;

    public SimpleListItemModel() {}

    public SimpleListItemModel(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

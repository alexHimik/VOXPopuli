package kz.voxpopuli.voxapplication.events;

import java.io.Serializable;

/**
 * Created by user on 09.04.15.
 */
public class CategorySelectedEvent implements Serializable {

    public static final String CATEGORY_DATA = "category_data";

    public static final String TEST_URL = "http://i.ytimg.com/vi/6klq1aVtZLM/maxresdefault.jpg";

    private int categoryId;
    private String categoryName;

    public CategorySelectedEvent() {
    }

    public CategorySelectedEvent(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

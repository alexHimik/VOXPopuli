package kz.voxpopuli.voxapplication.model;

public class ModelArticle {
    public String image, image_mid, image_big, title, description, date;
    public int comment, views;


    public ModelArticle(String image, String image_mid, String image_big, String title, String description, String date, int comment, int views){
        this.image = image;
        this.image_big = image_big;
        this.image_mid = image_mid;
        this.title = title;
        this.description = description;
        this.date = date;
        this.comment = comment;
        this.views = views;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_mid() {
        return image_mid;
    }

    public void setImage_mid(String image_mid) {
        this.image_mid = image_mid;
    }

    public String getImage_big() {
        return image_big;
    }

    public void setImage_big(String image_big) {
        this.image_big = image_big;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}

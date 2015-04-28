package kz.voxpopuli.voxapplication.events;

/**
 * Created by user on 28.04.15.
 */
public class UserAvatarSelectedEvent {

    private String imagePath;

    public UserAvatarSelectedEvent() {
    }

    public UserAvatarSelectedEvent(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

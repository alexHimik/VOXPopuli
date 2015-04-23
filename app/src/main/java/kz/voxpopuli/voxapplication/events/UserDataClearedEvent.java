package kz.voxpopuli.voxapplication.events;

/**
 * Created by user on 23.04.15.
 */
public class UserDataClearedEvent {

    private boolean cleared;

    public UserDataClearedEvent() {
    }

    public UserDataClearedEvent(boolean cleared) {
        this.cleared = cleared;
    }

    public boolean isCleared() {
        return cleared;
    }

    public void setCleared(boolean cleared) {
        this.cleared = cleared;
    }
}

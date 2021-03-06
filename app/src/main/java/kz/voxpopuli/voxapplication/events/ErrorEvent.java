package kz.voxpopuli.voxapplication.events;

import java.io.Serializable;

/**
 * Created by user on 07.04.15.
 */
public class ErrorEvent implements Serializable {

    private String message;
    private int code;

    public ErrorEvent(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

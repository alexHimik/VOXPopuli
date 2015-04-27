package kz.voxpopuli.voxapplication.network.wrappers.comments;

/**
 * Created by user on 27.04.15.
 */
import com.google.gson.annotations.Expose;

public class PostUserCommentWrapper {

    @Expose
    private String result;

    /**
     *
     * @return
     * The result
     */
    public String getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    public void setResult(String result) {
        this.result = result;
    }

    public PostUserCommentWrapper withResult(String result) {
        this.result = result;
        return this;
    }

}

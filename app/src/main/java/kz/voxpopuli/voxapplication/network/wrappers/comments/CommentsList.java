package kz.voxpopuli.voxapplication.network.wrappers.comments;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class CommentsList {

    @Expose
    private List<Comment> comments = new ArrayList<Comment>();

    /**
     *
     * @return
     * The comments
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     *
     * @param comments
     * The comments
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}

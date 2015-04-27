package kz.voxpopuli.voxapplication.network.wrappers.comments.article;

/**
 * Created by user on 27.04.15.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class ArticleCommentsWrapper {

    @Expose
    private List<ArcticleComment> arcticleComments = new ArrayList<ArcticleComment>();

    /**
     *
     * @return
     * The arcticleComments
     */
    public List<ArcticleComment> getArcticleComments() {
        return arcticleComments;
    }

    /**
     *
     * @param arcticleComments
     * The arcticleComments
     */
    public void setArcticleComments(List<ArcticleComment> arcticleComments) {
        this.arcticleComments = arcticleComments;
    }

    public ArticleCommentsWrapper withArcticleComments(List<ArcticleComment> arcticleComments) {
        this.arcticleComments = arcticleComments;
        return this;
    }

}

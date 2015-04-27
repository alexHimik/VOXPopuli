package kz.voxpopuli.voxapplication.network.wrappers.search.top;

/**
 * Created by user on 16.04.15.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class SearchResponse {

    @Expose
    private List<Group> groups = new ArrayList<Group>();

    /**
     *
     * @return
     * The groups
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     *
     * @param groups
     * The groups
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public SearchResponse withGroups(List<Group> groups) {
        this.groups = groups;
        return this;
    }

}
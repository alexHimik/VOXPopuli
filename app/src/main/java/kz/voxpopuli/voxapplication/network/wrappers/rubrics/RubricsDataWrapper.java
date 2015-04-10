package kz.voxpopuli.voxapplication.network.wrappers.rubrics;

/**
 * Created by user on 10.04.15.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class RubricsDataWrapper {

    @Expose
    private List<RubricsInfo> rubricsInfo = new ArrayList<RubricsInfo>();

    /**
     *
     * @return
     * The rubricsInfo
     */
    public List<RubricsInfo> getRubricsInfo() {
        return rubricsInfo;
    }

    /**
     *
     * @param rubricsInfo
     * The rubricsInfo
     */
    public void setRubricsInfo(List<RubricsInfo> rubricsInfo) {
        this.rubricsInfo = rubricsInfo;
    }

}

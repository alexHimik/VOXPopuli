package kz.voxpopuli.voxapplication.network.wrappers.pnews;


import com.google.gson.annotations.Expose;

public class PageNewsWrapper {
    @Expose
    private Pnews pnews;

    /**
     *
     * @return
     * The pnews
     */
    public Pnews getPnews() {
        return pnews;
    }

    /**
     *
     * @param pnews
     * The pnews
     */
    public void setPnews(Pnews pnews) {
        this.pnews = pnews;
    }
}

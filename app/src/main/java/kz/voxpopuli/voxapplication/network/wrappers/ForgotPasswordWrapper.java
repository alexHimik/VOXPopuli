package kz.voxpopuli.voxapplication.network.wrappers;

import com.google.gson.annotations.Expose;

public class ForgotPasswordWrapper {

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

    public ForgotPasswordWrapper withResult(String result) {
        this.result = result;
        return this;
    }
}
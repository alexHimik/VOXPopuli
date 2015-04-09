package kz.voxpopuli.voxapplication.events;

import java.io.Serializable;

/**
 * Created by user on 09.04.15.
 */
public class RubricSelectedEvent implements Serializable {

    public static final String RUBRIC_DATA = "rubric_data";

    private int rubricId;
    private String rubricName;

    public RubricSelectedEvent() {
    }

    public RubricSelectedEvent(int rubricId, String rubricName) {
        this.rubricId = rubricId;
        this.rubricName = rubricName;
    }

    public int getRubricId() {
        return rubricId;
    }

    public void setRubricId(int rubricId) {
        this.rubricId = rubricId;
    }

    public String getRubricName() {
        return rubricName;
    }

    public void setRubricName(String rubricName) {
        this.rubricName = rubricName;
    }
}

package kz.voxpopuli.voxapplication.network.util;

/**
 * Created by user on 10.04.15.
 */
public interface VoxProviderUrls {

    public static final String RUBRIC_ID_IDENTIFIER = "%rubricId%";

    public static final String MAIN_PAGE_CONTENT = "http://www.voxpopuli.kz/api/home.json";
    public static final String RUBRICS_INFO = "http://www.voxpopuli.kz/api/rubrics.json";
    public static final String RUBRIC_CONTENT = "http://www.voxpopuli.kz/api/rubric/%rubricId%.json";
}

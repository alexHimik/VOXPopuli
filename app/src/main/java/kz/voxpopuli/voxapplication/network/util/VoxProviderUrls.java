package kz.voxpopuli.voxapplication.network.util;

/**
 * Created by user on 10.04.15.
 */
public interface VoxProviderUrls {

    public static final String RUBRIC_ID_IDENTIFIER = "%rubricId%";
    public static final String RUBRIC_PAGE_IDENTIFIER = "%page%";
    public static final String USER_ID_IDENTIFIER = "%userId%";

    public static final String SALT = "VoxPopuli_API_SaLt_%#@$";

    public static final String MAIN_PAGE_CONTENT_REQUEST = "http://www.voxpopuli.kz/api/home.json";
    public static final String RUBRICS_INFO_REQUEST = "http://www.voxpopuli.kz/api/rubrics.json";
    public static final String RUBRIC_CONTENT_REQUEST = "http://www.voxpopuli.kz/api/rubric/%rubricId%/%page%.json";
    public static final String SEARCH_REQUEST = "http://www.voxpopuli.kz/api/search.json";
    public static final String SIGN_IN_REQUEST = "http://www.voxpopuli.kz/api/sign_in.json";
    public static final String USER_COMMENTS_REQUEST = "http://www.voxpopuli.kz/api/user/%userId%.json";

}

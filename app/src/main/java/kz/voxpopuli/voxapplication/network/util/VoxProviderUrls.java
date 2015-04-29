package kz.voxpopuli.voxapplication.network.util;

/**
 * Created by user on 10.04.15.
 */
public interface VoxProviderUrls {

    public static final String RUBRIC_ID_IDENTIFIER = "%rubricId%";
    public static final String RUBRIC_PAGE_IDENTIFIER = "%page%";
    public static final String USER_ID_IDENTIFIER = "%userId%";
    public static final String NEW_IDENTIFIER = "%newId%";
    public static final String TAG_IDENTIFIER = "%tagId%";
    public static final String ARTICLE_IDENTIFIER = "%articleId%";

    public static final String SALT = "VoxPopuli_API_SaLt_%#@$";

    public static final String MAIN_PAGE_CONTENT_REQUEST = "http://www.voxpopuli.kz/api/home.json";
    public static final String RUBRICS_INFO_REQUEST = "http://www.voxpopuli.kz/api/rubrics.json";
    public static final String RUBRIC_CONTENT_REQUEST = "http://www.voxpopuli.kz/api/rubric/%rubricId%/%page%.json";
    public static final String SEARCH_REQUEST = "http://www.voxpopuli.kz/api/search.json";
    public static final String SIGN_IN_REQUEST = "http://www.voxpopuli.kz/api/sign_in.json";
    public static final String USER_COMMENTS_REQUEST = "http://www.voxpopuli.kz/api/user/%userId%.json";
    public static final String NEWS_PAGE_DATA_REQUEST = "http://www.voxpopuli.kz/api/news/%newId%.json";
    public static final String USER_DATA_EDIT_REQUEST = "http://www.voxpopuli.kz/api/edit.json";
    public static final String SIGN_UP_USER_REQUEST = "http://www.voxpopuli.kz/api/sign_up.json";
    public static final String TAG_INFO_REQUEST = "http://www.voxpopuli.kz/api/tag/%tagId%.json";
    public static final String ARTICLE_COMMENTS_REQUEST = "http://www.voxpopuli.kz/api/comments/%articleId%/%page%.json";
    public static final String POST_ARTICLE_COMMENT_REQUEST = "http://www.voxpopuli.kz/api/comment.json";

    public static final String RULES_INFO_REQUEST = "http://www.voxpopuli.kz/api/news/10093.json";
    public static final String ABOUT_INFO_REQUEST = "http://www.voxpopuli.kz/api/news/10092.json";
    public static final String VACANCY_INFO_REQUEST = "http://www.voxpopuli.kz/api/news/11641.json";
}

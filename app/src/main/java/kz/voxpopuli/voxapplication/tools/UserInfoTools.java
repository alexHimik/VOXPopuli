package kz.voxpopuli.voxapplication.tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 14.04.15.
 */
public class UserInfoTools {

    private static final String USER_PROPS_NAME = "user_info";
    private static final String USER_FIRST_NAME = "user_fn";
    private static final String USER_LAST_NAME = "user_ln";
    private static final String USER_LOGIN = "user_login";
    private static final String USER_PASSWORD = "user_password";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_PHOTO_URL = "user_avatar_url";


    public static void saveUserFirstName(Context context, String firstName) {
        getEditor(context).putString(USER_FIRST_NAME, firstName).commit();
    }

    public static String getUserFirstName(Context context) {
        return getSharedPreferences(context).getString(USER_FIRST_NAME, null);
    }

    public static void saveUserLastName(Context context, String lastName) {
        getEditor(context).putString(USER_LAST_NAME, lastName).commit();
    }

    public static String getUserLastName(Context context) {
        return getSharedPreferences(context).getString(USER_LAST_NAME, null);
    }

    public static void saveUserLogin(Context context, String login) {
        getEditor(context).putString(USER_LOGIN, login).commit();
    }

    public static String getUserLogin(Context context) {
        return getSharedPreferences(context).getString(USER_LOGIN, null);
    }

    public static void saveUserPassword(Context context, String password) {
        getEditor(context).putString(USER_PASSWORD, password).commit();
    }

    public static String getUserPassword(Context context) {
        return getSharedPreferences(context).getString(USER_PASSWORD, null);
    }

    public static void saveUserEmail(Context context, String email) {
        getEditor(context).putString(USER_EMAIL, email).commit();
    }

    public static String getUserEmail(Context context) {
        return getSharedPreferences(context).getString(USER_EMAIL, null);
    }

    public static void saveUserAvatarUrl(Context context, String avatarUrl) {
        getEditor(context).putString(USER_PHOTO_URL, avatarUrl).commit();
    }

    public static String getUserAvatarUrl(Context context) {
        return getSharedPreferences(context).getString(USER_PHOTO_URL, null);
    }

    public static boolean isUserLoggedIn(Context context) {
        return getUserEmail(context) != null && getUserPassword(context) != null;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(USER_PROPS_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return context.getSharedPreferences(USER_PROPS_NAME, Context.MODE_PRIVATE).edit();
    }
}

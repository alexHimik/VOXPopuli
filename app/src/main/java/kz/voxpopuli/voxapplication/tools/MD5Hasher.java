package kz.voxpopuli.voxapplication.tools;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;

/**
 * Created by user on 21.04.15.
 */
public class MD5Hasher {
    public static String getHash(String... params) {
        StringBuilder builder = new StringBuilder();
        for(String s : params) {
            builder.append(s);
        }
        return new String(DigestUtils.md5(builder.toString()));
    }

    public static String getHash(Map<String, String> data) {
        StringBuilder builder = new StringBuilder();
        builder.append("email");
        builder.append("=");
        builder.append(data.get("email"));
        builder.append("&");
        builder.append("password");
        builder.append("=");
        builder.append(data.get("password"));
        builder.append("&");
        builder.append(data.get(""));
        return DigestUtils.md5Hex(builder.toString());
    }
}

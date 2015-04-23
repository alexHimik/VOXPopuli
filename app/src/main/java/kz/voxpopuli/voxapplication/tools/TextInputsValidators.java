package kz.voxpopuli.voxapplication.tools;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by user on 23.04.15.
 */
public class TextInputsValidators {

    public static boolean isInputEmpty(EditText field) {
        return TextUtils.isEmpty(field.getText());
    }

    public static boolean isInputLengthEnought(EditText field, int minLength) {
        return field != null ? (field.getText().length() > 0 && field.getText().length() >= minLength) : false;
    }

    public static boolean isInputEmail(EditText field) {
        if (TextUtils.isEmpty(field.getText())) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(field.getText().toString()).matches();
        }
    }
}

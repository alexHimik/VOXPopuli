package kz.voxpopuli.voxapplication.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.dialog.QustomDialogBuilder;

/**
 * Created by user on 27.04.15.
 */
public class DialogTools {

    public static void showInfoDialog(Context context, String title, String msg) {
        QustomDialogBuilder errorDialog = new QustomDialogBuilder(context);
        errorDialog.setTitleColor(context.getString(R.string.alert_dialog_color));
        errorDialog.setDividerColor(context.getString(R.string.alert_dialog_color));
        errorDialog.setTitle(title);
        errorDialog.setMessage(msg);
        errorDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        errorDialog.create().show();
    }

    public static void showNetworkErrorDialog(final Context context, String title, String msg) {
        QustomDialogBuilder errorDialog = new QustomDialogBuilder(context);
        errorDialog.setTitleColor(context.getString(R.string.alert_dialog_color));
        errorDialog.setDividerColor(context.getString(R.string.alert_dialog_color));
        errorDialog.setTitle(title);
        errorDialog.setMessage(msg);
        errorDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                context.startActivity(intent);
            }
        });
        errorDialog.setNegativeButton(context.getString(R.string.cancel_button_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        errorDialog.create().show();
    }
}

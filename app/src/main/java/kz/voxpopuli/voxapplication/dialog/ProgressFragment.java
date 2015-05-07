package kz.voxpopuli.voxapplication.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import kz.voxpopuli.voxapplication.fragments.BackStackDataDescriber;

/**
 * Created by user on 28.04.15.
 */
public class ProgressFragment extends DialogFragment implements BackStackDataDescriber {

    public static final String TAG = "ProgressFragment";
    public static final int FRAGMENT_ID = 100500;

    private ProgressDialog progressDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Test");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        return progressDialog;
    }

    public void startProgress() {
        progressDialog.show();
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }
}

package kz.voxpopuli.voxapplication.model.rows;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by user on 03.04.15.
 */
public interface IRowItemModel {
    public int getResourceId();
    public View initModelsViews(LayoutInflater inflater);
    public void setModelDataToViews(Context fragment);
    public int getId();
}

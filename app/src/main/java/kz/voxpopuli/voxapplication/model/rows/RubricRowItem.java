package kz.voxpopuli.voxapplication.model.rows;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import kz.voxpopuli.voxapplication.R;

/**
 * Created by user on 03.04.15.
 */
public class RubricRowItem implements IRowItemModel {

    private String rubricImageUrl;
    private String rubricNameText;

    private ImageView rubricImage;
    private TextView rubricName;

    public String getRubricImageUrl() {
        return rubricImageUrl;
    }

    public void setRubricImageUrl(String rubricImageUrl) {
        this.rubricImageUrl = rubricImageUrl;
    }

    public String getRubricNameText() {
        return rubricNameText;
    }

    public void setRubricNameText(String rubricNameText) {
        this.rubricNameText = rubricNameText;
    }

    @Override
    public int getResourceId() {
        return R.layout.rubric_icon;
    }

    @Override
    public View initModelsViews(LayoutInflater inflater) {
        View parent = inflater.inflate(getResourceId(), null);
        rubricImage = (ImageView)parent.findViewById(R.id.rubric_image_back);
        rubricName = (TextView)parent.findViewById(R.id.rubric_title);
        return parent;
    }

    @Override
    public void setModelDataToViews(Context context) {
        rubricName.setText(rubricNameText);
        Glide.with(context).load(rubricImageUrl).centerCrop().into(rubricImage);
    }
}

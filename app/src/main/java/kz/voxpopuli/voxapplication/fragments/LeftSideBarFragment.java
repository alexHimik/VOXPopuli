package kz.voxpopuli.voxapplication.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.util.LinkedList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.model.rows.IRowItemModel;
import kz.voxpopuli.voxapplication.model.rows.RubricRowItem;
import kz.voxpopuli.voxapplication.view.RowHoster;

/**
 * Created by user on 03.04.15.
 */
public class LeftSideBarFragment extends Fragment {

    private ImageView bluredBackImage;
    private ImageView userAvatar;
    private TextView userName;
    private RowHoster rowHoster;
    private LinearLayout settingsLayout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.left_side_bar, container);
        initViews(parent);
        fillHosterWithData();
        return parent;
    }

    private void initViews(View parent) {
        bluredBackImage = (ImageView)parent.findViewById(R.id.left_bar_user_photo_blured);
        userAvatar = (ImageView)parent.findViewById(R.id.left_bar_user_avatar);
        userName = (TextView)parent.findViewById(R.id.left_bar_user_name);
        rowHoster = (RowHoster)parent.findViewById(R.id.left_bar_rubrics_hoster);
        settingsLayout = (LinearLayout)parent.findViewById(R.id.left_bar_settings);

        BitmapPool pool = Glide.get(getActivity()).getBitmapPool();
        Glide.with(this).load("http://images.motofan.com/N/9/9/6/mas-accesorios-originales-para-honda-nc700s-nc700x_hd_26838.jpg").
                centerCrop().bitmapTransform(new BlurTransformation(getActivity(), pool, 7)).into(bluredBackImage);
        Glide.with(this).load("http://images.motofan.com/N/9/9/6/mas-accesorios-originales-para-honda-nc700s-nc700x_hd_26838.jpg").
                centerCrop().bitmapTransform(new CropCircleTransformation(pool)).into(userAvatar);

        userName.setText("User name!!!!");
    }

    private void fillHosterWithData() {
        rowHoster.initHoster(R.layout.left_bar_host_header, R.layout.hoster_row);
        List<IRowItemModel> models = new LinkedList<>();
        for(int i = 0; i <= 15; i++) {
            RubricRowItem item = new RubricRowItem();
            item.setRubricNameText("Rubric " + i);
            item.setRubricImageUrl("http://images.motofan.com/N/9/9/6/mas-accesorios-originales-para-honda-nc700s-nc700x_hd_26838.jpg");
            models.add(item);
        }
        rowHoster.setRowItemModels(models);
    }
}

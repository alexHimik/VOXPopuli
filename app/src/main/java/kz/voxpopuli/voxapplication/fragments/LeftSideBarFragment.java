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

import de.greenrobot.event.EventBus;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.model.rows.IRowItemModel;
import kz.voxpopuli.voxapplication.model.rows.RubricRowItem;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.RubricsInfoRequest;
import kz.voxpopuli.voxapplication.network.wrappers.rubrics.RubricsDataWrapper;
import kz.voxpopuli.voxapplication.network.wrappers.rubrics.RubricsInfo;
import kz.voxpopuli.voxapplication.tools.LeftBarCategoryHelper;
import kz.voxpopuli.voxapplication.tools.UserInfoTools;
import kz.voxpopuli.voxapplication.view.RowHoster;

/**
 * Created by user on 03.04.15.
 */
public class LeftSideBarFragment extends Fragment implements View.OnClickListener {

    private ImageView bluredBackImage;
    private ImageView userAvatar;
    private TextView userName;
    private RowHoster rowHoster;
    private LinearLayout settingsLayout;
    private LeftBarCategoryHelper categoryHelper;

    private List<IRowItemModel> rubricsModels = new LinkedList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View parent = inflater.inflate(R.layout.left_side_bar, container);
        categoryHelper = new LeftBarCategoryHelper(getActivity());
        initViews(parent);
        categoryHelper.initCategories(parent);
        fillHosterWithData();
        return parent;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.left_bar_user_name) {
            //TODO redirect to the user profile page
        }
    }

    private void initViews(View parent) {
        bluredBackImage = (ImageView)parent.findViewById(R.id.left_bar_user_photo_blured);
        userAvatar = (ImageView)parent.findViewById(R.id.left_bar_user_avatar);
        userName = (TextView)parent.findViewById(R.id.left_bar_user_name);
        rowHoster = (RowHoster)parent.findViewById(R.id.left_bar_rubrics_hoster);
        settingsLayout = (LinearLayout)parent.findViewById(R.id.left_bar_settings);

        if(UserInfoTools.isUserLoggedIn(getActivity())) {
            BitmapPool pool = Glide.get(getActivity()).getBitmapPool();
            Glide.with(this).load(UserInfoTools.getUserAvatarUrl(getActivity())).
                    centerCrop().bitmapTransform(new BlurTransformation(getActivity(), pool, 7)).into(bluredBackImage);
            Glide.with(this).load(UserInfoTools.getUserAvatarUrl(getActivity())).
                    centerCrop().bitmapTransform(new CropCircleTransformation(pool)).into(userAvatar);
            StringBuilder builder = new StringBuilder();
            builder.append(UserInfoTools.getUserFirstName(getActivity()));
            builder.append(" ");
            builder.append(UserInfoTools.getUserLastName(getActivity()));
            userName.setText(builder.toString());
        } else {
            userName.setOnClickListener(this);
        }
    }

    private void fillHosterWithData() {
        rowHoster.initHoster(R.layout.left_bar_host_header, R.layout.hoster_row);
        VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(
                new RubricsInfoRequest((MainActivity)getActivity()));
    }

    public void onEvent(RubricsDataWrapper rubricsDataWrapper) {
        for(RubricsInfo info : rubricsDataWrapper.getRubricsInfo()) {
            int main = Integer.parseInt(info.getId());
            RubricRowItem item = new RubricRowItem();
            item.setRubricNameText(info.getTitle());
            item.setRubricImageUrl(info.getImage());
            item.setId(info.getId());
            if(main > 0) {
                categoryHelper.getCategoriesInfo().add(item);
            } else {
                rubricsModels.add(item);
            }
        }
        rowHoster.setRowItemModels(rubricsModels);
        categoryHelper.selectCategory(R.id.left_bar_category_all);
    }
}

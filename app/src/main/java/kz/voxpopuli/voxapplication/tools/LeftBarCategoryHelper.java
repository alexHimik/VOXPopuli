package kz.voxpopuli.voxapplication.tools;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.events.CategorySelectedEvent;

/**
 * Created by user on 07.04.15.
 */
public class LeftBarCategoryHelper {

    private static List<Integer> categoryItemIds = new LinkedList<>();
    private static Map<Integer, Integer> categoryNames = new HashMap<>();
    private Map<Integer, TextView> categoryNameTextViews = new HashMap<>();
    private Map<Integer, LinearLayout> categoryBackgrounds = new HashMap<>();

    private CategoriesOnClickListener innerOnClickListener;
    private Context context;
    private TextView lastSelectedText;
    private LinearLayout lastSelectedLayout;

    static {
        categoryItemIds.add(R.id.left_bar_category_all);
        categoryItemIds.add(R.id.left_bar_category_business);
        categoryItemIds.add(R.id.left_bar_category_history);
        categoryItemIds.add(R.id.left_bar_category_interview);
        categoryItemIds.add(R.id.left_bar_category_vox_populi);

        categoryNames.put(R.id.left_bar_category_all, R.string.category_name_all);
        categoryNames.put(R.id.left_bar_category_business, R.string.category_name_business);
        categoryNames.put(R.id.left_bar_category_history, R.string.category_name_history);
        categoryNames.put(R.id.left_bar_category_interview, R.string.category_name_interview);
        categoryNames.put(R.id.left_bar_category_vox_populi, R.string.category_name_vox_populi);
    }

    public LeftBarCategoryHelper(Context context) {
        this.context = context;
    }

    public void initCategories(View parent) {
        innerOnClickListener = new CategoriesOnClickListener();
        for(Integer i : categoryItemIds) {
            LinearLayout all = (LinearLayout)parent.findViewById(i);
            TextView name = (TextView)all.findViewById(R.id.left_bar_category_name);
            name.setText(categoryNames.get(i));
            all.setOnClickListener(innerOnClickListener);
            categoryNameTextViews.put(i, name);
            categoryBackgrounds.put(i, all);
        }
    }

    private void setCategorySelected(int categoryId) {
        if(lastSelectedLayout != null) {
            lastSelectedLayout.setBackgroundColor(
                    context.getResources().getColor(R.color.vox_white));
        }
        if(lastSelectedText != null) {
            lastSelectedText.setTextColor(context.getResources().getColor(
                    R.color.side_bar_category_text));
        }
        categoryBackgrounds.get(categoryId).setBackgroundColor(
                context.getResources().getColor(R.color.side_bar_category_selected_color));
        categoryNameTextViews.get(categoryId).setTextColor(
                context.getResources().getColor(R.color.vox_red));

        lastSelectedLayout = categoryBackgrounds.get(categoryId);
        lastSelectedText = categoryNameTextViews.get(categoryId);
    }

    private void postCategorySelectedEvent(int categoryId) {
        EventBus.getDefault().post(new CategorySelectedEvent(categoryId,
                context.getString(categoryNames.get(categoryId))));
    }

    private class CategoriesOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_bar_category_all: {
                    setCategorySelected(v.getId());
                    postCategorySelectedEvent(R.id.left_bar_category_all);
                    break;
                }
                case R.id.left_bar_category_business: {
                    setCategorySelected(v.getId());
                    postCategorySelectedEvent(R.id.left_bar_category_business);
                    break;
                }
                case R.id.left_bar_category_history: {
                    setCategorySelected(v.getId());
                    postCategorySelectedEvent(R.id.left_bar_category_history);
                    break;
                }
                case R.id.left_bar_category_interview: {
                    setCategorySelected(v.getId());
                    postCategorySelectedEvent(R.id.left_bar_category_interview);
                    break;
                }
                case R.id.left_bar_category_vox_populi: {
                    setCategorySelected(v.getId());
                    postCategorySelectedEvent(R.id.left_bar_category_vox_populi);
                    break;
                }
            }
        }
    }
}

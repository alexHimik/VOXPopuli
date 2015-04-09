package kz.voxpopuli.voxapplication.tools;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kz.voxpopuli.voxapplication.R;

/**
 * Created by user on 07.04.15.
 */
public class LeftBarCategoryHelper implements View.OnClickListener {

    private static List<Integer> categoryItemIds = new LinkedList<>();
    private static Map<Integer, Integer> categoryNames = new HashMap<>();
    private Map<Integer, TextView> categoryNameTextViews = new HashMap<>();
    private Map<Integer, LinearLayout> categoryBackgrounds = new HashMap<>();

    static {
        categoryItemIds.add(R.id.left_bar_category_all);
        categoryItemIds.add(R.id.left_bar_category_business);
        categoryItemIds.add(R.id.left_bar_category_history);
        categoryItemIds.add(R.id.left_bar_category_interview);
        categoryItemIds.add(R.id.left_bar_category_vox_populi);

        categoryNames.put(R.string.category_name_all, R.string.category_name_all);
        categoryNames.put(R.string.category_name_business, R.string.category_name_business);
        categoryNames.put(R.string.category_name_history, R.string.category_name_history);
        categoryNames.put(R.string.category_name_interview, R.string.category_name_interview);
        categoryNames.put(R.string.category_name_vox_populi, R.string.category_name_vox_populi);
    }

    public void initCategories(View parent) {
        for(Integer i : categoryItemIds) {
            LinearLayout all = (LinearLayout)parent.findViewById(i);
            TextView name = (TextView)all.findViewById(R.id.left_bar_category_name);
            name.setText(categoryNames.get(i));
            all.setOnClickListener(this);
            categoryNameTextViews.put(R.id.left_bar_category_all, name);
            categoryBackgrounds.put(R.id.left_bar_category_all, all);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_bar_category_all: {
                break;
            }
            case R.id.left_bar_category_business: {
                break;
            }
            case R.id.left_bar_category_history: {
                break;
            }
            case R.id.left_bar_category_interview: {
                break;
            }
            case R.id.left_bar_category_vox_populi: {
                break;
            }
        }
    }
}

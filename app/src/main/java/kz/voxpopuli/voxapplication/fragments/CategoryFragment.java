package kz.voxpopuli.voxapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.LinkedList;
import java.util.List;

import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.adapter.SimpleListAdapter;
import kz.voxpopuli.voxapplication.events.CategorySelectedEvent;
import kz.voxpopuli.voxapplication.model.SimpleListItemModel;

/**
 * Created by user on 09.04.15.
 */
public class CategoryFragment extends FaddingTitleBaseFragment {

    public static final String TAG = "CategoryFragment";
    public static final int FRAGMENT_ID = 2;

    private String fragmentTitle = "";

    private SimpleListAdapter listAdapter;
    private List<SimpleListItemModel> items = new LinkedList<SimpleListItemModel>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View superView = super.onCreateView(inflater, container, savedInstanceState);
        //TODO init list view with data and adapter
        fillScreen(mArguments);
        listAdapter = new SimpleListAdapter(this, items);
        fragmentList.setAdapter(listAdapter);
        return superView;
    }

    private void fillScreen(Bundle data) {
        Glide.with(this).load(CategorySelectedEvent.TEST_URL).centerCrop().into(faddingHeader);
        items.clear();
        items.add(new SimpleListItemModel(getActivity().getResources().getString(R.string.vk_app_id),
                "http://jpx.responsejp.com/jpx/images/2012/04/17/173075_8.jpg"));
        items.add(new SimpleListItemModel(getActivity().getResources().getString(R.string.vk_app_id),
                "http://images.motofan.com/N/9/9/6/mas-accesorios-originales-para-honda-nc700s-nc700x_hd_26838.jpg"));
        items.add(new SimpleListItemModel(getActivity().getResources().getString(R.string.vk_app_id),
                "http://i.ytimg.com/vi/6klq1aVtZLM/maxresdefault.jpg"));
        items.add(new SimpleListItemModel(getActivity().getResources().getString(R.string.vk_app_id),
                "http://www.motoplanete.com/honda/zoom-700px/Honda-NC-700-S-2013-700px.jpg"));
        items.add(new SimpleListItemModel(getActivity().getResources().getString(R.string.vk_app_id),
                "http://www.motorrad-testbericht.at/magazin/honda/naked/nc700s/honda_nc700s_7.jpg"));
    }

    @Override
    public String getFragmentTag() {
        return TAG + fragmentTitle;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }
}

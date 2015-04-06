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
import kz.voxpopuli.voxapplication.model.SimpleListItemModel;

/**
 * Created by user on 25.03.15.
 */
public class TestFragmet extends FaddingTitleBaseFragment {

    public static final String TAG  = "TestFragmet";
    public static final int FRAGMENT_ID = 10;

    public static final String PARALAX_IMAGE_HEADER_KEY = "parallax_header";
    public static final String LIST_DATA_KEY = "list_data";

    private SimpleListAdapter listAdapter;
    private List<SimpleListItemModel> items = new LinkedList<SimpleListItemModel>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = super.onCreateView(inflater, container, savedInstanceState);
        fillScreen(mArguments);
        listAdapter = new SimpleListAdapter(this, items);
        fragmentList.setAdapter(listAdapter);
        return result;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }

    private void fillScreen(Bundle data) {
        Glide.with(this).load(data.getString(
                PARALAX_IMAGE_HEADER_KEY)).centerCrop().into(faddingHeader);
        items.clear();
        items.add(new SimpleListItemModel(getActivity().getResources().getString(R.string.big_test_string),
                "http://jpx.responsejp.com/jpx/images/2012/04/17/173075_8.jpg"));
        items.add(new SimpleListItemModel(getActivity().getResources().getString(R.string.big_test_string),
                "http://images.motofan.com/N/9/9/6/mas-accesorios-originales-para-honda-nc700s-nc700x_hd_26838.jpg"));
        items.add(new SimpleListItemModel(getActivity().getResources().getString(R.string.big_test_string),
                "http://i.ytimg.com/vi/6klq1aVtZLM/maxresdefault.jpg"));
        items.add(new SimpleListItemModel(getActivity().getResources().getString(R.string.big_test_string),
                "http://www.motoplanete.com/honda/zoom-700px/Honda-NC-700-S-2013-700px.jpg"));
        items.add(new SimpleListItemModel(getActivity().getResources().getString(R.string.big_test_string),
                "http://www.motorrad-testbericht.at/magazin/honda/naked/nc700s/honda_nc700s_7.jpg"));
    }
}

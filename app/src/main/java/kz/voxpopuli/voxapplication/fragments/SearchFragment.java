package kz.voxpopuli.voxapplication.fragments;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.adapter.SearchByStringAdapter;
import kz.voxpopuli.voxapplication.adapter.SearchByTopAdapter;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.SearchByStringRequest;
import kz.voxpopuli.voxapplication.network.request.SearchByTopRequest;
import kz.voxpopuli.voxapplication.network.wrappers.mpage.Article;
import kz.voxpopuli.voxapplication.network.wrappers.search.string.SearchByStringWrapper;
import kz.voxpopuli.voxapplication.network.wrappers.search.top.Group;
import kz.voxpopuli.voxapplication.network.wrappers.search.top.SearchByTopWrapper;

/**
 * Created by user on 15.04.15.
 */
public class SearchFragment extends BaseFragment {

    public static final String TAG = "SearchFragment";
    public static final int FRAGMENT_ID = 10;

    public static final int TOP_MODE = 0;
    public static final int STRING_MODE = 1;

    private int mode = STRING_MODE; //TOP_MODE;   //TODO replace to top by default

    private ListView contentList;
    private RobotoTextView headerText;
    private LinearLayout header;
    private BaseAdapter listAdapter;

    private List<Article> stringModeData = new LinkedList<>();
    private List<Group> topModeData = new LinkedList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        contentList = (ListView)inflater.inflate(R.layout.search_string_fragment, null);
        return contentList;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        runTopMode();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public View getActionBarCustomView(LayoutInflater inflater) {
        View customBar = inflater.inflate(R.layout.search_action_bar, null);
        leftbarItem = customBar.findViewById(R.id.left_drawer_item);
        centerBatItem = customBar.findViewById(R.id.search_input);
        rightBarItem = customBar.findViewById(R.id.right_drawer_item);
        leftItemTouch = customBar.findViewById(R.id.left_drawer_item_touch);
        initActionBarItems();
        ((ActionBarActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.vox_white)));
        ((ActionBarActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        return customBar;
    }

    @Override
    public void initActionBarItems() {
        leftItemTouch.setOnClickListener(backListener);
        ((EditText)centerBatItem).addTextChangedListener(searchInputListener);
        rightBarItem.setOnClickListener(backListener);
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }

    private void handleSearchMode(String input) {
        if(input.length() > 0) {
            rightBarItem.setVisibility(View.VISIBLE);
            runStringMode(input);
        } else {
            rightBarItem.setVisibility(View.INVISIBLE);
            runTopMode();
        }
    }

    private void runStringMode(String forSearch) {
        initFragmentForStringSearch(contentList);
        Map<String, String> params = new HashMap<>();
        params.put(SearchByStringRequest.SEARCH_STRING_PARAM, forSearch);
        Request request = new SearchByStringRequest(params,
                ((MainActivity)getActivity()));
        request.setTag(TAG);
        VolleyNetworkProvider provider = VolleyNetworkProvider.getInstance(getActivity());
        provider.getRequestQueue().cancelAll(TAG);
        provider.addToRequestQueue(request);
    }

    private void runTopMode() {
        initFragmentForTopSearch(contentList);
        Map<String, String> params = new HashMap<>();
        params.put(SearchByTopRequest.TOP_MATERIALS_PARAM, "true");
        Request request = new SearchByTopRequest(params,
                ((MainActivity)getActivity()));
        VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(request);
    }

    private void initFragmentForStringSearch(ListView container) {
        if(contentList.getHeaderViewsCount() == 0) {
            if(header == null) {
                LayoutInflater inflater = (LayoutInflater)getActivity().
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                header = (LinearLayout)inflater.inflate(
                        R.layout.search_string_fragment_titile, null);
                headerText = (RobotoTextView)header.findViewById(R.id.search_by_string_list_header);
            }
            contentList.addHeaderView(header);
        }
        listAdapter = new SearchByStringAdapter(this, stringModeData);
        contentList.setAdapter(listAdapter);
    }

    private void initFragmentForTopSearch(ListView container) {
        if(contentList.getHeaderViewsCount() > 0) {
            contentList.removeHeaderView(header);
        }
        listAdapter = new SearchByTopAdapter(this, topModeData);
        contentList.setAdapter(listAdapter);
    }

    public void onEvent(SearchByStringWrapper searchByStringWrapper) {
        stringModeData.clear();
        List<Article> data = searchByStringWrapper.getSearchResponse().getArticles();
        headerText.setText(getString(R.string.string_search_header).replaceAll("%amount%",
                String.valueOf(data.size())));
        if(data != null && data.size() > 0) {
            stringModeData.addAll(data);
            listAdapter = new SearchByStringAdapter(this, stringModeData);
            contentList.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        }
    }

    public void onEvent(SearchByTopWrapper searchByTopWrapper) {
        topModeData.clear();
        List<Group> data = searchByTopWrapper.getSearchResponse().getGroups();
        if(data != null && data.size() > 0) {
            topModeData.addAll(data);
            listAdapter = new SearchByTopAdapter(this, topModeData);
            contentList.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        }
    }

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.right_drawer_item) {
                ((EditText)centerBatItem).setText("");
            } else if(v.getId() == R.id.left_drawer_item_touch) {
                ((MainActivity)getActivity()).onBackPressed();
            }
        }
    };

    private TextWatcher searchInputListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //do nothing here
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //do nothing here
        }

        @Override
        public void afterTextChanged(Editable s) {
            handleSearchMode(s.toString());
        }
    };
}

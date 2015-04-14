package kz.voxpopuli.voxapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.adapter.ArticlesAdapter;
import kz.voxpopuli.voxapplication.events.CategorySelectedEvent;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.MainPageContentRequest;
import kz.voxpopuli.voxapplication.network.request.RubricContentRequest;
import kz.voxpopuli.voxapplication.network.wrappers.mpage.Article;
import kz.voxpopuli.voxapplication.network.wrappers.mpage.MainPageDataWrapper;
import kz.voxpopuli.voxapplication.network.wrappers.rubrics.RubricContentWrapper;

/**
 * Created by user on 09.04.15.
 */
public class CategoryFragment extends BaseFragment implements Response.ErrorListener,
        ListView.OnItemClickListener {

    public static final String TAG = "CategoryFragment";
    public static final int FRAGMENT_ID = 2;

    private ListView lv;
    public List<Article> articles = new LinkedList<>();
    private ArticlesAdapter articlesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle data = getArguments();
        CategorySelectedEvent selectedEvent = (CategorySelectedEvent)data.get(
                CategorySelectedEvent.CATEGORY_DATA);

        Request request;
        if(getString(R.string.category_name_all).equals(selectedEvent.getCategoryName())) {
            request = new MainPageContentRequest(this);
            getActivity().setTitle(getString(R.string.category_name_all));
        } else {
            request = new RubricContentRequest(String.valueOf(selectedEvent.getCategoryId()), this);
            getActivity().setTitle(selectedEvent.getCategoryName());
        }
        VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(request);

        View parent = inflater.inflate(R.layout.articles, container, false);
        lv = (ListView) parent.findViewById(R.id.lv_articles);
        articlesAdapter = new ArticlesAdapter(this, articles);
        lv.setAdapter(articlesAdapter);
        lv.setOnItemClickListener(this);
        return parent;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }

    //handling events for main page and categories data
    public void onEvent(MainPageDataWrapper wrapper) {
        if (!articles.isEmpty()) {
            articles.clear();
        }
        articles.addAll(wrapper.getMain().getArticles());
        articlesAdapter.notifyDataSetChanged();
    }

    public void onEvent(RubricContentWrapper rubricContentWrapper) {
        articles.addAll(rubricContentWrapper.getArticles());
        articlesAdapter.notifyDataSetChanged();
    }
}

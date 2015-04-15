package kz.voxpopuli.voxapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
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
public class CategoryFragment extends BaseFragment implements ListView.OnItemClickListener,
        SwipyRefreshLayout.OnRefreshListener {

    public static final String TAG = "CategoryFragment";
    public static final int FRAGMENT_ID = 2;

    private ListView lv;
    private SwipyRefreshLayout swipeRefreshLayout;
    private List<Article> articles = new LinkedList<>();
    private ArticlesAdapter articlesAdapter;

    private int currentPage = 1;
    private String currentRubricId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        swipeRefreshLayout = (SwipyRefreshLayout)inflater.inflate(R.layout.articles, container, false);
        lv = (ListView) swipeRefreshLayout.findViewById(R.id.lv_articles);
        articlesAdapter = new ArticlesAdapter(this, articles, true);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.vox_red),
                getActivity().getResources().getColor(R.color.vox_red),
                getActivity().getResources().getColor(R.color.vox_red));
        lv.setAdapter(articlesAdapter);
        lv.setOnItemClickListener(this);
        handleCategoryContentGetting();
        return swipeRefreshLayout;
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection swipyRefreshLayoutDirection) {
        if(swipyRefreshLayoutDirection == SwipyRefreshLayoutDirection.TOP) {
            if(currentPage > 1) {
                currentPage = currentPage - 1;
                RubricContentRequest request = new RubricContentRequest(currentRubricId, currentPage,
                        (MainActivity)getActivity());
                VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(request);
            } else {
                swipeRefreshLayout.setRefreshing(false);
            }
        } else {
            if(currentPage < 10) {
                currentPage = currentPage + 1;
                RubricContentRequest request = new RubricContentRequest(currentRubricId, currentPage,
                        (MainActivity)getActivity());
                VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(request);
            } else {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
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
        articlesAdapter.setRedItemsUsing(true);
        articles.addAll(wrapper.getMain().getArticles());
        articlesAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void onEvent(RubricContentWrapper rubricContentWrapper) {
        if(!articles.isEmpty() && currentPage == 1) {
            articles.clear();
        }
        currentRubricId = rubricContentWrapper.getRubricId();
        articlesAdapter.setRedItemsUsing(false);
        articles.addAll(rubricContentWrapper.getArticles());
        articlesAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void handleCategoryContentGetting() {
        Bundle data = getArguments();
        CategorySelectedEvent selectedEvent = (CategorySelectedEvent)data.get(
                CategorySelectedEvent.CATEGORY_DATA);

        Request request;
        if(getString(R.string.category_name_all).equals(selectedEvent.getCategoryName())) {
            request = new MainPageContentRequest((MainActivity)getActivity());
            getActivity().setTitle(getString(R.string.category_name_all));
        } else {
            request = new RubricContentRequest(String.valueOf(selectedEvent.getCategoryId()), 1,
                    (MainActivity)getActivity());
            getActivity().setTitle(selectedEvent.getCategoryName());
        }
        VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(request);
    }
}

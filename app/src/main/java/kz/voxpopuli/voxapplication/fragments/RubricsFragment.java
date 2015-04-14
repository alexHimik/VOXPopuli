package kz.voxpopuli.voxapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.adapter.ArticlesAdapter;
import kz.voxpopuli.voxapplication.events.RubricSelectedEvent;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.RubricContentRequest;
import kz.voxpopuli.voxapplication.network.wrappers.mpage.Article;
import kz.voxpopuli.voxapplication.network.wrappers.rubrics.RubricContentWrapper;

/**
 * Created by user on 26.03.15.
 */
public class RubricsFragment extends FaddingTitleBaseFragment implements
        SwipyRefreshLayout.OnRefreshListener{

    public static final String TAG = "RubricsFragment";
    public static final int FRAGMENT_ID = 1;

    public static final String PARALAX_IMAGE_HEADER_KEY = "parallax_header";
    public static final String LIST_DATA_KEY = "list_data";

    private ArticlesAdapter articlesAdapter;
    private List<Article> articles = new LinkedList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = super.onCreateView(inflater, container, savedInstanceState);
        articlesAdapter = new ArticlesAdapter(this, articles);
        fragmentList.setAdapter(articlesAdapter);
        swipyRefreshLayout.setOnRefreshListener(this);
        swipyRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.vox_red),
                getActivity().getResources().getColor(R.color.vox_red),
                getActivity().getResources().getColor(R.color.vox_red));
        getRubricData(mArguments);
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
    public void onRefresh(SwipyRefreshLayoutDirection swipyRefreshLayoutDirection) {

    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }

    public void onEvent(RubricContentWrapper rubricContentWrapper) {
        swipyRefreshLayout.setRefreshing(false);
        articles.addAll(rubricContentWrapper.getArticles());
        articlesAdapter.notifyDataSetChanged();
        Glide.with(this).load(rubricContentWrapper.getRubricImage()).
                centerCrop().into(faddingHeader);
    }

    private void getRubricData(Bundle data) {
        RubricSelectedEvent event = (RubricSelectedEvent)data.get(RubricSelectedEvent.RUBRIC_DATA);
        VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(
                new RubricContentRequest(String.valueOf(event.getRubricId()),
                        (MainActivity)getActivity()));
    }
}

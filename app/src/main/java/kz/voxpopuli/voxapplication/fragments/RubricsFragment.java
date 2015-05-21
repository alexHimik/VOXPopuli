package kz.voxpopuli.voxapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
        SwipyRefreshLayout.OnRefreshListener, ListView.OnItemClickListener {

    public static final String TAG = "RubricsFragment";
    public static final int FRAGMENT_ID = 1;

    private ArticlesAdapter articlesAdapter;
    private List<Article> articles = new LinkedList<>();

    private View fragmentLayout;

    private int currentPage = 1;
    private String currentRubricId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(fragmentLayout == null) {
            fragmentLayout = super.onCreateView(inflater, container, savedInstanceState);
        }

        View customBar = getActionBarCustomView(inflater);
        mFadingHelper.resetActionBarAlfa();
        ((MainActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(
                mFadingHelper.getmActionBarBackgroundDrawable());

        articlesAdapter = new ArticlesAdapter(this, articles, false);
        fragmentList.setOnItemClickListener(this);
        fragmentList.setAdapter(articlesAdapter);
        swipyRefreshLayout.setOnRefreshListener(this);
        swipyRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.vox_red),
                getActivity().getResources().getColor(R.color.vox_red),
                getActivity().getResources().getColor(R.color.vox_red));
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        getRubricData(mArguments);
        return fragmentLayout;
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
        if(swipyRefreshLayoutDirection == SwipyRefreshLayoutDirection.TOP) {
            if(currentPage > 1) {
                currentPage = currentPage - 1;
                RubricContentRequest request = new RubricContentRequest(getActivity(), currentRubricId, currentPage,
                        (MainActivity)getActivity());
                VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(request);
            } else {
                swipyRefreshLayout.setRefreshing(false);
            }
        } else {
            if(currentPage < 10) {
                currentPage = currentPage + 1;
                RubricContentRequest request = new RubricContentRequest(getActivity(), currentRubricId, currentPage,
                        (MainActivity)getActivity());
                VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(request);
            } else {
                swipyRefreshLayout.setRefreshing(false);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //hack!!! for some reason upper item returns position 1
        if(position > 0) {
            Article artice = articlesAdapter.getItem(position - 1);
            Bundle bundle = new Bundle();
            bundle.putString(NewsPageFragment.ARTICLE_KEY, artice.getId());
            ((MainActivity)getActivity()).handleFragmentSwitching(NewsPageFragment.FRAGMENT_ID,
                    bundle);
        }
    }

    @Override
    public View getActionBarCustomView(LayoutInflater inflater) {
        View castomBar = super.getActionBarCustomView(inflater);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setCustomView(castomBar);
        return castomBar;
    }

    @Override
    public void initActionBarItems() {
        leftItemTouch.setOnClickListener(barClickListener);
        rightBarItem.setOnClickListener(barClickListener);
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
        Glide.with(this).load(getString(R.string.vox_url) + rubricContentWrapper.getRubricImage()).
                centerCrop().into(faddingHeader);
        if(currentPage == 1 && !articles.isEmpty()) {
            articles.clear();
        }
        currentRubricId = rubricContentWrapper.getRubricId();
        articles.addAll(rubricContentWrapper.getArticles());
        swipyRefreshLayout.setRefreshing(false);
        articlesAdapter.notifyDataSetChanged();
    }

    private void getRubricData(Bundle data) {
        RubricSelectedEvent event = (RubricSelectedEvent)data.get(RubricSelectedEvent.RUBRIC_DATA);
        VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(
                new RubricContentRequest(getActivity(), String.valueOf(event.getRubricId()), 1,
                        (MainActivity)getActivity()));
        ((TextView)centerBatItem).setText(event.getRubricName());
    }

    private View.OnClickListener barClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).onClick(v);
        }
    };
}

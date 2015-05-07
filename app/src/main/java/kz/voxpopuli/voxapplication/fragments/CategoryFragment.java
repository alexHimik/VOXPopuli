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

import com.android.volley.Request;

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
import kz.voxpopuli.voxapplication.network.request.TagInfoRequest;
import kz.voxpopuli.voxapplication.network.wrappers.mpage.Article;
import kz.voxpopuli.voxapplication.network.wrappers.mpage.MainPageDataWrapper;
import kz.voxpopuli.voxapplication.network.wrappers.rubrics.RubricContentWrapper;
import kz.voxpopuli.voxapplication.network.wrappers.tag.TagDataWrapper;
import kz.voxpopuli.voxapplication.tools.SocialNetworkUtils;

/**
 * Created by user on 09.04.15.
 */
public class CategoryFragment extends BaseFragment implements ListView.OnItemClickListener {

    public static final String TAG = "CategoryFragment";
    public static final int FRAGMENT_ID = 2;

    public static SocialNetworkUtils socialNetworkUtils;

    private ListView lv;
    private List<Article> articles = new LinkedList<>();
    private ArticlesAdapter articlesAdapter;

    private int currentPage = 1;
    private boolean tagShowing;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        lv = (ListView)inflater.inflate(R.layout.articles, container, false);
        articlesAdapter = new ArticlesAdapter(this, articles, true);
        lv.setAdapter(articlesAdapter);
        lv.setOnItemClickListener(this);
        handleCategoryContentGetting();
        socialNetworkUtils = SocialNetworkUtils.getInstance(this);
        return lv;
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
        Article artice = articlesAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString(NewsPageFragment.ARTICLE_KEY, artice.getId());
        ((MainActivity)getActivity()).handleFragmentSwitching(NewsPageFragment.FRAGMENT_ID,
                bundle);
    }

    @Override
    public View getActionBarCustomView(LayoutInflater inflater) {
        View customBar = super.getActionBarCustomView(inflater);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        return customBar;
    }

    @Override
    public void initActionBarItems() {
        rightBarItem.setOnClickListener(barListener);
        leftItemTouch.setOnClickListener(barListener);
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
        tagShowing = false;
        articlesAdapter.setRedItemsUsing(true);
        articles.addAll(wrapper.getMain().getArticles());
        articlesAdapter.notifyDataSetChanged();
    }

    public void onEvent(RubricContentWrapper wrapper) {
        if (!articles.isEmpty()) {
            articles.clear();
        }
        tagShowing = false;
        articlesAdapter.setRedItemsUsing(true);
        articles.addAll(wrapper.getArticles());
        articlesAdapter.notifyDataSetChanged();
    }

    public void onEvent(TagDataWrapper tagDataWrapper) {
        if(!articles.isEmpty() && currentPage == 1) {
            articles.clear();
        }
        tagShowing = true;
        ((TextView)centerBatItem).setText(tagDataWrapper.getTag().getTitle());
        articlesAdapter.setRedItemsUsing(false);
        articles.addAll(tagDataWrapper.getArticles());
        articlesAdapter.notifyDataSetChanged();
    }

    private void handleCategoryContentGetting() {
        Bundle data = getArguments();
        CategorySelectedEvent selectedEvent = (CategorySelectedEvent)data.get(
                CategorySelectedEvent.CATEGORY_DATA);

        Request request = null;
        if(getString(R.string.category_name_all).equals(selectedEvent.getCategoryName()) && !selectedEvent.isTag()) {
            request = new MainPageContentRequest(getActivity(), (MainActivity)getActivity());
            ((TextView)centerBatItem).setText(getString(R.string.category_name_all));
        } else if(!selectedEvent.isTag()) {
            request = new RubricContentRequest(getActivity(), String.valueOf(selectedEvent.getCategoryId()), 1,
                    (MainActivity)getActivity());
            ((TextView)centerBatItem).setText(selectedEvent.getCategoryName());
        } else if(selectedEvent.isTag()) {
            request = new TagInfoRequest(getActivity(), String.valueOf(selectedEvent.getCategoryId()),
                    (MainActivity)getActivity());
            leftbarItem.setBackgroundResource(R.drawable.vox_ic_white_arrow);
        }
        VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(request);
    }

    private View.OnClickListener barListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(tagShowing) {
                ((MainActivity)getActivity()).onBackPressed();
            } else {
                ((MainActivity)getActivity()).onClick(v);
            }
        }
    };
}

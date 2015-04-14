package kz.voxpopuli.voxapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.adapter.ArticlesAdapter;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.MainPageContentRequest;
import kz.voxpopuli.voxapplication.network.wrappers.mpage.Article;
import kz.voxpopuli.voxapplication.network.wrappers.mpage.MainPageDataWrapper;

public class MainStreamPageFragment extends BaseFragment implements Response.ErrorListener {

    public static final String TAG = "MainStreamPageFragment";
    public static final int FRAGMENT_ID = 0;
    private ListView lv;
    public List<Article> articles = new LinkedList<>();
    private ArticlesAdapter articlesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(
                new MainPageContentRequest(this));

        View parent = inflater.inflate(R.layout.articles, container,false);
        lv = (ListView)parent.findViewById(R.id.lv_articles);
        articlesAdapter = new ArticlesAdapter(this,articles);
        lv.setAdapter(articlesAdapter);
        lv.setOnItemClickListener(listClick);

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

    private ListView.OnItemClickListener listClick = new GridView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
                                long id) {
Log.d("Asdfg","Title="+articles.get(position).getTitle());
            // TODO Auto-generated method stub
            // выводим номер позиции
//            mSelectText.setText(String.valueOf(position));
        }
    };

    public void onEvent(MainPageDataWrapper wrapper) {
        articles.addAll(wrapper.getMain().getArticles());
        articlesAdapter.notifyDataSetChanged();
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}

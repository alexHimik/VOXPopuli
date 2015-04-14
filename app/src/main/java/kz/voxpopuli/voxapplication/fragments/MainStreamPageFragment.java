package kz.voxpopuli.voxapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
//        fillTestListWithData();
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

    private void fillTestListWithData() {
        articles.add(new Article("", "Путь к мечте", "", "http://www.voxpopuli.kz/img/article/118/35_tn.jpg", "",
                "Сегодня многие казахстанцы живут мечтой открыть собственный бизнес. Основной причиной, почему задумки остаются всего лишь мечтой.",
                "2 апреля 13:53", "915", "10"));
        articles.add(new Article("", "Вдохновляя на большее: Жаксылык Ушкемпиров", "", "http://www.voxpopuli.kz/img/article/117/3_tn.jpg", "",
                "Жаксылык Ушкемпиров стал первым казахом, завоевавшим золотую медаль на московской Олимпиаде в 1980 году.",
                "7 апреля 13:53", "705", "30"));
        articles.add(new Article("", "Вегетарианство и сыроедение: дань моде или путь к здоровью",
                "http://www.voxpopuli.kz/img/article/118/85_tn.jpg", "http://www.voxpopuli.kz/img/article/118/85_tn.jpg",
                "http://www.voxpopuli.kz/img/article/118/85_tn.jpg", "Здоровое питание – вопрос, который волнует современное " +
                "общество во всём мире. На эту тему написана уже ни одна книга и проведён ни один тренинг",
                "10 апреля 13:53", "505", "20"));
        articles.add(new Article("", "История Центральной аптеки Алматы", "http://www.voxpopuli.kz/img/article/118/18_mid.jpg",
                "http://www.voxpopuli.kz/img/article/118/18_mid.jpg", "http://www.voxpopuli.kz/img/article/118/18_main.jpg",
                "Одна из старейших аптек города – алматинская аптека №2 – существует с 20-х годов прошлого века. " +
                        "По просьбе наших читателей Vox Populi рассказывает историю этой городской легенды",
                "10 апреля 13:53", "505", "20"));
        articles.add(new Article("", "Увядший сад матери", "http://www.voxpopuli.kz/img/article/117/77_mid.jpg",
                "http://www.voxpopuli.kz/img/article/117/77_mid.jpg", "http://www.voxpopuli.kz/img/article/117/77_main.jpg",
                "В Алматы, рядом с центром города, живет женщина с нелегкой судьбой. Ашир-апай – мать наркомана. " +
                        "Все то время, пока он страдал от наркотической зависимости, она задавала себе вопросы.",
                "10 апреля 13:53", "505" ,"20"));
        articles.add(new Article("", "Бархан-фитнес, бахча-йога и аквабатика – оздоровление по-казахстански",
                "http://www.voxpopuli.kz/img/article/118/72_tn.jpg", "http://www.voxpopuli.kz/img/article/118/72_mid.jpg",
                "http://www.voxpopuli.kz/img/article/118/72_main.jpg",
                "Быть здоровым в Казахстане непросто. Наша страна испытала многие виды экологических бедствий, среди которых ядерный полигон и высохшее море.",
                "2 марта 13:53", "85", "7"));
    }
}

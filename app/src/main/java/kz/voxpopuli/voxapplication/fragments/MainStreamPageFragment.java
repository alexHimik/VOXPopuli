package kz.voxpopuli.voxapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.adapter.ArticlesAdapter;
import kz.voxpopuli.voxapplication.model.ModelArticle;

public class MainStreamPageFragment extends BaseFragment {

    public static final String TAG = "MainStreamPageFragment";
    public static final int FRAGMENT_ID = 0;
    private ListView lv;
    public ModelArticle[] articles= {new ModelArticle("http://www.voxpopuli.kz/img/article/118/35_tn.jpg",
            "http://www.voxpopuli.kz/img/article/118/35_mid.jpg", "http://www.voxpopuli.kz/img/article/118/35_main.jpg",
            "Путь к мечте",
            "Сегодня многие казахстанцы живут мечтой открыть собственный бизнес. Основной причиной, почему задумки остаются всего лишь мечтой.",
            "2 апреля 13:53",10,915),
            new ModelArticle("http://www.voxpopuli.kz/img/article/117/3_tn.jpg",
                    "http://www.voxpopuli.kz/img/article/117/3_mid.jpg", "http://www.voxpopuli.kz/img/article/117/3_main.jpg",
                    "Вдохновляя на большее: Жаксылык Ушкемпиров",
                    "Жаксылык Ушкемпиров стал первым казахом, завоевавшим золотую медаль на московской Олимпиаде в 1980 году.",
                    "7 апреля 13:53",30,705),
            new ModelArticle("http://www.voxpopuli.kz/img/article/118/85_tn.jpg",
                    "http://www.voxpopuli.kz/img/article/118/85_mid.jpg", "http://www.voxpopuli.kz/img/article/118/85_main.jpg",
                    "Вегетарианство и сыроедение: дань моде или путь к здоровью",
                    "Здоровое питание – вопрос, который волнует современное общество во всём мире. На эту тему написана уже ни одна книга и проведён ни один тренинг",
                    "10 апреля 13:53",20,505),
            new ModelArticle("http://www.voxpopuli.kz/img/article/118/18_tn.jpg",
                    "http://www.voxpopuli.kz/img/article/118/18_mid.jpg", "http://www.voxpopuli.kz/img/article/118/18_main.jpg",
                    "История Центральной аптеки Алматы",
                    "Одна из старейших аптек города – алматинская аптека №2 – существует с 20-х годов прошлого века. По просьбе наших читателей Vox Populi рассказывает историю этой городской легенды",
                    "10 апреля 13:53",20,505),
            new ModelArticle("http://www.voxpopuli.kz/img/article/117/77_tn.jpg",
                    "http://www.voxpopuli.kz/img/article/117/77_mid.jpg", "http://www.voxpopuli.kz/img/article/117/77_main.jpg",
                    "Увядший сад матери",
                    "В Алматы, рядом с центром города, живет женщина с нелегкой судьбой. Ашир-апай – мать наркомана. Все то время, пока он страдал от наркотической зависимости, она задавала себе вопросы.",
                    "10 апреля 13:53",20,505),
            new ModelArticle("http://www.voxpopuli.kz/img/article/118/72_tn.jpg",
                    "http://www.voxpopuli.kz/img/article/118/72_mid.jpg", "http://www.voxpopuli.kz/img/article/118/72_main.jpg",
                    "Бархан-фитнес, бахча-йога и аквабатика – оздоровление по-казахстански",
                    "Быть здоровым в Казахстане непросто. Наша страна испытала многие виды экологических бедствий, среди которых ядерный полигон и высохшее море.",
                    "2 марта 13:53",7,85)
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View parent = inflater.inflate(R.layout.articles, container,false);
        lv = (ListView)parent.findViewById(R.id.lv_articles);
        lv.setAdapter(new ArticlesAdapter(this,articles));
        lv.setOnItemClickListener(listClick);

        return parent;
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

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }
}

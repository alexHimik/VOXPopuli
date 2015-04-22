package kz.voxpopuli.voxapplication.fragments;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;


import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kz.voxpopuli.voxapplication.R;
//import kz.voxpopuli.voxapplication.adapter.ArticlesAdapter;
import kz.voxpopuli.voxapplication.adapter.PageAdapter;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.PageNewsRequest;
import kz.voxpopuli.voxapplication.network.wrappers.mpage.Article;
import kz.voxpopuli.voxapplication.network.wrappers.pnews.Author;
import kz.voxpopuli.voxapplication.network.wrappers.pnews.Content;
import kz.voxpopuli.voxapplication.network.wrappers.pnews.PageNewsWrapper;
import kz.voxpopuli.voxapplication.network.wrappers.pnews.Pnews;

public class NewsPageFragment extends BaseFragment {
    public static final String TAG = "NewsPageFragment";
    public static final int FRAGMENT_ID = 3;

    public Context context;
    private LinearLayout ll;
    private Pnews pn = new Pnews();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        View parent = inflater.inflate(R.layout.news_page, container,false);
        ll = (LinearLayout) parent.findViewById(R.id.lineLayout_1);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int maxWidth = display.getWidth();
        formPnews();
        FormNewsPage fNP = new FormNewsPage(context, pn, ll, parent, maxWidth);
        fNP.setNewsPage();

        return parent;
    }

    public void formPnews(){
        Article article = new Article("281", "Burger King Казахстан: итоги 2014 года и планы на будущее", "http://www.voxpopuli.kz/img/article/117/60_tn.jpg",
                "http://www.voxpopuli.kz/img/article/117/60_mid.jpg", "http://www.voxpopuli.kz/img/article/117/60_mid.jpg",
                "17 февраля 2015 г. состоялось очередное заседание медиа-клуба, посвященное результатам деятельности Бургер Кинг® в Казахстане за 2014 г., а также дальнейшим планам Компании по завоеванию рынка в условиях ужесточения конкуренции.",
                "19 февраля, 2015", "20", "10");
        pn.setArticle(article);
        List<Content> cont = new ArrayList<Content>();
        Content c = new Content();
        c.setType("txt");
        c.setData("В 2014 г. чистый оборот компании составил более 10 млн. долларов. В течение 2014 г. Компанией было открыто 10 новых ресторанов и фуд-кортов, включая первый ресторан «Автоэкспресс», 2 фуд-корта и 2 ресторана в Алматы. В Астане свои двери открыли 1 фуд-корт и 2 ресторана, на западе Казахстана, в г. Актобе свою операционную деятельность начал 1 фуд-корт, а в г. Актау - ресторан.");
        cont.add(c);
        c = new Content();
        c.setType("title");
        c.setData("Слово руководству");
        cont.add(c);
        c = new Content();
        c.setType("txt");
        c.setData("«В 2015 г. мы планируем открытие еще 10 ресторанов и фуд-кортов, 5 из которых в данный момент находятся в стадии строительства, в том числе 4 ресторана «Автоэкспресс», что позволит создать  более 140 новых рабочих мест», – поделился г-н Нашед.");
        cont.add(c);
        c = new Content();
        c.setType("Photo");
        c.setData("http://www.voxpopuli.kz/img/inner/117/60/btt_2119_01.jpg");
        c.setTitle("Самир Нашед говорит в микрофон");
        c.setAuthor("Фото: Тимур Батыршин");
        cont.add(c);
        c = new Content();
        c.setType("txt");
        c.setData("«На рынке фаст-фуда McDonald’s исторически был нашим главным конкурентом в сегменте бургеров. Начиная с 1950-ых годов до нынешнего времени, McDonald’s и Burger King удалось полностью стандартизировать операции. Сегодня обе компании по праву считаются одними из крупнейших сетей бургеров в мире.  Приход такого крупного бренда, как McDonald’s, безусловно, увеличит конкуренцию на рынке, предлагая более широкий выбор и разнообразие продуктов для клиентов.");
        cont.add(c);
        c = new Content();
        c.setType("Gallery");
        c.setData("http://www.voxpopuli.kz/img/article/118/18_mid.jpg ; http://www.voxpopuli.kz/img/article/118/35_mid.jpg ; " +
                "http://www.voxpopuli.kz/img/article/118/78_mid.jpg ; http://www.voxpopuli.kz/img/article/119/0_mid.jpg ; " +
                "http://www.voxpopuli.kz/img/article/117/3_mid.jpg");
        c.setTitle("Результаты Бургер Кинг в Казахстане в 2014г.");
        c.setAuthor("");
        cont.add(c);

        c = new Content();
        c.setType("comment");
        c.setData("В Казахстане Бургер Кинг® – первый международный бренд, который уже прошел этап становления, поэтому сейчас мы можем себе позволить сфокусироваться на совершенствовании сервиса для гостей в наших ресторанах, чтобы клиенты возвращались к нам снова и снова.");
        c.setTitle("Ануар Утемуратов");
        c.setAuthor("Представитель Бургер Кинг");
        cont.add(c);
        c = new Content();
        c.setType("txt");
        c.setData("Бургер Кинг® тщательно контролирует качество своей продукции и очень тщательно относится к выбору поставщиков. Все поставщики имеют Сертификат Безопасности (Food safety Certificate), который утвержден GFSI (Global Food Safety Initiative).");
        cont.add(c);

        pn.setContent(cont);

        Author a = new Author();
        a.setName("Акмарал Жайлыбаева");
        a.setPost("Журналист");
        a.setAvatar("http://www.voxpopuli.kz/img/user/123/75_main.jpg");
        pn.setAuthor(a);
        pn.setTags("Казахстан,Астана,Бургер Кинг,Байконур,Киев");

        List<Article> similar = new ArrayList<Article>();

        Article ar = new Article("281", "Белый цвет надежды", "http://www.voxpopuli.kz/img/article/117/99_tn.jpg",
                "http://www.voxpopuli.kz/img/article/117/99_tn.jpg", "http://www.voxpopuli.kz/img/article/117/99_tn.jpg",
                "",
                "26 февраля 14:00", "20", "10");
        similar.add(ar);
        ar = new Article("281", "Pictor – бизнес на живописи", "http://www.voxpopuli.kz/img/article/117/87_tn.jpg",
                "http://www.voxpopuli.kz/img/article/117/87_tn.jpg", "http://www.voxpopuli.kz/img/article/117/87_tn.jpg",
                "",
                "24 февраля 14:00", "50", "7");
        similar.add(ar);
        ar = new Article("281", "BeSmart – бизнес на скидках", "http://www.voxpopuli.kz/img/article/117/74_tn.jpg",
                "http://www.voxpopuli.kz/img/article/117/74_tn.jpg", "http://www.voxpopuli.kz/img/article/117/74_tn.jpg",
                "",
                "20 марта 15:00", "120", "17");
        similar.add(ar);
        pn.setSimilar(similar);
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

    public void onEvent(PageNewsWrapper wrapper) {
        pn = wrapper.getPnews();
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }

}
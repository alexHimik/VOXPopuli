package kz.voxpopuli.voxapplication.fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.adapter.PageAdapter;
import kz.voxpopuli.voxapplication.events.CategorySelectedEvent;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.network.request.ArticleContentRequest;
import kz.voxpopuli.voxapplication.network.wrappers.article.Article;
import kz.voxpopuli.voxapplication.network.wrappers.article.ArticleDataWrapper;
import kz.voxpopuli.voxapplication.network.wrappers.article.Author;
import kz.voxpopuli.voxapplication.network.wrappers.article.Content;
import kz.voxpopuli.voxapplication.network.wrappers.article.Similar;
import kz.voxpopuli.voxapplication.network.wrappers.article.Tag;

public class NewsPageFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = "NewsPageFragment";
    public static final int FRAGMENT_ID = 3;
    public static final String ARTICLE_KEY = "article";
    private LinearLayout ll;
    private View parent;
    int maxWidth;
    Activity actv;

    public ViewGroup.LayoutParams lp_W_W, lp_M_W, lp_M_M;
    private MediaController mediaControls;
    Article pn;
    String mWebViewText = "";
    WebView mWebView;
    Map<String, WebView> videoHTML = new HashMap();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View customBar = super.getActionBarCustomView(inflater);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(customBar);

        parent = inflater.inflate(R.layout.news_scroll, container, false);
        actv = getActivity();
        ll = (LinearLayout) parent.findViewById(R.id.lineLayout_1);
        Bundle data = getArguments();
        String articleId = data.getString(ARTICLE_KEY);
        if(articleId != null) {
            VolleyNetworkProvider.getInstance(getActivity()).addToRequestQueue(new ArticleContentRequest(articleId,
                    (MainActivity)getActivity()));
        }
        return parent;
    }

    @Override
    public void initActionBarItems() {
        rightBarItem.setVisibility(View.INVISIBLE);
        leftbarItem.setOnClickListener(barClickListener);
        leftbarItem.setBackgroundResource(R.drawable.vox_ic_white_arrow);
    }

    private View.OnClickListener barClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).onBackPressed();
        }
    };


    public void onEvent(ArticleDataWrapper articleDataWrapper) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        maxWidth = display.getWidth() - ((int) getResources().getDimension(R.dimen.news_padding_txt))*2;
        ((RobotoTextView)centerBatItem).setText(articleDataWrapper.getArticle().getTitle());
        pn = articleDataWrapper.getArticle();
        setNewsPage();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        WebView v;
        String st;
        EventBus.getDefault().unregister(this);

        for (Map.Entry entry: videoHTML.entrySet()) {
            st = (String)entry.getKey();
            v = (WebView)entry.getValue();
            v.loadDataWithBaseURL(null, st, "text/html", "en_US","");
        }
        super.onStop();
    }


    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }

    public void setNewsPage(){
        lp_W_W = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp_M_W = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp_M_M = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        setArticle();
        setContent();
        setAuthors(pn.getAuthors());
        setTags(ll,pn.getTags());
        setSimilar(pn.getSimilar());
        setBottom(pn.getCommentsAmount());
    }

    @Override
    public void onClick(View v) {
        String id = v.getTag().toString();
        int i = id.indexOf(";");
        String btn = id.substring(0,i);
        id = id.substring(i+1);
        if (btn.equals("tag")) {
            Bundle bundle = new Bundle();
            CategorySelectedEvent forTagEvent = new CategorySelectedEvent(Integer.parseInt(id), "");
            forTagEvent.setTag(true);
            bundle.putSerializable(CategorySelectedEvent.CATEGORY_DATA, forTagEvent);
            ((MainActivity)getActivity()).handleFragmentSwitching(CategoryFragment.FRAGMENT_ID,
                    bundle);
            return;
        }
        if (btn.equals("art")) {
            Bundle bundle = new Bundle();
            bundle.putString(NewsPageFragment.ARTICLE_KEY, id);
            ((MainActivity)getActivity()).handleFragmentSwitching(NewsPageFragment.FRAGMENT_ID,
                    bundle);
            return;
        }
        if (btn.equals("com")) {
            Bundle bundle = new Bundle();
            bundle.putString(NewsPageFragment.ARTICLE_KEY, id);
            ((MainActivity)getActivity()).handleFragmentSwitching(CommentsListFragment.FRAGMENT_ID,
                    bundle);
            return;
        }
        if (btn.equals("send")) {
            SocialDialogFragment dlg = new SocialDialogFragment();
            dlg.setLink(pn.getLink());
            dlg.show(((MainActivity)getActivity()).getSupportFragmentManager(), "dlg");
            return;
        }

    }

    public void setArticle(){
        ImageView iv = (ImageView)parent.findViewById(R.id.imageView);
        Glide.with(this).load(pn.getImageMid()).into(iv);

        LinearLayout la = new LinearLayout(getActivity());
        la.setLayoutParams(lp_M_W);
        la.setOrientation(LinearLayout.VERTICAL);
        int pad = (int) getActivity().getResources().getDimension(R.dimen.news_padding_txt);
        la.setPadding(pad, pad, pad, 0);

        la.addView(newRobTV(lp_W_W, pn.getTitle(), R.style.news_title, 0));

        LinearLayout othe1 = new LinearLayout(getActivity());
        othe1.setLayoutParams(lp_M_W);
        othe1.setOrientation(LinearLayout.HORIZONTAL);
        int pad1 = (int) getActivity().getResources().getDimension(R.dimen.news_size_line);
        othe1.setPadding(0, 0, 0, pad1);
        othe1.setBackgroundColor(getActivity().getResources().getColor(R.color.news_color_line));
        la.addView(othe1);
        LinearLayout othe2 = new LinearLayout(getActivity());
        othe2.setLayoutParams(lp_M_M);
        othe2.setOrientation(LinearLayout.HORIZONTAL);
        pad1 = (int) getActivity().getResources().getDimension(R.dimen.news_padding_betw_text);
        othe2.setPadding(0, pad1, 0, pad1);
        othe2.setGravity(Gravity.CENTER_VERTICAL);
        othe2.setBackgroundColor(getActivity().getResources().getColor(R.color.news_color_white));
        othe1.addView(othe2);
        othe2.addView(newRobTV(lp_W_W, pn.getPostDate(), R.style.com_datetime, 0));
        ImageView iv1 = new ImageView(getActivity());
        iv1.setLayoutParams(lp_W_W);
        iv1.setPadding(12, 0, 12, 0);
        iv1.setImageResource(R.drawable.vox_ic_sm_grey_eye);
        othe2.addView(iv1);
        othe2.addView(newRobTV(lp_W_W, pn.getViwed(), R.style.com_datetime, 0));
        iv1 = new ImageView(getActivity());
        iv1.setLayoutParams(lp_W_W);
        iv1.setPadding(12, 0, 12, 0);
        iv1.setImageResource(R.drawable.vox_ic_sm_grey_comment);
        othe2.addView(iv1);
        othe2.addView(newRobTV(lp_W_W, pn.getCommentsAmount(), R.style.com_datetime, 0));

        la.addView(newRobTV(lp_W_W, pn.getDescription(), R.style.news_descr, 0));

        ll.addView(la);
    }

    public RobotoTextView newRobTV(ViewGroup.LayoutParams lp, String txt, int style, int bg){
        RobotoTextView tv = new RobotoTextView(getActivity());
        tv.setLayoutParams(lp);
        tv.setText(txt);
        if (style >0) tv.setTextAppearance(getActivity(), style);
        if (bg>0) tv.setBackgroundResource(bg);
        return tv;
    }

    public void setTags(LinearLayout linerL, List<Tag> tag){
        int width, widthAll;
        ViewGroup.LayoutParams lpWMW = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lpM = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                (int)getActivity().getResources().getDimension(R.dimen.news_height_tag));
        lpM.setMargins(0,0,5,0);

        LinearLayout lt1 = new LinearLayout(getActivity());
        lt1.setLayoutParams(lpWMW);
        lt1.setBackgroundColor(getActivity().getResources().getColor(R.color.news_color_line));
        int padLine = (int) getActivity().getResources().getDimension(R.dimen.news_size_line);
        lt1.setPadding(0, 0, 0, padLine);
        LinearLayout lt = new LinearLayout(getActivity());
        lt.setLayoutParams(lpWMW);
        lt.setOrientation(LinearLayout.VERTICAL);
        lt.setBackgroundColor(getActivity().getResources().getColor(R.color.news_color_white));
        int padText = (int) getActivity().getResources().getDimension(R.dimen.news_padding_txt);
        lt.setPadding(padText, padText, padText, padText);
        lt1.addView(lt);

        LinearLayout line = newLayout(getActivity(), lp_W_W);
        lt.addView(line);
        widthAll = 0;
        for (int i=0;i<tag.size();i++){
            TextView tv = new TextView(getActivity());
            tv.setLayoutParams(lpM);
            tv.setText(tag.get(i).getTitle());
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            tv.setBackgroundResource(R.drawable.tag_shape);
            tv.setTextAppearance(getActivity(), R.style.news_tag);
            tv.setTag("tag;"+tag.get(i).getId());
            tv.setClickable(true);
            tv.setOnClickListener(this);
            tv.measure(0, 0);
            width = tv.getMeasuredWidth()+5;
            widthAll += width;
            if (widthAll<maxWidth) line.addView(tv);
            else {
                line = newLayout(getActivity(),lp_W_W);
                lt.addView(line);
                line.addView(tv);
                widthAll = width;
            }
        }
        linerL.addView(lt1);
    }

    public void setBottom(String com){
        View v = parent.findViewById(R.id.comment_bottom_bar);
        RobotoTextView rv = (RobotoTextView) v.findViewById(R.id.rt);
        LinearLayout iv_com = (LinearLayout) v.findViewById(R.id.imv_com_b);
        iv_com.setTag("com;"+pn.getId());
        iv_com.setClickable(true);
        iv_com.setOnClickListener(this);
        LinearLayout iv_send = (LinearLayout) v.findViewById(R.id.send);
        iv_send.setTag("send;"+pn.getId());
        iv_send.setClickable(true);
        iv_send.setOnClickListener(this);
        rv.setText(com + " комментариев");
    }

    public LinearLayout newLayout(Context context, ViewGroup.LayoutParams lp){
        LinearLayout line1 = new LinearLayout(context);
        line1.setLayoutParams(lp);
        int padT = (int) context.getResources().getDimension(R.dimen.news_padding_img);
        line1.setPadding(0,padT,0,0);
        line1.setOrientation(LinearLayout.HORIZONTAL);
        return line1;
    }


    public void setContent(){
        List<Content> content = pn.getContent();
        int iMax = content.size();
        View v;
        for (int i=0; i<iMax;i++){
            v = setV(content.get(i));
            if (v != null) ll.addView(v);
        }
    }

    public View setV(Content c){
        String type = c.getType();
        if (type.equals("title")) return setTitle(c);
        if (type.equals("txt")) return setTxt(c);
        if (type.equals("comment")) return setComment(c);
        if (type.equals("photo")) return setPhoto(c);
        if (type.equals("video")) return setVideo(c);
        if (type.equals("gallery")) return setGallery(c);
        return null;
    }

    public View setTitle(Content c){
        TextView tv = new TextView(getActivity());
        tv.setText(c.getData().get(0));
        tv.setLayoutParams(lp_W_W);
        tv.setTextColor(getActivity().getResources().getColor(R.color.news_color_title));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getActivity().getResources().getDimension(R.dimen.news_fsize_title));
        LinearLayout lt = new LinearLayout(getActivity());
        lt.setLayoutParams(lp_W_W);
        int padText = (int) getActivity().getResources().getDimension(R.dimen.news_padding_txt);
        int padDelt = (int) getActivity().getResources().getDimension(R.dimen.news_padding_top_title);
        lt.setPadding(padText, padDelt, padText, 0);
        lt.addView(tv);
        return lt;
    }

    public View setTxt(Content c){
        TextView tv = new TextView(getActivity());
        tv.setText(c.getData().get(0));
        tv.setLayoutParams(lp_W_W);
        tv.setTextColor(getActivity().getResources().getColor(R.color.news_color_text));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getActivity().getResources().getDimension(R.dimen.news_fsize_text));
        LinearLayout lt = new LinearLayout(getActivity());
        lt.setLayoutParams(lp_W_W);
        int padText = (int) getActivity().getResources().getDimension(R.dimen.news_padding_txt);
        lt.setPadding(padText, padText, padText, padText);
        lt.addView(tv);
        return lt;
    }

    public View setPhoto(Content c){
        ImageView iv = new ImageView(getActivity());
        iv.setLayoutParams(lp_W_W);
        Glide.with(getActivity()).load(c.getUrl()).into(iv);
        LinearLayout lt = new LinearLayout(getActivity());
        lt.setOrientation(LinearLayout.VERTICAL);
        int pad = (int) getActivity().getResources().getDimension(R.dimen.news_padding_img);
        lt.setPadding(pad, 0, pad, 0);
        lt.addView(iv);
        if ((c.getTitle() != null && c.getTitle().length() > 0) ||
                (c.getAuthor() != null && c.getAuthor().length() > 0)) {
            LinearLayout author = new LinearLayout(getActivity());
            author.setOrientation(LinearLayout.VERTICAL);
            author.setLayoutParams(lp_W_W);
            int indent = (int) getActivity().getResources().getDimension(R.dimen.news_indent48);
            author.setPadding(indent, 0, 0, 0);
            LinearLayout b1 = new LinearLayout(getActivity());
            author.setOrientation(LinearLayout.VERTICAL);
            b1.setLayoutParams(lp_W_W);
            b1.setBackgroundColor(getActivity().getResources().getColor(R.color.news_color_line));
            int t = (int) getActivity().getResources().getDimension(R.dimen.news_thickness_line);
            b1.setPadding(t,0,0,0);
            LinearLayout b2 = new LinearLayout(getActivity());
            b2.setLayoutParams(lp_W_W);
            b2.setBackgroundColor(getActivity().getResources().getColor(R.color.news_color_white));
            b2.setOrientation(LinearLayout.VERTICAL);
            int in12 = (int) getActivity().getResources().getDimension(R.dimen.news_indent12);
            b2.setPadding(in12,pad,0,pad);

            TextView tv = new TextView(getActivity());
            tv.setText(c.getTitle());
            tv.setLayoutParams(lp_W_W);
            tv.setTextColor(getActivity().getResources().getColor(R.color.news_color_text));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getActivity().getResources().getDimension(R.dimen.news_fsize_photo));
            b2.addView(tv);

            TextView tva = new TextView(getActivity());
            tva.setText(c.getAuthor());
            tva.setLayoutParams(lp_W_W);
            tva.setTextColor(getActivity().getResources().getColor(R.color.news_color_text));
            tva.setTextSize(TypedValue.COMPLEX_UNIT_PX, getActivity().getResources().getDimension(R.dimen.news_fsize_photo));
            b2.addView(tva);
            b1.addView(b2);
            author.addView(b1);
            lt.addView(author);
        }
        return lt;
    }

    public View setGallery(Content c){
//        final String [] gal = c.getData().split(" ; ");
        final List<String> gal = c.getData();
        final TextView numPhoto = new TextView(getActivity());
        numPhoto.setLayoutParams(lp_W_W);
        numPhoto.setTextAppearance(getActivity(), R.style.photo_title);
        numPhoto.setText("1 из "+gal.size());
        TextView txtGal = new TextView(getActivity());
        txtGal.setLayoutParams(lp_W_W);
        txtGal.setTextAppearance(getActivity(), R.style.title_gallery);
        txtGal.setText("Галерея        ");

        LinearLayout titleG = new LinearLayout(getActivity());
        titleG.setOrientation(LinearLayout.HORIZONTAL);
        int pad1 = (int) getActivity().getResources().getDimension(R.dimen.news_pad_title_gallery);
        titleG.setPadding(pad1, 0, pad1, pad1);
        titleG.addView(txtGal);
        titleG.addView(numPhoto);
        PageAdapter pa = new PageAdapter(getActivity(), gal);
        ViewPager vp = new ViewPager(getActivity());
        vp.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                (int) getResources().getDimension(R.dimen.news_gallery_h)));
        vp.setAdapter(pa);
        vp.setCurrentItem(0);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                numPhoto.setText((position + 1) + " из " + gal.size());
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        LinearLayout lt = new LinearLayout(getActivity());
        lt.setOrientation(LinearLayout.VERTICAL);
        int pad = (int) getActivity().getResources().getDimension(R.dimen.news_padding_img);
        lt.setPadding(pad, 0, pad, 0);
        lt.addView(titleG);
        lt.addView(vp);
        if ((c.getTitle().length()>0) ||(c.getAuthor().length()>0)) {
            LinearLayout author = new LinearLayout(getActivity());
            author.setOrientation(LinearLayout.VERTICAL);
            author.setLayoutParams(lp_W_W);
            int indent = (int) getActivity().getResources().getDimension(R.dimen.news_indent48);
            author.setPadding(indent, 0, 0, 0);
            LinearLayout b1 = new LinearLayout(getActivity());
            author.setOrientation(LinearLayout.VERTICAL);
            b1.setLayoutParams(lp_W_W);
            b1.setBackgroundColor(getActivity().getResources().getColor(R.color.news_color_line));
            int t = (int) getActivity().getResources().getDimension(R.dimen.news_thickness_line);
            b1.setPadding(t,0,0,0);
            LinearLayout b2 = new LinearLayout(getActivity());
            b2.setLayoutParams(lp_W_W);
            b2.setBackgroundColor(getActivity().getResources().getColor(R.color.news_color_white));
            b2.setOrientation(LinearLayout.VERTICAL);
            int in12 = (int) getActivity().getResources().getDimension(R.dimen.news_indent12);
            b2.setPadding(in12,pad,0,pad);
            if (c.getTitle().length()>0){
                TextView tv = new TextView(getActivity());
                tv.setText(c.getTitle());
                tv.setLayoutParams(lp_W_W);
                tv.setTextColor(getActivity().getResources().getColor(R.color.news_color_text));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getActivity().getResources().getDimension(R.dimen.news_fsize_photo));
                b2.addView(tv);
            }
            if (c.getAuthor().length()>0) {
                TextView tva = new TextView(getActivity());
                tva.setText(c.getAuthor());
                tva.setLayoutParams(lp_W_W);
                tva.setTextColor(getActivity().getResources().getColor(R.color.news_color_text));
                tva.setTextSize(TypedValue.COMPLEX_UNIT_PX, getActivity().getResources().getDimension(R.dimen.news_fsize_photo));
                b2.addView(tva);
            }
            b1.addView(b2);
            author.addView(b1);
            lt.addView(author);
        }
        return lt;
    }

    public View setComment(Content c){
        LinearLayout author = new LinearLayout(getActivity());
        author.setOrientation(LinearLayout.HORIZONTAL);
        author.setLayoutParams(lp_W_W);
        int indent = (int) getActivity().getResources().getDimension(R.dimen.news_pad_comment_l);
        int indR = (int) getActivity().getResources().getDimension(R.dimen.news_pad_comment_r);
        author.setPadding(indent, 0, indR, 0);
        ImageView iv = new ImageView(getActivity());
        iv.setLayoutParams(lp_W_W);
        iv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.vox_ic_red_quote));
        author.addView(iv);

        LinearLayout b1 = new LinearLayout(getActivity());
        b1.setOrientation(LinearLayout.VERTICAL);
        b1.setLayoutParams(lp_W_W);
        int ind = (int) getActivity().getResources().getDimension(R.dimen.news_pad_comment_t);
        b1.setPadding(ind, ind, 0, 0);
        TextView tvc = new TextView(getActivity());
        tvc.setText(c.getData().get(0));
        tvc.setLayoutParams(lp_W_W);
        tvc.setTextAppearance(getActivity(), R.style.comment);
        b1.addView(tvc);

        LinearLayout b2 = new LinearLayout(getActivity());
        b2.setLayoutParams(lp_W_W);
        b2.setOrientation(LinearLayout.VERTICAL);
        TextView tv = new TextView(getActivity());
        tv.setLayoutParams(lp_W_W);
        tv.setText(c.getTitle());
        tv.setTextAppearance(getActivity(), R.style.photo_title);
        b2.addView(tv);

        TextView tva = new TextView(getActivity());
        tva.setLayoutParams(lp_W_W);
        tva.setText(c.getAuthor());
        tva.setTextAppearance(getActivity(), R.style.photo_author);
        b2.addView(tva);
        b1.addView(b2);
        author.addView(b1);
        return author;
    }

    public View setVideo(Content c){
        String st = c.getUrl().substring(c.getUrl().lastIndexOf("/"));
        LinearLayout v = new LinearLayout(getActivity());
        v.setLayoutParams(lp_M_W);
        v.setBackgroundColor(getActivity().getResources().getColor(R.color.news_color_test1));
        mWebView = new WebView(actv);
        ViewGroup.LayoutParams lp = lp_M_W;
//        lp.height = (int)getActivity().getResources().getDimension(R.dimen.news_video_h);
        mWebView.setLayoutParams(lp);

        mWebView.getSettings().setJavaScriptEnabled(true);
//        String htmlText =
//                "<html><body><div align='center'><iframe src='http://www.youtube.com/embed/"+
//                        st +
//                        "' frameborder='0'></iframe></div></body></html>";
        String htmlText = setVideoHTML(st);
        mWebViewText = htmlText;
        mWebView.loadDataWithBaseURL(null, htmlText, "text/html", "en_US","");
        videoHTML.put(st, mWebView);
        v.addView(mWebView);
        return v;
    }

    public String setVideoHTML(String st){
        return "<html><body><div width='100%' heigth='100%' align='center'>" +
                "<iframe src='http://www.youtube.com/embed/"+
                st + "' frameborder='0'></iframe></div></body></html>";
    }

    public void setAuthors(List<Author> a){
        int iMax = a.size();
        View v;
        for (int i=0; i<iMax;i++){
            setAuthor(a.get(i));
        }
    }

    public void setAuthor(Author a){
        View view = ((LayoutInflater) getActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.news_author, null);
        ImageView iv = (ImageView) view.findViewById(R.id.imv);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView post = (TextView) view.findViewById(R.id.post);
        BitmapPool pool = Glide.get(getActivity()).getBitmapPool();
        Glide.with(this).load(a.getAvatar()).bitmapTransform(new CropCircleTransformation(pool)).into(iv);
        name.setText(a.getTitle());
        post.setText(a.getPosition());
        ll.addView(view);
//        return view;
    }


    public void setSimilar(List<Similar> s){
        TextView tv = new TextView(getActivity());
        tv.setText("Материал по теме");
        tv.setLayoutParams(lp_W_W);
        tv.setTextColor(getActivity().getResources().getColor(R.color.news_color_text));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getActivity().getResources().getDimension(R.dimen.news_fsize_text));
        LinearLayout lt = new LinearLayout(getActivity());
        lt.setLayoutParams(lp_W_W);
        int padText = (int) getActivity().getResources().getDimension(R.dimen.news_padding_txt);
        int padSim = (int) getActivity().getResources().getDimension(R.dimen.news_padding_similar);
        lt.setPadding(padSim, padText, padSim, padSim);
        lt.addView(tv);
        ll.addView(lt);

        int iMax = s.size();
        View v = null;
        for (int i=0; i<iMax;i++){
            v = setS(s.get(i));
            if (v != null) ll.addView(v);
        }
    }

    public View setS(Similar a){
        ImageView iv;
        TextView title;
        TextView date;
        TextView txt_views;
        TextView txt_comment;

        View view = ((LayoutInflater) getActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_little, null);
        view.setClickable(true);
        view.setOnClickListener(this);
        view.setTag("art;" + a.getId());
        iv = (ImageView) view.findViewById(R.id.imv_l);
        title = (TextView) view.findViewById(R.id.title_l);
        date = (TextView) view.findViewById(R.id.date_l);
        txt_views = (TextView) view.findViewById(R.id.txt_views_l);
        txt_comment = (TextView) view.findViewById(R.id.txt_comment_l);
        Glide.with(this).load(a.getImage()).centerCrop().into(iv);
        title.setText(a.getTitle());
        date.setText(a.getPostDate());
        txt_views.setText(a.getViwed());
        txt_comment.setText(a.getCommentsAmount());

        return view;
    }
}

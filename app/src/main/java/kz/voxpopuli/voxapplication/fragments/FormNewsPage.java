package kz.voxpopuli.voxapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.adapter.PageAdapter;
import kz.voxpopuli.voxapplication.network.wrappers.mpage.Article;
import kz.voxpopuli.voxapplication.network.wrappers.pnews.Author;
import kz.voxpopuli.voxapplication.network.wrappers.pnews.Content;
import kz.voxpopuli.voxapplication.network.wrappers.pnews.NewsTag;
import kz.voxpopuli.voxapplication.network.wrappers.pnews.Pnews;

public class FormNewsPage implements Response.ErrorListener, View.OnClickListener {
    int maxWidth;
    LinearLayout ll;
    Pnews pn;
    Context context;
    View parent;
    public ViewGroup.LayoutParams lpWrap;
    private MediaController mediaControls;

    public FormNewsPage(Context cont, Pnews p, LinearLayout l, View parent, int mWidth){
        maxWidth = mWidth - ((int) parent.getResources().getDimension(R.dimen.news_padding_txt))*2;
        ll = l;
        pn = p;
        context = cont;
        this.parent = parent;
    }

    public void setNewsPage(){
        lpWrap = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setArticle();
        setContent();
        setAuthor(pn.getAuthor());
//        ll.addView(setAuthor(pn.getAuthor()));
        setTags(ll,pn.getTags());
        setSimilar(pn.getSimilar());
        setBottom(pn.getArticle().getCommentsAmount());

    }

    @Override
    public void onClick(View v) {
        String classV = v.getClass().getName();
        if (classV.indexOf("TextView")>0) {
            List<NewsTag> tags = pn.getTags();
            TextView t = (TextView)v;
            String tag = t.getText().toString();
            String id = "";
            for (int i = 0; i<tags.size(); i++){
                if (tag.equals(tags.get(i).getTitle())) {
                    id = tags.get(i).getId();
                    break;
                }
            }

            Log.d("TAG","TAG="+t.getText()+" ID="+id);
        }
        else {
            TextView tv = (TextView) v.findViewById(R.id.title_l);
            Log.d("TAG","Article="+tv.getText());
        }
    }

    public void setArticle(){
        Article article = pn.getArticle();
        ImageView iv = (ImageView)parent.findViewById(R.id.imv);
        TextView title = (TextView)parent.findViewById(R.id.title);
        Glide.with(context).load(article.getImageMid()).into(iv);
        title.setText(article.getTitle());
        TextView descr = (TextView)parent.findViewById(R.id.descr);
        descr.setText(article.getDescription());
        TextView date = (TextView)parent.findViewById(R.id.date);
        date.setText(article.getPostDate());
        TextView v = (TextView)parent.findViewById(R.id.txt_views);
        v.setText(article.getViwed());
        TextView com = (TextView)parent.findViewById(R.id.txt_comment);
        com.setText(article.getCommentsAmount());
    }

    public void setTags(LinearLayout linerL, List<NewsTag> tag){
        int width, widthAll;
        ViewGroup.LayoutParams lpWMW = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lpM = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                (int)context.getResources().getDimension(R.dimen.news_height_tag));
        lpM.setMargins(0,0,5,0);

        LinearLayout lt1 = new LinearLayout(context);
        lt1.setLayoutParams(lpWMW);
        lt1.setBackgroundColor(context.getResources().getColor(R.color.news_color_line));
        int padLine = (int) context.getResources().getDimension(R.dimen.news_size_line);
        lt1.setPadding(0, 0, 0, padLine);
        LinearLayout lt = new LinearLayout(context);
        lt.setLayoutParams(lpWMW);
        lt.setOrientation(LinearLayout.VERTICAL);
        lt.setBackgroundColor(context.getResources().getColor(R.color.news_color_white));
        int padText = (int) context.getResources().getDimension(R.dimen.news_padding_txt);
        lt.setPadding(padText, padText, padText, padText);
        lt1.addView(lt);

        LinearLayout line = newLayout(context, lpWrap);
        lt.addView(line);
        widthAll = 0;
        for (int i=0;i<tag.size();i++){
            TextView tv = new TextView(context);
            tv.setLayoutParams(lpM);
            tv.setText(tag.get(i).getTitle());
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            tv.setBackgroundResource(R.drawable.tag_shape);
            tv.setTextAppearance(context, R.style.news_tag);

            tv.setClickable(true);
            tv.setOnClickListener(this);
            tv.measure(0, 0);
            width = tv.getMeasuredWidth()+5;
            widthAll += width;
            if (widthAll<maxWidth) line.addView(tv);
            else {
                line = newLayout(context,lpWrap);
                lt.addView(line);
                line.addView(tv);
                widthAll = width;
            }
        }
        linerL.addView(lt1);
    }

    public void setBottom(String com){

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
        if (type.equals("Photo")) return setPhoto(c);
        if (type.equals("Video")) return setVideo(c);
        if (type.equals("Gallery")) return setGallery(c);
        return null;
    }

    public View setTitle(Content c){
        TextView tv = new TextView(context);
        tv.setText(c.getData().get(0));
        tv.setLayoutParams(lpWrap);
        tv.setTextColor(context.getResources().getColor(R.color.news_color_title));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.news_fsize_title));
        LinearLayout lt = new LinearLayout(context);
        lt.setLayoutParams(lpWrap);
        int padText = (int) context.getResources().getDimension(R.dimen.news_padding_txt);
        int padDelt = (int) context.getResources().getDimension(R.dimen.news_padding_top_title);
        lt.setPadding(padText, padDelt, padText, 0);
        lt.addView(tv);
        return lt;
    }

    public View setTxt(Content c){
        TextView tv = new TextView(context);
        tv.setText(c.getData().get(0));
        tv.setLayoutParams(lpWrap);
        tv.setTextColor(context.getResources().getColor(R.color.news_color_text));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.news_fsize_text));
        LinearLayout lt = new LinearLayout(context);
        lt.setLayoutParams(lpWrap);
        int padText = (int) context.getResources().getDimension(R.dimen.news_padding_txt);
        lt.setPadding(padText, padText, padText, padText);
        lt.addView(tv);
        return lt;
    }

    public View setPhoto(Content c){
        ImageView iv = new ImageView(context);
        iv.setLayoutParams(lpWrap);
        Glide.with(context).load(c.getData().get(0)).into(iv);
        LinearLayout lt = new LinearLayout(context);
        lt.setOrientation(LinearLayout.VERTICAL);
        int pad = (int) context.getResources().getDimension(R.dimen.news_padding_img);
        lt.setPadding(pad, 0, pad, 0);
        lt.addView(iv);
        if ((c.getTitle().length()>0) ||(c.getAuthor().length()>0)) {
            LinearLayout author = new LinearLayout(context);
            author.setOrientation(LinearLayout.VERTICAL);
            author.setLayoutParams(lpWrap);
            int indent = (int) context.getResources().getDimension(R.dimen.news_indent48);
            author.setPadding(indent, 0, 0, 0);
            LinearLayout b1 = new LinearLayout(context);
            author.setOrientation(LinearLayout.VERTICAL);
            b1.setLayoutParams(lpWrap);
            b1.setBackgroundColor(context.getResources().getColor(R.color.news_color_line));
            int t = (int) context.getResources().getDimension(R.dimen.news_thickness_line);
            b1.setPadding(t,0,0,0);
            LinearLayout b2 = new LinearLayout(context);
            b2.setLayoutParams(lpWrap);
            b2.setBackgroundColor(context.getResources().getColor(R.color.news_color_white));
            b2.setOrientation(LinearLayout.VERTICAL);
            int in12 = (int) context.getResources().getDimension(R.dimen.news_indent12);
            b2.setPadding(in12,pad,0,pad);
            TextView tv = new TextView(context);
            tv.setText(c.getTitle());
            tv.setLayoutParams(lpWrap);
            tv.setTextColor(context.getResources().getColor(R.color.news_color_text));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.news_fsize_photo));
            b2.addView(tv);
            TextView tva = new TextView(context);
            tva.setText(c.getAuthor());
            tva.setLayoutParams(lpWrap);
            tva.setTextColor(context.getResources().getColor(R.color.news_color_text));
            tva.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.news_fsize_photo));
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
        final TextView numPhoto = new TextView(context);
        numPhoto.setLayoutParams(lpWrap);
        numPhoto.setTextAppearance(context, R.style.photo_title);
        numPhoto.setText("1 из "+gal.size());
        TextView txtGal = new TextView(context);
        txtGal.setLayoutParams(lpWrap);
        txtGal.setTextAppearance(context, R.style.title_gallery);
        txtGal.setText("Галерея        ");

        LinearLayout titleG = new LinearLayout(context);
        titleG.setOrientation(LinearLayout.HORIZONTAL);
        int pad1 = (int) context.getResources().getDimension(R.dimen.news_pad_title_gallery);
        titleG.setPadding(pad1, 0, pad1, pad1);
        titleG.addView(txtGal);
        titleG.addView(numPhoto);
        PageAdapter pa = new PageAdapter(context, gal);
        ViewPager vp = new ViewPager(context);
        vp.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                (int) context.getResources().getDimension(R.dimen.news_height_gallery)));
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
//                Log.d("QWERT", "Pos = "+position+" Scroll = " + positionOffsetPixels + " FF = "+positionOffset+" AA="+(int) (256*positionOffset));
//                pts.setBackgroundColor(Color.argb((int) (256 * positionOffset), 240, 130, 230));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        LinearLayout lt = new LinearLayout(context);
        lt.setOrientation(LinearLayout.VERTICAL);
        int pad = (int) context.getResources().getDimension(R.dimen.news_padding_img);
        lt.setPadding(pad, 0, pad, 0);
        lt.addView(titleG);
        lt.addView(vp);
        if ((c.getTitle().length()>0) ||(c.getAuthor().length()>0)) {
            LinearLayout author = new LinearLayout(context);
            author.setOrientation(LinearLayout.VERTICAL);
            author.setLayoutParams(lpWrap);
            int indent = (int) context.getResources().getDimension(R.dimen.news_indent48);
            author.setPadding(indent, 0, 0, 0);
            LinearLayout b1 = new LinearLayout(context);
            author.setOrientation(LinearLayout.VERTICAL);
            b1.setLayoutParams(lpWrap);
            b1.setBackgroundColor(context.getResources().getColor(R.color.news_color_line));
            int t = (int) context.getResources().getDimension(R.dimen.news_thickness_line);
            b1.setPadding(t,0,0,0);
            LinearLayout b2 = new LinearLayout(context);
            b2.setLayoutParams(lpWrap);
            b2.setBackgroundColor(context.getResources().getColor(R.color.news_color_white));
            b2.setOrientation(LinearLayout.VERTICAL);
            int in12 = (int) context.getResources().getDimension(R.dimen.news_indent12);
            b2.setPadding(in12,pad,0,pad);
            if (c.getTitle().length()>0){
                TextView tv = new TextView(context);
                tv.setText(c.getTitle());
                tv.setLayoutParams(lpWrap);
                tv.setTextColor(context.getResources().getColor(R.color.news_color_text));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.news_fsize_photo));
                b2.addView(tv);
            }
            if (c.getAuthor().length()>0) {
                TextView tva = new TextView(context);
                tva.setText(c.getAuthor());
                tva.setLayoutParams(lpWrap);
                tva.setTextColor(context.getResources().getColor(R.color.news_color_text));
                tva.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.news_fsize_photo));
                b2.addView(tva);
            }
            b1.addView(b2);
            author.addView(b1);
            lt.addView(author);
        }
        return lt;
    }

    public View setComment(Content c){
        LinearLayout author = new LinearLayout(context);
        author.setOrientation(LinearLayout.HORIZONTAL);
        author.setLayoutParams(lpWrap);
        int indent = (int) context.getResources().getDimension(R.dimen.news_pad_comment_l);
        int indR = (int) context.getResources().getDimension(R.dimen.news_pad_comment_r);
        author.setPadding(indent, 0, indR, 0);
        ImageView iv = new ImageView(context);
        iv.setLayoutParams(lpWrap);
        iv.setImageDrawable(context.getResources().getDrawable(R.drawable.vox_ic_red_quote));
        author.addView(iv);

        LinearLayout b1 = new LinearLayout(context);
        b1.setOrientation(LinearLayout.VERTICAL);
        b1.setLayoutParams(lpWrap);
        int ind = (int) context.getResources().getDimension(R.dimen.news_pad_comment_t);
        b1.setPadding(ind, ind, 0, 0);
        TextView tvc = new TextView(context);
        tvc.setText(c.getData().get(0));
        tvc.setLayoutParams(lpWrap);
        tvc.setTextAppearance(context, R.style.comment);
        b1.addView(tvc);

        LinearLayout b2 = new LinearLayout(context);
        b2.setLayoutParams(lpWrap);
        b2.setOrientation(LinearLayout.VERTICAL);
        TextView tv = new TextView(context);
        tv.setLayoutParams(lpWrap);
        tv.setText(c.getTitle());
        tv.setTextAppearance(context, R.style.photo_title);
        b2.addView(tv);

        TextView tva = new TextView(context);
        tva.setLayoutParams(lpWrap);
        tva.setText(c.getAuthor());
        tva.setTextAppearance(context, R.style.photo_author);
        b2.addView(tva);
        b1.addView(b2);
        author.addView(b1);
        return author;
    }

    public void setAuthor(Author a){
        View view = ((LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.news_author, null);
        ImageView iv = (ImageView) view.findViewById(R.id.imv);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView post = (TextView) view.findViewById(R.id.post);
        BitmapPool pool = Glide.get(context).getBitmapPool();
        Glide.with(context).load(a.getAvatar()).bitmapTransform(new CropCircleTransformation(pool)).into(iv);
        name.setText(a.getName());
        post.setText(a.getPost());
        ll.addView(view);
//        return view;
    }


    public void setSimilar(List<Article> s){
        TextView tv = new TextView(context);
        tv.setText("Материал по теме");
        tv.setLayoutParams(lpWrap);
        tv.setTextColor(context.getResources().getColor(R.color.news_color_text));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.news_fsize_text));
        LinearLayout lt = new LinearLayout(context);
        lt.setLayoutParams(lpWrap);
        int padText = (int) context.getResources().getDimension(R.dimen.news_padding_txt);
        int padSim = (int) context.getResources().getDimension(R.dimen.news_padding_similar);
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

    public View setS(Article a){
        ImageView iv;
        TextView title;
        TextView date;
        TextView txt_views;
        TextView txt_comment;

        View view = ((LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_little, null);
        view.setClickable(true);
        view.setOnClickListener(this);
        iv = (ImageView) view.findViewById(R.id.imv_l);
        title = (TextView) view.findViewById(R.id.title_l);
        date = (TextView) view.findViewById(R.id.date_l);
        txt_views = (TextView) view.findViewById(R.id.txt_views_l);
        txt_comment = (TextView) view.findViewById(R.id.txt_comment_l);
        Glide.with(context).load(a.getImage()).centerCrop().into(iv);
        title.setText(a.getTitle());
        date.setText(a.getPostDate());
        txt_views.setText(a.getViwed());
        txt_comment.setText(a.getCommentsAmount());

        return view;
    }

    public View setVideo(Content c){
        LinearLayout v = new LinearLayout(context);
//        ViewGroup.LayoutParams lparam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,R.dimen.news_video_h);
        v.setLayoutParams(lpWrap);
        v.setBackgroundColor(context.getResources().getColor(R.color.news_color_test1));

//        TextView tv = new TextView(context);
//        tv.setText(c.getData());
//        tv.setLayoutParams(lpWrap);
//        tv.setTextColor(getResources().getColor(R.color.news_color_title));
//        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.news_fsize_title));
//        LinearLayout lt = new LinearLayout(context);
//        lt.setLayoutParams(lpWrap);
//        int padText = (int) getResources().getDimension(R.dimen.news_padding_txt);
//        int padDelt = (int) getResources().getDimension(R.dimen.news_padding_top_title);
//        lt.setPadding(padText, padDelt, padText, 0);
//        lt.addView(tv);
//        v.addView(lt);

        VideoView myVideoView = new VideoView(context);
        myVideoView.setVideoURI(Uri.parse(c.getData().get(0)));
        if (mediaControls == null) {
            mediaControls = new MediaController(context);
        }
        myVideoView.setMediaController(mediaControls);
        myVideoView.requestFocus(0);
        myVideoView.setVisibility(View.VISIBLE);



//        myVideoView.start();
//        myVideoView.pause();
        v.addView(myVideoView);
        return v;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}

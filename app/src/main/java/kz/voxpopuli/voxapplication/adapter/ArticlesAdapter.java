package kz.voxpopuli.voxapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.model.ModelArticle;

public class ArticlesAdapter extends BaseAdapter {
    private Fragment context;
    private ModelArticle[] items;

    public ArticlesAdapter(Fragment context, ModelArticle[] items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.length;
    }

    @Override
    public Object getItem(int position) {
        return items == null ? null : items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return 3;
    }

    public int getItemViewType(int position) {
        int type=0;
        if (position < 2) type=0;
        else if (position == 2) type = 1;
        else type = 2;
        return type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv;
        TextView title;
        TextView descr;
        TextView date;
        TextView txt_views;
        TextView txt_comment;

//        Bitmap bm = Bitmap.createBitmap(R.dimen.img_w_l, R.dimen.img_h_l, Bitmap.Config.RGB_565);

        View view = convertView;
        ModelArticle ma = items[position];
        switch (getItemViewType( position)){
            case 0: view = ((LayoutInflater) context.getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_red, null);
                break;
            case 1: view = ((LayoutInflater) context.getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_white, null);
                break;
            case 2: view = ((LayoutInflater) context.getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_little, null);
        }


        switch (getItemViewType( position)){
            case 0:
                iv = (ImageView) view.findViewById(R.id.imv);
                title = (TextView) view.findViewById(R.id.title);
                descr = (TextView) view.findViewById(R.id.descr);
                date = (TextView) view.findViewById(R.id.date);
                txt_views = (TextView) view.findViewById(R.id.txt_views);
                txt_comment = (TextView) view.findViewById(R.id.txt_comment);
                descr.setText(ma.getDescription());
                Glide.with(context).
                        load(ma.getImage_mid()).
                        into(iv);
//                Glide.with(context).
//                        load(ma.getImage_mid()).
//                        into(bm);
//                iv.setImageBitmap(cut(bm,R.dimen.img_w_l, R.dimen.img_h_l));
                title.setText(ma.getTitle());
                date.setText(ma.getDate());
                txt_views.setText(""+ma.getViews());
                txt_comment.setText(""+ma.getComment());
                break;
            case 1:
                iv = (ImageView) view.findViewById(R.id.imv_w);
                title = (TextView) view.findViewById(R.id.title_w);
                date = (TextView) view.findViewById(R.id.date_w);
                txt_views = (TextView) view.findViewById(R.id.txt_views_w);
                txt_comment = (TextView) view.findViewById(R.id.txt_comment_w);
                Glide.with(context).load(ma.getImage_mid()).into(iv);
                title.setText(ma.getTitle());
                date.setText(ma.getDate());
                txt_views.setText(""+ma.getViews());
                txt_comment.setText(""+ma.getComment());
                break;
            case 2:
                iv = (ImageView) view.findViewById(R.id.imv_l);
                title = (TextView) view.findViewById(R.id.title_l);
                date = (TextView) view.findViewById(R.id.date_l);
                txt_views = (TextView) view.findViewById(R.id.txt_views_l);
                txt_comment = (TextView) view.findViewById(R.id.txt_comment_l);
                Glide.with(context).load(ma.getImage_mid()).into(iv);
                title.setText(ma.getTitle());
                date.setText(ma.getDate());
                txt_views.setText(""+ma.getViews());
                txt_comment.setText(""+ma.getComment());
        }

        return view;
    }

//    public Bitmap cut (Bitmap bitmap, int reqWidth, int reqHeight){
//        Bitmap resBitmap;
//        int wIm, hIm, wBegin,hBegin;
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//        float w1=(float) width/reqWidth;
//        float h1=(float) height/reqHeight;
//        if (w1>=h1) {        // обрезаем ширину width
//            wIm = (int) (reqWidth*h1);
//            hIm = height;
//            wBegin = (width - wIm) / 2;
//            hBegin = 0;
//        } else {        // обрезаем высоту height
//            wIm = width;
//            hIm = (int) (reqHeight * w1);
//            hBegin = (height - hIm) / 2;
//            wBegin = 0;
//        }
//        resBitmap = Bitmap.createBitmap(wIm, hIm, Bitmap.Config.RGB_565);
//        int[] pixels = new int[wIm * hIm];
//        bitmap.getPixels(pixels, 0, wIm, wBegin, hBegin, wIm, hIm);
//        resBitmap.setPixels(pixels, 0, wIm, 0, 0, wIm, hIm);
//        return resBitmap;
//    }

}

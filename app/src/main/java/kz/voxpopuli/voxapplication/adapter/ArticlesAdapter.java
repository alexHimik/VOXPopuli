package kz.voxpopuli.voxapplication.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.network.wrappers.mpage.Article;

public class ArticlesAdapter extends BaseAdapter {
    private Fragment context;
    private List<Article> items;

    public ArticlesAdapter(Fragment context, List<Article> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return items == null ? null : items.get(position);
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
        int type = 0;
        if (position < 10) type = 0;
        else if ((position % 5) == 0) type = 1;
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
        LinearLayout ll;

        View view = convertView;
        Article ma = items.get(position);
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
                        load(ma.getImageMid()).
                        into(iv);
                title.setText(ma.getTitle());
                date.setText(ma.getPostDate());
                txt_views.setText(ma.getViwed());
                txt_comment.setText(ma.getCommentsAmount());
                break;
            case 1:
                iv = (ImageView) view.findViewById(R.id.imv_w);
                title = (TextView) view.findViewById(R.id.title_w);
                date = (TextView) view.findViewById(R.id.date_w);
                txt_views = (TextView) view.findViewById(R.id.txt_views_w);
                txt_comment = (TextView) view.findViewById(R.id.txt_comment_w);
                Glide.with(context).load(ma.getImageMid()).into(iv);
                title.setText(ma.getTitle());
                date.setText(ma.getPostDate());
                txt_views.setText(ma.getViwed());
                txt_comment.setText(ma.getCommentsAmount());
                break;
            case 2:
                iv = (ImageView) view.findViewById(R.id.imv_l);
                title = (TextView) view.findViewById(R.id.title_l);
                date = (TextView) view.findViewById(R.id.date_l);
                txt_views = (TextView) view.findViewById(R.id.txt_views_l);
                txt_comment = (TextView) view.findViewById(R.id.txt_comment_l);
                Glide.with(context).load(ma.getImageMid()).centerCrop().into(iv);
                title.setText(ma.getTitle());
                date.setText(ma.getPostDate());
                txt_views.setText(ma.getViwed());
                txt_comment.setText(ma.getCommentsAmount());
        }
        return view;
    }
}

package kz.voxpopuli.voxapplication.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.LinkedList;
import java.util.List;

import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.fragments.NewsPageFragment;
import kz.voxpopuli.voxapplication.network.wrappers.mpage.Article;

/**
 * Created by user on 16.04.15.
 */
public class SearchByStringAdapter extends BaseAdapter {

    private List<Article> items = new LinkedList<>();
    private Fragment context;

    public SearchByStringAdapter(Fragment fragment, List<Article> articles) {
        this.items = articles;
        this.context = fragment;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Article getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(items.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View recycled = convertView;
        ItemHolder holder;

        if(recycled == null) {
            recycled = ((LayoutInflater) context.getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_little, null);

            holder = new ItemHolder();
            holder.iv = (ImageView) recycled.findViewById(R.id.imv_l);
            holder.title = (TextView) recycled.findViewById(R.id.title_l);
            holder.date = (TextView) recycled.findViewById(R.id.date_l);
            holder.txt_views = (TextView) recycled.findViewById(R.id.txt_views_l);
            holder.txt_comment = (TextView) recycled.findViewById(R.id.txt_comment_l);

            recycled.setOnClickListener(clickListener);
            recycled.setTag(holder);
        }

        holder = (ItemHolder)recycled.getTag();
        Article article = items.get(position);
        Glide.with(context).load(article.getImageMid()).centerCrop().into(holder.iv);
        holder.title.setText(article.getTitle());
        holder.date.setText(article.getPostDate());
        holder.txt_views.setText(article.getViwed());
        holder.txt_comment.setText(article.getCommentsAmount());
        holder.articleId = article.getId();

        return recycled;
    }

    public List<Article> getItems() {
        return items;
    }

    public void setItems(List<Article> items) {
        this.items = items;
    }

    private class ItemHolder {
        public ImageView iv;
        public TextView title;
        public TextView descr;
        public TextView date;
        public TextView txt_views;
        public TextView txt_comment;
        public LinearLayout ll;

        public String articleId;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ItemHolder tag = (ItemHolder)v.getTag();
            Bundle bundle = new Bundle();
            bundle.putString(NewsPageFragment.ARTICLE_KEY, tag.articleId);
            ((MainActivity)context.getActivity()).handleFragmentSwitching(NewsPageFragment.FRAGMENT_ID,
                    bundle);
        }
    };
}

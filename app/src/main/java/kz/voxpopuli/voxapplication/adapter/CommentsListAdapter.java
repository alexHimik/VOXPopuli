package kz.voxpopuli.voxapplication.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.network.wrappers.comments.Comment;
import kz.voxpopuli.voxapplication.network.wrappers.comments.article.ArcticleComment;
import kz.voxpopuli.voxapplication.network.wrappers.mpage.Article;
import kz.voxpopuli.voxapplication.tools.UserInfoTools;

public class CommentsListAdapter extends BaseAdapter {
    private Fragment context;
    private List<ArcticleComment> items;

    public CommentsListAdapter(Fragment context, List<ArcticleComment> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public ArcticleComment getItem(int position) {
        return items == null ? null : items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv;
        TextView author;
        TextView descr;
        TextView datetime;
        TextView textComment;

        BitmapPool pool;

        View view = convertView;
        ArcticleComment com = items.get(position);

        view = ((LayoutInflater) context.getActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.comments_item, null);

        iv = (ImageView) view.findViewById(R.id.imv_com);
        author = (TextView) view.findViewById(R.id.author);
        datetime = (TextView) view.findViewById(R.id.datetime);
        textComment = (TextView) view.findViewById(R.id.txt_comm);

        author.setText(com.getAuthorName());
        datetime.setText(com.getCommentDate());
        textComment.setText(com.getComment());
        pool = Glide.get(context.getActivity()).getBitmapPool();
        Glide.with(context).load(com.getAuthorAvatar()).bitmapTransform(new CropCircleTransformation(pool)).into(iv);

        return view;
    }
}

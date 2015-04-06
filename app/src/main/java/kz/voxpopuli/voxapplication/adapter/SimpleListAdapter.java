package kz.voxpopuli.voxapplication.adapter;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.model.SimpleListItemModel;

/**
 * Created by user on 27.03.15.
 */
public class SimpleListAdapter extends BaseAdapter {

    private List<SimpleListItemModel> items;
    private Fragment context;

    public SimpleListAdapter(Fragment context, List<SimpleListItemModel> items) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if(view == null) {
            view = ((LayoutInflater)context.getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.news_card_big, null);
            holder = new ViewHolder();
            holder.image = (ImageView)view.findViewById(R.id.new_card_big_image);
            holder.textView = (TextView)view.findViewById(R.id.new_card_bit_title);
            view.setTag(holder);
        }
        holder = (ViewHolder)view.getTag();
        holder.textView.setText(items.get(position).getTitle());
        Glide.with(context).load(items.get(position).getUrl()).centerCrop().into(holder.image);
        return view;
    }

    public List<SimpleListItemModel> getItems() {
        return items;
    }

    public void setItems(List<SimpleListItemModel> items) {
        this.items = items;
    }

    private class ViewHolder {
        public ImageView image;
        public TextView textView;
    }
}

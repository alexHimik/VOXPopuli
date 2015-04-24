package kz.voxpopuli.voxapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.devspark.robototextview.widget.RobotoTextView;

import java.util.List;

import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.network.wrappers.comments.user.Comment;

/**
 * Created by user on 23.04.15.
 */
public class UserProfileCommentsAdapter extends BaseAdapter {

    private final int VIEW_TYPES_COUNT = 2;
    private final int VIEW_TYPE_HEADER = 0;
    private final int VIEW_TYPE_COMMENT = 1;

    private Context context;
    private List<Comment> commnets;
    private LayoutInflater inflater;

    public UserProfileCommentsAdapter(Context context, List<Comment> items) {
        this.context = context;
        this.commnets = items;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(commnets.get(position).getId());
    }

    @Override
    public int getCount() {
        return commnets.size();
    }

    @Override
    public Comment getItem(int position) {
        return commnets.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_COMMENT;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPES_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(getItemViewType(position) == VIEW_TYPE_HEADER) {
            view = inflater.inflate(R.layout.user_profile_comment_first, null);
            RobotoTextView text = (RobotoTextView)view.findViewById(R.id.comments_header);
            text.setText(context.getString(R.string.user_comments_text));
        } else if(getItemViewType(position) == VIEW_TYPE_COMMENT) {
            view = inflater.inflate(R.layout.user_profile_comment, null);
            RobotoTextView date = (RobotoTextView)view.findViewById(R.id.comment_date);
            date.setText(commnets.get(position).getCommentDate());
            RobotoTextView comment = (RobotoTextView)view.findViewById(R.id.comment_body);
            comment.setText(commnets.get(position).getComment());
            RobotoTextView theme = (RobotoTextView)view.findViewById(R.id.comment_theme);
            theme.setText(commnets.get(position).getTitle());
        }
        return view;
    }
}

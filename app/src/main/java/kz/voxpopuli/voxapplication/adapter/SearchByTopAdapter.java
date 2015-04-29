package kz.voxpopuli.voxapplication.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.LinkedList;
import java.util.List;

import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.fragments.NewsPageFragment;
import kz.voxpopuli.voxapplication.model.rows.IRowItemModel;
import kz.voxpopuli.voxapplication.network.wrappers.search.top.Article;
import kz.voxpopuli.voxapplication.network.wrappers.search.top.Group;

public class SearchByTopAdapter extends BaseAdapter {

    public static final int VIEW_TYPES_COUNT = 2;
    public static final int VIEW_TYPE_GROUP = 0;
    public static final int VIEW_TYPE_ARTICLE = 1;

    private Fragment fragment;
    private LayoutInflater inflater;
    private List<Group> items = new LinkedList<>();
    private List<List<ArticleAndGroup>> innerItems = new LinkedList<>();

    public SearchByTopAdapter(Fragment fragment, List<Group> items) {
        this.fragment = fragment;
        this.items= items;
        inflater = (LayoutInflater)fragment.getActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        transformGroupDataIntoFlatPresentation(items);
    }

    @Override
    public int getCount() {
        return innerItems.size();
    }

    @Override
    public List<ArticleAndGroup> getItem(int position) {
        return innerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPES_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return innerItems.get(position).size() == 1 ? VIEW_TYPE_GROUP : VIEW_TYPE_ARTICLE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        List<ArticleAndGroup> item = innerItems.get(position);
        View view = convertView;
        int pad8 = (int) parent.getResources().getDimension(R.dimen.news_8);
        int pad4 = (int) parent.getResources().getDimension(R.dimen.news_4);
        if(item.size() == 1 && item.get(0).isGroup()) {
            view = item.get(0).initModelsViews(inflater);
            item.get(0).setModelDataToViews(fragment.getActivity());
        } else {
            View row = inflater.inflate(R.layout.hoster_row, null);
            View left = row.findViewById(R.id.hoster_row_left_item);
            View leftCell = item.get(0).initModelsViews(inflater);
            leftCell.setLayoutParams(left.getLayoutParams());
            leftCell.setId(item.get(0).getId());
            ((ViewGroup)row).removeView(left);
            ((ViewGroup)row).addView(leftCell, 0);
            leftCell.setPadding(pad8, pad8, pad4, 0);
            item.get(0).setModelDataToViews(fragment.getActivity());

            if(item.size() > 1) {
                View right = row.findViewById(R.id.hoster_row_right_item);
                View rightCell = item.get(1).initModelsViews(inflater);
                rightCell.setLayoutParams(right.getLayoutParams());
                rightCell.setId(item.get(1).getId());
                ((ViewGroup)row).removeView(right);
                ((ViewGroup)row).addView(rightCell, 1);
                rightCell.setPadding(pad4, pad8, pad8, 0);
                item.get(1).setModelDataToViews(fragment.getActivity());
            }
            view = row;
        }
        return view;
    }

    private void transformGroupDataIntoFlatPresentation(List<Group> data) {
        if(data != null && data.size() > 0) {
            for(Group group : data) {
                ArticleAndGroup itm = new ArticleAndGroup();
                boolean shareFlag = false;
                itm.setGroup(true);
                itm.setGroupName(group.getName());
                if(group.getName().equals(fragment.getString(R.string.most_share))) {
                    shareFlag = true;
                }
                List<ArticleAndGroup> list = new LinkedList<>();
                list.add(itm);
                innerItems.add(list);
                boolean filled = false;
                List<ArticleAndGroup> twoItems = null;
                for(Article a : group.getArticles()) {
                    ArticleAndGroup atg = new ArticleAndGroup();
                    atg.setGroup(false);
                    atg.setShare(shareFlag);
                    atg.setViwed(a.getViwed());
                    atg.setTitle(a.getTitle());
                    atg.setPostDate(a.getPostDate());
                    atg.setId(a.getId());
                    atg.setImage(a.getImage());
                    if(!filled) {
                        twoItems = new LinkedList<>();
                        twoItems.add(atg);
                        innerItems.add(twoItems);
                        filled = true;
                    } else {
                        twoItems.add(atg);
                        filled = false;
                    }
                }
            }
        }
    }

    public class ArticleAndGroup implements IRowItemModel {

        RobotoTextView titleView;
        RobotoTextView viewedView;
        RobotoTextView groupView;
        ImageView imageView;
        ImageButton bottomButton;

        private boolean group;
        private boolean share;
        private String groupName;
        private String title;
        private String image;
        private String postDate;
        private String viwed;
        private String id;

        private ArticleAndGroup() {
        }

        public boolean isGroup() {
            return group;
        }

        public void setGroup(boolean group) {
            this.group = group;
        }

        public boolean isShare() {
            return share;
        }

        public void setShare(boolean share) {
            this.share = share;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPostDate() {
            return postDate;
        }

        public void setPostDate(String postDate) {
            this.postDate = postDate;
        }

        public String getViwed() {
            return viwed;
        }

        public void setViwed(String viwed) {
            this.viwed = viwed;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdArticle() {
            return id;
        }

        @Override
        public int getId() {
            return Integer.parseInt(id);
        }

        @Override
        public int getResourceId() {
            return isGroup() ? R.layout.search_string_fragment_titile :
                    R.layout.default_search_item;
        }

        @Override
        public View initModelsViews(LayoutInflater inflater) {
            View view;
            if(isGroup()) {
                view = inflater.inflate(R.layout.search_string_fragment_titile, null);
                groupView = (RobotoTextView)view.findViewById(R.id.search_by_string_list_header);
            } else {
                view = inflater.inflate(R.layout.default_search_item, null);
                view.setOnClickListener(clickListener);
//                ClickableHolder tag = new ClickableHolder(false, getId());
                view.setTag(getIdArticle());
                titleView = (RobotoTextView)view.findViewById(R.id.card_title);
                imageView = (ImageView)view.findViewById(R.id.card_image);
                viewedView = (RobotoTextView)view.findViewById(R.id.card_viewed_amount);
                bottomButton = (ImageButton)view.findViewById(R.id.default_search_info_icon);
            }
            return view;
        }

        @Override
        public void setModelDataToViews(Context fragment) {
            if(isGroup()) {
                groupView.setText(getGroupName());
            } else {
                Glide.with(fragment).load(getImage()).centerCrop().into(imageView);
                titleView.setText(getTitle());
                if(isShare()) bottomButton.setBackgroundDrawable(fragment.getResources().getDrawable(
                        R.drawable.vox_ic_sm_grey_share));
                viewedView.setText(getViwed());
            }
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString(NewsPageFragment.ARTICLE_KEY, v.getTag().toString());
            ((MainActivity)fragment.getActivity()).handleFragmentSwitching(NewsPageFragment.FRAGMENT_ID,
                    bundle);
        }
    };
}

package kz.voxpopuli.voxapplication.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.devspark.robototextview.widget.RobotoEditText;

import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;
import kz.voxpopuli.voxapplication.adapter.ArticlesAdapter;
import kz.voxpopuli.voxapplication.adapter.CommentsListAdapter;
import kz.voxpopuli.voxapplication.network.wrappers.comments.Comment;
import kz.voxpopuli.voxapplication.network.wrappers.comments.CommentsList;
import kz.voxpopuli.voxapplication.network.wrappers.mpage.Article;
import kz.voxpopuli.voxapplication.network.wrappers.pnews.PageNewsWrapper;
import kz.voxpopuli.voxapplication.tools.UserInfoTools;

public class CommentsListFragment extends FaddingTitleBaseFragment implements View.OnClickListener{
    public static final String TAG = "CommentsListFragment";
    public static final int FRAGMENT_ID = 200;

//    public Context context;

    private CommentsListAdapter commentsAdapter;
    private List<Comment> comments = new LinkedList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context context = getActivity().getApplicationContext();
        Comment c = new Comment();
            c.setId("125");
            c.setAuthor("Ayala");
            c.setAvatar("http://www.voxpopuli.kz/img/user/90/50_tn.jpg");
            c.setDatetime("Вчера 14:37");
            c.setTxt("А чем отличаются дроны от коптеров? Недавно видела видео, на котором рыбачили с помощью дрона");
        comments.add(c);
        c = new Comment();
            c.setId("126");
            c.setAuthor("Ула Темир");
            c.setAvatar("http://www.voxpopuli.kz/img/user/86/52_tn.png");
            c.setDatetime("Вчера 15:37");
            c.setTxt("Дрон значит беспилотный летательный аппарат, он летает по заданному маршруту, постоянное управление с пульта ему не нужно. У дорогих мультикоптеров есть такая функция. ");
        comments.add(c);
        c = new Comment();
            c.setId("127");
            c.setAuthor("Ruslan Rimov");
            c.setAvatar("http://www.voxpopuli.kz/img/user/333/93_tn.png");
            c.setDatetime("14 апреля 10:22");
            c.setTxt("Дорогой ВОКС. Раз уж Вы пишите (постите и.т.п) про полуголых баб, то пожалуйста не пишите (постите и.т.п) реп`ы о светом.");
        comments.add(c);
        c = new Comment();
            c.setId("125");
            c.setAuthor("Ayala");
            c.setAvatar("http://www.voxpopuli.kz/img/user/90/50_tn.jpg");
            c.setDatetime("Вчера 14:37");
            c.setTxt("А чем отличаются дроны от коптеров? Недавно видела видео, на котором рыбачили с помощью дрона");
        comments.add(c);
        c = new Comment();
            c.setId("126");
            c.setAuthor("Ула Темир");
            c.setAvatar("http://www.voxpopuli.kz/img/user/86/52_tn.png");
            c.setDatetime("Вчера 15:37");
            c.setTxt("Дрон значит беспилотный летательный аппарат, он летает по заданному маршруту, постоянное управление с пульта ему не нужно. У дорогих мультикоптеров есть такая функция. ");
        comments.add(c);
        c = new Comment();
            c.setId("127");
            c.setAuthor("Ruslan Rimov");
            c.setAvatar("http://www.voxpopuli.kz/img/user/333/93_tn.png");
            c.setDatetime("14 апреля 10:22");
            c.setTxt("Дорогой ВОКС. Раз уж Вы пишите (постите и.т.п) про полуголых баб, то пожалуйста не пишите (постите и.т.п) реп`ы о светом.");
        comments.add(c);
        c = new Comment();
            c.setId("125");
            c.setAuthor("Ayala");
            c.setAvatar("http://www.voxpopuli.kz/img/user/90/50_tn.jpg");
            c.setDatetime("Вчера 14:37");
            c.setTxt("А чем отличаются дроны от коптеров? Недавно видела видео, на котором рыбачили с помощью дрона");
        comments.add(c);
        c = new Comment();
            c.setId("126");
            c.setAuthor("Ула Темир");
            c.setAvatar("http://www.voxpopuli.kz/img/user/86/52_tn.png");
            c.setDatetime("Вчера 15:37");
            c.setTxt("Дрон значит беспилотный летательный аппарат, он летает по заданному маршруту, постоянное управление с пульта ему не нужно. У дорогих мультикоптеров есть такая функция. ");
        comments.add(c);
        c = new Comment();
            c.setId("127");
            c.setAuthor("Ruslan Rimov");
            c.setAvatar("http://www.voxpopuli.kz/img/user/333/93_tn.png");
            c.setDatetime("14 апреля 10:22");
            c.setTxt("Дорогой ВОКС. Раз уж Вы пишите (постите и.т.п) про полуголых баб, то пожалуйста не пишите (постите и.т.п) реп`ы о светом.");
        comments.add(c);

        View parent = inflater.inflate(R.layout.comments_list, container,false);
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        RobotoEditText rt = (RobotoEditText) parent.findViewById(R.id.rt);
//        rt.requestFocus(false);
        ListView lv = (ListView) parent.findViewById(R.id.lv);
        commentsAdapter = new CommentsListAdapter(this, comments);
        ImageView send = (ImageView) parent.findViewById(R.id.send);
        send.setOnClickListener(this);
        lv.setAdapter(commentsAdapter);
        ImageView iv = (ImageView) parent.findViewById(R.id.imv_com_b);

        UserInfoTools uit = new UserInfoTools();
        BitmapPool pool = Glide.get(getActivity()).getBitmapPool();
//        Glide.with(context).load(uit.getUserAvatarUrl(context)).bitmapTransform(new CropCircleTransformation(pool)).into(iv);
        Glide.with(context).load("http://www.voxpopuli.kz/img/user/379/20_main.png").bitmapTransform(new CropCircleTransformation(pool)).into(iv);

        return parent;
    }

    @Override
    public void initActionBarItems() {
        rightBarItem.setVisibility(View.INVISIBLE);
        leftbarItem.setOnClickListener(barClickListener);
    }
    private View.OnClickListener barClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).onClick(v);
        }
    };

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

    public void onEvent(CommentsList wrapper) {
        comments = wrapper.getComments();
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }

    @Override
    public void onClick(View v) {
        Log.d("QWERT", "SEND Comment");
    }
}

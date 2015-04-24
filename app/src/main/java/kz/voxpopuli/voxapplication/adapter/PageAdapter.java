package kz.voxpopuli.voxapplication.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PageAdapter extends PagerAdapter {
    Context context;
    List<String> pages = null;

    public PageAdapter(Context cont, List<String> pages){
        this.pages = pages;
        context = cont;
    }

    @Override
    public Object instantiateItem(View collection, int position){
        ViewGroup.LayoutParams lpWrap = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView v = new ImageView(context);
        v.setLayoutParams(lpWrap);
        Glide.with(context).load(pages.get(position)).into(v);
        ((ViewPager) collection).addView(v, 0);
        return v;
    }

    @Override
    public void destroyItem(View collection, int position, Object view){
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public int getCount(){
        return pages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object){
        return view.equals(object);
    }

    @Override
    public void finishUpdate(View arg0){
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1){
    }

    @Override
    public Parcelable saveState(){
        return null;
    }

    @Override
    public void startUpdate(View arg0){
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return "Фото  " + position + " из " + pages.size();
    }
}
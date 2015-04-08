package kz.voxpopuli.voxapplication.activity;

import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.facebook.FacebookSocialNetwork;
import com.github.gorbin.asne.googleplus.GooglePlusSocialNetwork;
import com.github.gorbin.asne.twitter.TwitterSocialNetwork;
import com.github.gorbin.asne.vk.VkSocialNetwork;
import com.vk.sdk.VKScope;

import java.util.ArrayList;
import java.util.Arrays;

import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.fragments.MainStreamPageFragment;
import kz.voxpopuli.voxapplication.fragments.RubricsFragment;
import kz.voxpopuli.voxapplication.fragments.TestFragmet;
import kz.voxpopuli.voxapplication.tools.FragmentFactory;
import kz.voxpopuli.voxapplication.tools.SocialNetworkUtils;
import kz.voxpopuli.voxapplication.tools.ViewTools;


public class MainActivity extends ActionBarActivity implements View.OnClickListener,
        FragmentManager.OnBackStackChangedListener {

    private DrawerLayout drawerLayout;
    private FrameLayout contentLayout;
    private RelativeLayout drawerList;
    private ListView rightDrawer;
    private TextView barTitle;
//    private ActionBarDrawerToggle drawerToggle;

    private CharSequence title;
//    private CharSequence drawerTitle;

//    private String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        if(drawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        int id = item.getItemId();
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
        if(barTitle != null) {
            barTitle.setText(this.title);
        }
//        getSupportActionBar().setTitle(this.title);
    }

//    /**
//     * When using the ActionBarDrawerToggle, you must call it during
//     * onPostCreate() and onConfigurationChanged()...
//     */
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        drawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        // Pass any configuration change to the drawer toggles
//        drawerToggle.onConfigurationChanged(newConfig);
//    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.START|Gravity.LEFT)){
            drawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.left_drawer_item) {
            if(drawerLayout.isDrawerOpen(drawerList)) {
                drawerLayout.closeDrawer(drawerList);
            } else {
                drawerLayout.openDrawer(drawerList);
            }
        } else if(v.getId() == R.id.right_drawer_item) {
            if(drawerLayout.isDrawerOpen(rightDrawer)) {
                drawerLayout.closeDrawer(rightDrawer);
            } else {
                drawerLayout.openDrawer(rightDrawer);
            }
        }
    }

    @Override
    public void onBackStackChanged() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(
                SocialNetworkUtils.SOCIAL_NETWORK_TAG);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void handleFragmentSwitching(int position, Bundle args) {
        Fragment fragment = FragmentFactory.getFragment(
                getSupportFragmentManager(), position);
        if(args != null) {
            fragment.setArguments(args);
        }
        showNewFragment(fragment, fragment.getTag());
    }

    private void moveDrawerToTop() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        drawerLayout = (DrawerLayout)inflater.inflate(R.layout.drawer_decor, null);
        contentLayout = (FrameLayout)drawerLayout.findViewById(R.id.content_frame);

        RelativeLayout barLayout = (RelativeLayout)inflater.inflate(R.layout.action_bar_header, null);
        barLayout.findViewById(R.id.left_drawer_item).setOnClickListener(this);
        barLayout.findViewById(R.id.right_drawer_item).setOnClickListener(this);
        barTitle = (TextView)barLayout.findViewById(R.id.action_bar_title);
        getSupportActionBar().setCustomView(barLayout);

        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        View child = decor.getChildAt(0);
        decor.removeView(child);

        contentLayout.addView(child, 0);

        decor.addView(drawerLayout);

        contentLayout = (FrameLayout)drawerLayout.findViewById(R.id.content_frame);
        drawerList = (RelativeLayout)drawerLayout.findViewById(R.id.left_drawer);
        drawerList.setPadding(0, ViewTools.getStatusBarHeight(this), 0, 0);
        rightDrawer = (ListView)drawerLayout.findViewById(R.id.right_drawer);
    }

    private void initActionBar() {
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
    }

    private void initDrawer() {
//        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout,
//                R.string.action_settings, R.string.action_settings) {
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle(drawerTitle);
//                invalidateOptionsMenu();
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//                getSupportActionBar().setTitle(title);
//                invalidateOptionsMenu();
//            }
//        };

//        titles = getResources().getStringArray(R.array.drawer_menu_items);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
//        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void showNewFragment(Fragment fragment, String fragmentTag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, fragment, fragmentTag);
        if(getFragmentManager().findFragmentByTag(fragmentTag) == null) {
            transaction.addToBackStack(fragmentTag);
        }
        transaction.commit();
    }

    private void initViews() {
        moveDrawerToTop();
        initActionBar();
        initDrawer();
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        switch (position) {
            case 0: {
                handleFragmentSwitching(MainStreamPageFragment.FRAGMENT_ID, null);
                break;
            }
            case 1: {
                handleFragmentSwitching(RubricsFragment.FRAGMENT_ID, null);
                break;
            }
            case 2: {
                Bundle b = new Bundle();
                b.putString(TestFragmet.PARALAX_IMAGE_HEADER_KEY,
                        "http://www.motoplanete.com/honda/zoom-700px/Honda-NC-700-S-2013-700px.jpg");
                handleFragmentSwitching(TestFragmet.FRAGMENT_ID, b);
            }
            default: {
                break;
            }
        }

//        setTitle(titles[position]);
        drawerLayout.closeDrawer(drawerList);
    }
}

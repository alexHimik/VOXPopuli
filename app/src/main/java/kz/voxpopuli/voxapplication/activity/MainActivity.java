package kz.voxpopuli.voxapplication.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.Serializable;

import de.greenrobot.event.EventBus;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.events.CategorySelectedEvent;
import kz.voxpopuli.voxapplication.events.RubricSelectedEvent;
import kz.voxpopuli.voxapplication.fragments.CategoryFragment;
import kz.voxpopuli.voxapplication.fragments.MainStreamPageFragment;
import kz.voxpopuli.voxapplication.fragments.NewsPageFragment;
import kz.voxpopuli.voxapplication.fragments.RubricsFragment;
import kz.voxpopuli.voxapplication.fragments.TestFragmet;
import kz.voxpopuli.voxapplication.network.VolleyNetworkProvider;
import kz.voxpopuli.voxapplication.tools.FragmentFactory;
import kz.voxpopuli.voxapplication.tools.SocialNetworkUtils;
import kz.voxpopuli.voxapplication.tools.ViewTools;


public class MainActivity extends ActionBarActivity implements View.OnClickListener,
        Response.ErrorListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private FrameLayout contentLayout;
    private RelativeLayout drawerList;
    private TextView barTitle;

    private CharSequence title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(new ColorDrawable(
                getResources().getColor(R.color.vox_white)));
        initViews();
//        handleFragmentSwitching(MainStreamPageFragment.FRAGMENT_ID, null);
        handleFragmentSwitching(NewsPageFragment.FRAGMENT_ID, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
        if(barTitle != null) {
            barTitle.setText(this.title);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.START|Gravity.LEFT)){
            drawerLayout.closeDrawers();
            return;
        }
        if(getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportFragmentManager().popBackStack();
            finish();
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.left_drawer_item) {
            togleLeftDrawer();
        } else if(v.getId() == R.id.right_drawer_item) {

        }
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
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
    }

    private void initActionBar() {
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name,
                R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().getCustomView().setVisibility(View.VISIBLE);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().getCustomView().setVisibility(View.INVISIBLE);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void initDrawer() {
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
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

    /** handlers for events from EventBus */
    public void onEvent(CategorySelectedEvent categorySelectedEvent) {
        handleCategoryOrRubricSelection(CategoryFragment.FRAGMENT_ID,
                CategorySelectedEvent.CATEGORY_DATA, categorySelectedEvent);
    }

    public void onEvent(RubricSelectedEvent rubricSelectedEvent) {
        handleCategoryOrRubricSelection(RubricsFragment.FRAGMENT_ID,
                RubricSelectedEvent.RUBRIC_DATA, rubricSelectedEvent);
    }

    /** error handler for network responses from the Volley */
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("MianActivity", "Volley error -> " + error.getMessage());
    }

    private void handleCategoryOrRubricSelection(int fragmentId, String dataKey, Serializable data) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(dataKey, data);
        togleLeftDrawer();
        handleFragmentSwitching(fragmentId, bundle);
    }

    private void togleLeftDrawer() {
        if(drawerLayout.isDrawerOpen(drawerList)) {
            drawerLayout.closeDrawer(drawerList);
        } else {
            drawerLayout.openDrawer(drawerList);
        }
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
            case 3: {
                handleFragmentSwitching(NewsPageFragment.FRAGMENT_ID, null);
            }
            default: {
                break;
            }
        }
        drawerLayout.closeDrawer(drawerList);
    }
}

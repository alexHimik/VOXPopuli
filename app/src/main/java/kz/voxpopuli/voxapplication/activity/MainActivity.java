package kz.voxpopuli.voxapplication.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.Serializable;
import java.net.UnknownHostException;

import de.greenrobot.event.EventBus;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.dialog.QustomDialogBuilder;
import kz.voxpopuli.voxapplication.events.CategorySelectedEvent;
import kz.voxpopuli.voxapplication.events.ErrorEvent;
import kz.voxpopuli.voxapplication.events.RubricSelectedEvent;
import kz.voxpopuli.voxapplication.events.UserAvatarSelectedEvent;
import kz.voxpopuli.voxapplication.fragments.BackStackDataDescriber;
import kz.voxpopuli.voxapplication.fragments.CategoryFragment;
import kz.voxpopuli.voxapplication.fragments.CommentsListFragment;
import kz.voxpopuli.voxapplication.fragments.NewsPageFragment;
import kz.voxpopuli.voxapplication.fragments.RubricsFragment;
import kz.voxpopuli.voxapplication.fragments.SearchFragment;
import kz.voxpopuli.voxapplication.tools.DialogTools;
import kz.voxpopuli.voxapplication.tools.FragmentFactory;
import kz.voxpopuli.voxapplication.tools.ImageUtils;
import kz.voxpopuli.voxapplication.tools.SocialNetworkUtils;
import kz.voxpopuli.voxapplication.tools.ViewTools;


public class MainActivity extends ActionBarActivity implements View.OnClickListener,
        Response.ErrorListener {

    public static final int GET_PICTURE_REQUEST = 111;
    public static final int GET_FILE_CHOICER_REQUEST = 112;
    public static final String IMAGE_PATH_GETTING_ACTION = "captured_image_path_event";
    public static final String CAPTURED_IMAGE_PATH = "image_path";

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private FrameLayout contentLayout;
    private RelativeLayout drawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(new ColorDrawable(
                getResources().getColor(R.color.vox_white)));
        initViews();
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
        if(v.getId() == R.id.left_drawer_item_touch) {
            togleLeftDrawer();
        } else if(v.getId() == R.id.right_drawer_item) {
            handleFragmentSwitching(SearchFragment.FRAGMENT_ID, null);
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

        if(resultCode == RESULT_OK) {
            String filePath = null;
            if(requestCode == GET_PICTURE_REQUEST) {
                filePath = ImageUtils.mCurrentFilePhotoPath;
            } else if(requestCode == GET_FILE_CHOICER_REQUEST) {
                filePath = ImageUtils.getInstance().getFileFromGallery(data.getData(), this);
            }
            if(filePath != null) {
                Intent intent = new Intent(MainActivity.IMAGE_PATH_GETTING_ACTION);
                intent.putExtra(MainActivity.CAPTURED_IMAGE_PATH, filePath);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            }
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
                View customBar = getSupportActionBar().getCustomView();
                if(customBar != null) {
                    customBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                View customBar = getSupportActionBar().getCustomView();
                if(customBar != null) {
                    customBar.setVisibility(View.INVISIBLE);
                }
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void initDrawer() {
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    }

    private void showNewFragment(Fragment fragment, String fragmentTag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(((BackStackDataDescriber)fragment).getFragmentId() == SearchFragment.FRAGMENT_ID) {
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        }
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

    public void onEventMainThread(ErrorEvent errorEvent) {
        DialogTools.showInfoDialog(this, getString(R.string.error_dialog_title),
                errorEvent.getMessage());
    }

    /** error handler for network responses from the Volley */
    @Override
    public void onErrorResponse(VolleyError error) {
        if(error.getCause() instanceof UnknownHostException) {
            DialogTools.showNetworkErrorDialog(this, getString(R.string.error_dialog_title),
                    getString(R.string.network_unreachable_alarm));
        }
    }

    private void handleCategoryOrRubricSelection(int fragmentId, String dataKey, Serializable data) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(dataKey, data);
        if(drawerLayout.isDrawerOpen(drawerList)) {
            drawerLayout.closeDrawer(drawerList);
        }
        clearBackStack();
        handleFragmentSwitching(fragmentId, bundle);
    }

    public void togleLeftDrawer() {
        if(drawerLayout.isDrawerOpen(drawerList)) {
            drawerLayout.closeDrawer(drawerList);
        } else {
            drawerLayout.openDrawer(drawerList);
        }
    }

    private void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}

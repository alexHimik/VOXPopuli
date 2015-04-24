package kz.voxpopuli.voxapplication.activity;

import android.app.AlertDialog;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.Serializable;

import de.greenrobot.event.EventBus;
import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.events.CategorySelectedEvent;
import kz.voxpopuli.voxapplication.events.RubricSelectedEvent;
import kz.voxpopuli.voxapplication.fragments.BackStackDataDescriber;
import kz.voxpopuli.voxapplication.fragments.CategoryFragment;
import kz.voxpopuli.voxapplication.fragments.CommentsListFragment;
import kz.voxpopuli.voxapplication.fragments.NewsPageFragment;
import kz.voxpopuli.voxapplication.fragments.RubricsFragment;
import kz.voxpopuli.voxapplication.fragments.SearchFragment;
import kz.voxpopuli.voxapplication.tools.FragmentFactory;
import kz.voxpopuli.voxapplication.tools.SocialNetworkUtils;
import kz.voxpopuli.voxapplication.tools.ViewTools;


public class MainActivity extends ActionBarActivity implements View.OnClickListener,
        Response.ErrorListener {

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
        handleFragmentSwitching(NewsPageFragment.FRAGMENT_ID,null);
//        readImageBytes();
    }

//    private void readImageBytes() {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.raw.splash);
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
//
//        String str = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
//
//        File file = new File(Environment.getExternalStorageDirectory(), "test_img.png");
//        try {
//            if(!file.exists()) {
//                file.createNewFile();
//            }
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(Base64.decode(str.getBytes(), Base64.DEFAULT));
//        } catch (FileNotFoundException ex) {
//            Log.e("MainActivity", ex.getMessage());
//        } catch (IOException ex) {
//            Log.e("MainActivity", ex.getMessage());
//        }
//    }

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
        if(v.getId() == R.id.left_drawer_item) {
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

    /** error handler for network responses from the Volley */
    @Override
    public void onErrorResponse(VolleyError error) {
        AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
        errorDialog.setTitle("Error!");
        errorDialog.setMessage(error.getMessage());
        errorDialog.create().show();
        Log.e("MianActivity", "Volley error -> " + error.getMessage());
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

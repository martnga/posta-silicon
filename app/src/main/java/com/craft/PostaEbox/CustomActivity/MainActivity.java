package com.craft.PostaEbox.CustomActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.craft.PostaEbox.Fragments.Home;
import com.craft.PostaEbox.Fragments.Providers;
import com.craft.PostaEbox.Fragments.eBox;
import com.craft.PostaEbox.R;
import com.craft.PostaEbox.Utils.DrawerAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    FrameLayout content_frame;
    DrawerAdapter mAdapter;
    String userphone;
    public static final String PHONE_NUMBER = "mobile";

    private ActionBarDrawerToggle mDrawerToggle;
    String[] DrawerMenu = {"Home", "eBox", "Providers","Wallet","Settings","LogOut"};
    int [] DrawerIcons={R.drawable.home,R.drawable.box,R.drawable.billers,R.drawable.wallet,R.drawable.settings,R.drawable.logout};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Getting phone number from shared prefences
        SharedPreferences prefs = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        userphone = prefs.getString(PHONE_NUMBER, "");

        //Take User to login page if not registered
        if(userphone.isEmpty()){
            Intent i = new Intent(MainActivity.this, RootActivity.class);
            startActivity(i);
        }
        setContentView(R.layout.activity_main);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        content_frame = (FrameLayout) findViewById(R.id.content_frame);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        addDrawerItems();
        setupDrawer();
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getSupportActionBar().setTitle(DrawerMenu[position]);
                displayView(position);
            }
        });
    }

    private void addDrawerItems() {
        mAdapter = new DrawerAdapter(MainActivity.this,DrawerMenu,DrawerIcons);
        mDrawerList.setAdapter(mAdapter);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle("Home");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return true;
    }

    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Home();
                break;

            case 1:
                fragment = new eBox();
                break;

            case 2:
                fragment = new Providers();
                break;
            case 3:
                this.finish();
                break;
        }
        if(fragment!=null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(content_frame.getId(), fragment).commit();
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(DrawerMenu[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

}

package com.agarwal.ashi.upes_app;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton fab;
    DrawerLayout drawer;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        /* Setting the the action bar */
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(2);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Workshops"));
        tabLayout.addTab(tabLayout.newTab().setText("Seminars"));
        tabLayout.addTab(tabLayout.newTab().setText("Competitions"));
        tabLayout.addTab(tabLayout.newTab().setText("Cultural"));
        tabLayout.addTab(tabLayout.newTab().setText("Sports"));
        tabLayout.addTab(tabLayout.newTab().setText("Webminars"));
        window=getWindow();

        /*retrieving an instance of ViewPager */
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        /* setting the tab layout with the view pager */
        tabLayout.setupWithViewPager(viewPager);



        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        /*Setting the overflow icon as calender icon */
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_date_range_black_24dp);
        toolbar.setOverflowIcon(drawable);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //setting the listener for navigation view
        navigationView.setNavigationItemSelectedListener(this);

        /* Setting the default layout colour based on the user choice in the
           SchoolSelectActivity
         */
        SharedPreferences spref=getSharedPreferences("com.agarwal.ashi.upes_app.choice",Context.MODE_PRIVATE);
        String choice=spref.getString("choice",null);
        System.out.println(choice);
        setUILayout(choice);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_calenderview) {
            Intent intent=new Intent(MainActivity.this,CalenderView.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        setUILayout(item.getItemId());
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setUILayout(String desc) {
        switch(desc) {
            case "home" :
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                viewPager.setAdapter(new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),
                                     R.color.colorPrimary));
                System.out.println(R.color.colorPrimary);
                break;

            case "socs" :
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.socs)));
                tabLayout.setBackgroundColor(getResources().getColor(R.color.socs));
                window.setStatusBarColor(getResources().getColor(R.color.soce_dark));
                viewPager.setAdapter(new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),
                        R.color.socs));
                System.out.println(R.color.socs);
                break;

            case "soe" :
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.soe)));
                tabLayout.setBackgroundColor(getResources().getColor(R.color.soe));
                window.setStatusBarColor(getResources().getColor(R.color.soe_dark));
                viewPager.setAdapter(new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),
                        R.color.soe));
                System.out.println(R.color.soe);
                break;

            case "sob" :
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sob)));
                tabLayout.setBackgroundColor(getResources().getColor(R.color.sob));
                window.setStatusBarColor(getResources().getColor(R.color.sob_dark));
                viewPager.setAdapter(new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),
                        R.color.sob));
                System.out.println(R.color.sob);
                break;

            case "sod" :
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sod)));
                tabLayout.setBackgroundColor(getResources().getColor(R.color.sod));
                window.setStatusBarColor(getResources().getColor(R.color.sod_dark));
                viewPager.setAdapter(new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),
                        R.color.sod));
                System.out.println(R.color.sod);
                break;

            case "sol" :
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sol)));
                tabLayout.setBackgroundColor(getResources().getColor(R.color.sol));
                window.setStatusBarColor(getResources().getColor(R.color.sol_dark));
                viewPager.setAdapter(new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),
                        R.color.sol));
                System.out.println(R.color.sol);
                break;

            default :
                break;
        }
    }

    private void setUILayout(int menuId) {
        switch(menuId) {
            case R.id.home :
                setUILayout("home");
                break;

            case R.id.socs :
                setUILayout("socs");
                break;

            case R.id.soe :
                setUILayout("soe");
                break;

            case R.id.sob :
                setUILayout("sob");
                break;

            case R.id.sod :
                setUILayout("sod");
                break;

            case R.id.sol :
                setUILayout("sol");
                break;

            default :
                break;
        }
    }
}

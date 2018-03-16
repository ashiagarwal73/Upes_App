package com.agarwal.ashi.upes_app;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity
        implements ExpandableListView.OnGroupClickListener,
                   ExpandableListView.OnChildClickListener,
                   ExpandableListView.OnGroupExpandListener {
    ExpandableListView expandableListView;
    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton fab;
    DrawerLayout drawer;
    Window window;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference eventsDataReference;
    DatabaseReference societyReference;
    DatabaseReference schoolReference;
    long selectedGroupID;
    ArrayList<EventsInformation> events=new ArrayList();
    ArrayList<Society> societies=new ArrayList();
    ArrayList<School> schools=new ArrayList();
    ArrayList<String> menuNames=new ArrayList();
    NavigationMenuAdapter navMenuAdapter;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainv2);
//*********************************************** Firebase part **********************************************************
        System.out.println("Firebase part started\n");
        eventsDataReference=firebaseDatabase.getReference("EventsDetails");
        societyReference=firebaseDatabase.getReference("Society");
        schoolReference=firebaseDatabase.getReference("School");

        eventsDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("tag","onDataChange() called");
                System.out.println("ondatachange called");
                events=new ArrayList<>();
                for (DataSnapshot q:dataSnapshot.getChildren()) {
                    Log.i("tag","for loop running");
                    events.add(q.getValue(EventsInformation.class));
                }
                setSchoolData(selectedGroupID,events);
                Log.i("tag","events size : "+events.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        schoolReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                schools=new ArrayList<>();
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    schools.add(ds.getValue(School.class));
                }
                prepareNavigationMenu();
                navMenuAdapter.setSchools(schools);
                navMenuAdapter.setMenuNames(menuNames);
                navMenuAdapter.notifyDataSetChanged();
                System.out.println("schools size on datachange : "+schools.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        societyReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("tag","onDataChange for societies called");
                societies=new ArrayList<Society>();
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    System.out.println("socities reference datachange for loop running");
                    societies.add(ds.getValue(Society.class));
                }
                prepareNavigationMenu();
                navMenuAdapter.setSocieties(societies);
                navMenuAdapter.setMenuNames(menuNames);
                navMenuAdapter.notifyDataSetChanged();
                System.out.println("socities size on datachange : "+societies.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        System.out.println("adding of listeners complete");
//***********************************************Firebase part End ************************************************************
//*******************************************************************************Test*****************************************
        prepareNavigationMenu();
        navMenuAdapter=new NavigationMenuAdapter(this,schools,societies,menuNames);
        expandableListView=(ExpandableListView)findViewById(R.id.expandableListView);
        expandableListView.setAdapter(navMenuAdapter);
        expandableListView.setOnGroupClickListener(this);
        expandableListView.setOnChildClickListener(this);

        //expandableListView.setOnGroupExpandListener(this);
//**************************************************************************Test******************************************

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

   //     navigationView = (NavigationView) findViewById(R.id.nav_view);
        //setting the listener for navigation view
    //    navigationView.setNavigationItemSelectedListener(this);

        /* Setting the default layout colour based on the user choice in the
           SchoolSelectActivity
         */
        SharedPreferences spref=getSharedPreferences("com.agarwal.ashi.upes_app.choice",Context.MODE_PRIVATE);
        String choice=spref.getString("choice",null);
        System.out.println(choice);
        setSchoolData(choice,events);
    }

    private void prepareNavigationMenu() {
        menuNames=new ArrayList<>();
        menuNames.add("Home");
        for(int i=0;i<schools.size();i++) {
            menuNames.add(schools.get(i).getName());
        }
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

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        // Handle ExpandableListView item click events here
        setSchoolData(id,events);
        parent.setSelectedGroup(groupPosition);
        return false; //click was not completely handled;
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int i1, long l) {
        Log.i("tag","onchildclick");
        ConstraintLayout container=(ConstraintLayout)view;
        TextView tV=(TextView)container.getChildAt(0);
        String societyName=(String)tV.getText();
        Log.i("tag","onChildClick : "+tV.getText());
        drawer.closeDrawer(Gravity.START);
        ArrayList<EventsInformation> evtodisplay=new ArrayList();
        for(int i=0;i<events.size();i++) {
            EventsInformation temp=events.get(i);
            if(societyName.equalsIgnoreCase(temp.getSociety())) {
                evtodisplay.add(temp);
                Log.i("tag","events : "+temp.getEventName());
            }
        }
        setSchoolData(selectedGroupID,evtodisplay);
        return false;
    }


    @Override
    public void onGroupExpand(int groupPosition) {
        Log.i("tag","expand");
        for(int i=0;i<6;i++) {
            if(i==groupPosition)
                highlight((ConstraintLayout)expandableListView.getChildAt(groupPosition),true);
            //else
                highlight((ConstraintLayout)expandableListView.getChildAt(i),false);
        }
    }

    private void highlight(ConstraintLayout container,boolean highlight) {
        ImageView icon=(ImageView)container.getChildAt(0);
        TextView tV=(TextView)container.getChildAt(1);
        ImageView arrow=(ImageView)container.getChildAt(2);
        /*ColorStateList foreground;
        if(highlight){
            tV.setTextColor(getResources().getColor(R.color.foreground_highlight_color));
            foreground=ColorStateList.valueOf(getResources().getColor(R.color.foreground_highlight_color));
            container.setBackgroundColor(getResources().getColor(R.color.highlight_color));
        }
        else {
            tV.setTextColor(getResources().getColor(R.color.highlight_color));
            foreground=ColorStateList.valueOf(getResources().getColor(R.color.highlight_color));
            container.setBackgroundColor(getResources().getColor(R.color.foreground_highlight_color));
        }
        icon.setImageTintList(foreground);
        arrow.setImageTintList(foreground);*/
    }

    private void setSchoolData(String desc,ArrayList<EventsInformation> events) {
        com.agarwal.ashi.upes_app.PagerAdapter pagerAdapter;
        switch(desc) {
            case "home" :
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                pagerAdapter=new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),
                        R.color.colorPrimary,events);
                viewPager.setAdapter(pagerAdapter);
                pagerAdapter.notifyDataSetChanged();
                System.out.println(R.color.colorPrimary);
                break;

            case "socs" :
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.socs)));
                tabLayout.setBackgroundColor(getResources().getColor(R.color.socs));
                window.setStatusBarColor(getResources().getColor(R.color.soce_dark));
                pagerAdapter=new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),
                        R.color.socs,events);
                viewPager.setAdapter(pagerAdapter);
                pagerAdapter.notifyDataSetChanged();
                System.out.println(R.color.socs);
                break;

            case "soe" :
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.soe)));
                tabLayout.setBackgroundColor(getResources().getColor(R.color.soe));
                window.setStatusBarColor(getResources().getColor(R.color.soe_dark));
                pagerAdapter=new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),
                        R.color.soe,events);
                viewPager.setAdapter(pagerAdapter);
                pagerAdapter.notifyDataSetChanged();
                System.out.println(R.color.soe);
                break;

            case "sob" :
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sob)));
                tabLayout.setBackgroundColor(getResources().getColor(R.color.sob));
                window.setStatusBarColor(getResources().getColor(R.color.sob_dark));
                pagerAdapter=new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),
                        R.color.sob,events);
                viewPager.setAdapter(pagerAdapter);
                pagerAdapter.notifyDataSetChanged();
                System.out.println(R.color.sob);
                break;

            case "sod" :
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sod)));
                tabLayout.setBackgroundColor(getResources().getColor(R.color.sod));
                window.setStatusBarColor(getResources().getColor(R.color.sod_dark));
                pagerAdapter=new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),
                        R.color.sod,events);
                viewPager.setAdapter(pagerAdapter);
                pagerAdapter.notifyDataSetChanged();
                System.out.println(R.color.sod);
                break;

            case "sol" :
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sol)));
                tabLayout.setBackgroundColor(getResources().getColor(R.color.sol));
                window.setStatusBarColor(getResources().getColor(R.color.sol_dark));
                pagerAdapter=new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),
                        R.color.sol,events);
                viewPager.setAdapter(pagerAdapter);
                pagerAdapter.notifyDataSetChanged();
                System.out.println(R.color.sol);
                break;

            default :
                break;
        }
    }

    private void setSchoolData(long id,ArrayList<EventsInformation> events) {
        this.selectedGroupID=id;
        if(id==getResources().getInteger(R.integer.home))
                setSchoolData("home",events);
        else if(id==getResources().getInteger(R.integer.socs))
                setSchoolData("socs",events);
        else if(id==getResources().getInteger(R.integer.soe))
                setSchoolData("soe",events);
        else if(id==getResources().getInteger(R.integer.sob))
                setSchoolData("sob",events);
        else if(id==getResources().getInteger(R.integer.sol))
                setSchoolData("sod",events);
        else if(id==getResources().getInteger(R.integer.sod))
                setSchoolData("sol",events);
    }

}

package com.agarwal.ashi.upes_app.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.agarwal.ashi.upes_app.ConnectionBroadCastReceiver;
import com.agarwal.ashi.upes_app.adapter.NavigationMenuAdapter;
import com.agarwal.ashi.upes_app.R;
import com.agarwal.ashi.upes_app.pojo.Counter;
import com.agarwal.ashi.upes_app.pojo.EventsInformation;
import com.agarwal.ashi.upes_app.pojo.School;
import com.agarwal.ashi.upes_app.pojo.Society;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements ExpandableListView.OnGroupClickListener,
                   ExpandableListView.OnChildClickListener,
                   ExpandableListView.OnGroupExpandListener,
                   ExpandableListView.OnGroupCollapseListener{
    private static final String CHANNEL1_ID="CHANNEL 1";
    ExpandableListView expandableListView;
    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton fab;
    DrawerLayout drawer;
    Window window;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference rootReference;
    DatabaseReference societyReference;
    DatabaseReference schoolReference;
    long selectedGroupID;
    String selectedGroupName;
    ArrayList<EventsInformation> events=new ArrayList();
    ArrayList<Society> societies=new ArrayList();
    ArrayList<School> schools=new ArrayList();
    ArrayList<String> menuNames=new ArrayList();
    NavigationMenuAdapter navMenuAdapter;
    public TextView noconnection;
    private Counter counter=new Counter();
    ProgressBar progressBar;
    ConnectionBroadCastReceiver cbcr;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainv2);
        createNotificationChannel(CHANNEL1_ID);

        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        cbcr=new ConnectionBroadCastReceiver();
        registerReceiver(cbcr,filter);

        noconnection=(TextView)findViewById(R.id.noconnection);
        progressBar=(ProgressBar)findViewById(R.id.prgressbar2);
        progressBar.setVisibility(View.VISIBLE);
//*********************************************** Firebase part **********************************************************
        System.out.println("Firebase part started\n");
        rootReference=firebaseDatabase.getReference();
        societyReference=firebaseDatabase.getReference("Society");
        schoolReference=firebaseDatabase.getReference("School");

        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("tag","onDataChange() called");
                System.out.println("ondatachange called");
                events=new ArrayList<>();
                for (DataSnapshot q:dataSnapshot.child("EventsDetails").getChildren()) {
                    Log.i("tag","for loop running");
                    events.add(q.getValue(EventsInformation.class));
                }
                displayEvents(selectedGroupID,getEventsbasedOnSchool(selectedGroupName));
                Log.i("tag","events size : "+events.size());
                //noconnection.setVisibility(View.GONE);
                if(events.size()-counter.getCounter()==1)
                    notifyForNewEvent(events.get(events.size()-1),1);
                counter.setCounter(events.size());
                progressBar.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
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

        expandableListView.setOnGroupExpandListener(this);
        expandableListView.setOnGroupCollapseListener(this);
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
        displayEvents(choice,getEventsbasedOnSchool(choice));
        selectedGroupID=findGroupId(choice);
    }

    public void setConnectivityStatus(boolean isConnected) {
        if(!isConnected) {
            noconnection.setText(getString(R.string.noconnection));
            noconnection.setVisibility(View.VISIBLE);
        }
        else
            noconnection.setVisibility(View.GONE);
    }

    private long findGroupId(String groupName) {
        if(groupName.equalsIgnoreCase(getString(R.string.home)))
            return getResources().getInteger(R.integer.home);
        else if(groupName.equalsIgnoreCase(getString(R.string.socs)))
            return getResources().getInteger(R.integer.socs);
        else if(groupName.equalsIgnoreCase(getString(R.string.soe)))
            return getResources().getInteger(R.integer.soe);
        else if(groupName.equalsIgnoreCase(getString(R.string.sob)))
            return getResources().getInteger(R.integer.sob);
        else if(groupName.equalsIgnoreCase(getString(R.string.sol)))
            return getResources().getInteger(R.integer.sol);
        else if(groupName.equalsIgnoreCase(getString(R.string.home)))
            return getResources().getInteger(R.integer.sod);
        else
            return -1;
    }
    private String findGroupName(long id) {
        if(id==getResources().getInteger(R.integer.home))
            return getString(R.string.home);
        else if(id==getResources().getInteger(R.integer.socs))
            return getString(R.string.socs);
        else if(id==getResources().getInteger(R.integer.soe))
            return getString(R.string.soe);
        else if(id==getResources().getInteger(R.integer.sob))
            return getString(R.string.sob);
        else if(id==getResources().getInteger(R.integer.sol))
            return getString(R.string.sol);
        else if(id==getResources().getInteger(R.integer.sod))
            return getString(R.string.sod);
        else return null;
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
        ConstraintLayout container=(ConstraintLayout)v;
        TextView tV=(TextView)container.getChildAt(1);
        displayEvents(id,getEventsbasedOnSchool((String)tV.getText()));
        parent.setSelectedGroup(groupPosition);
        ViewGroup vg=(ViewGroup)v;
        if(navMenuAdapter.getChildrenCount(groupPosition)==0)
            drawer.closeDrawer(Gravity.START);
        return false; //click was not completely handled;
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int i1, long l) {
        Log.i("tag","onchildclick");
        ConstraintLayout container=(ConstraintLayout)view;
        TextView tV=(TextView)container.getChildAt(0);
        Log.i("tag","onChildClick : "+tV.getText());
        drawer.closeDrawer(Gravity.START);

        School school=(School)navMenuAdapter.getGroup(groupPosition);
        displayEvents(selectedGroupID,getEventsbasedOnSociety(school.getName(),(String)tV.getText()));
        return false;
    }

    private ArrayList<EventsInformation> getEventsbasedOnSchool(String schoolName) {
        ArrayList<EventsInformation> evtodisplay=new ArrayList();
        Log.i("tag","schoolName  : "+schoolName);
        for(int i=0;i<events.size();i++) {
            EventsInformation temp=events.get(i);
            Log.i("temm.getschool()","temp.getschool : "+temp.getSchool());

            if(schoolName.equalsIgnoreCase(temp.getSchool())) {
                evtodisplay.add(temp);
                Log.i("tag","geteventsbasedonschool : "+temp.getEventName());
            }
        }
        return evtodisplay;
    }
    private ArrayList<EventsInformation> getEventsbasedOnSociety(String schoolName,String societyName) {
        Log.i("tag","geteventsbasedonsociety");
        ArrayList<EventsInformation> evtodisplay=new ArrayList();
        Log.i("tag","societyName  : "+societyName);
        for(int i=0;i<events.size();i++) {
            EventsInformation temp=events.get(i);
            Log.i("temm.getsociety()","temp.getsociety : "+temp.getSociety());

            if(societyName.equalsIgnoreCase(temp.getSociety()) && schoolName.equalsIgnoreCase(temp.getSchool())) {
                evtodisplay.add(temp);
                Log.i("tag","getevtodisplaybasedonsociety : true "+temp.getEventName());
            }
        }
        return evtodisplay;
    }


    private void displayEvents(String schoolName,ArrayList<EventsInformation> evtodisplay) {
        this.selectedGroupName=schoolName;
        com.agarwal.ashi.upes_app.PagerAdapter pagerAdapter;
        if(schoolName.equalsIgnoreCase(getString(R.string.home))) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
            getSupportActionBar().setTitle("Home");
            tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            pagerAdapter = new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),
                    R.color.colorPrimary, events);
            viewPager.setAdapter(pagerAdapter);
            pagerAdapter.notifyDataSetChanged();
            System.out.println(R.color.colorPrimary);
        }
        else if(schoolName.equalsIgnoreCase(getString(R.string.socs))) {
            getSupportActionBar().setTitle("School of Computer Science");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.socs)));
            tabLayout.setBackgroundColor(getResources().getColor(R.color.socs));
            window.setStatusBarColor(getResources().getColor(R.color.soce_dark));
            pagerAdapter = new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),
                    R.color.socs, evtodisplay);
            viewPager.setAdapter(pagerAdapter);
            pagerAdapter.notifyDataSetChanged();
            System.out.println(R.color.socs);
        }
        else if(schoolName.equalsIgnoreCase(getString(R.string.soe))) {
            getSupportActionBar().setTitle("School of Engineering");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.soe)));
            tabLayout.setBackgroundColor(getResources().getColor(R.color.soe));
            window.setStatusBarColor(getResources().getColor(R.color.soe_dark));
            pagerAdapter = new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),
                    R.color.soe, evtodisplay);
            viewPager.setAdapter(pagerAdapter);
            pagerAdapter.notifyDataSetChanged();
            System.out.println(R.color.soe);
        }
        else if(schoolName.equalsIgnoreCase(getString(R.string.sob))) {
            getSupportActionBar().setTitle("School of Business");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sob)));
            tabLayout.setBackgroundColor(getResources().getColor(R.color.sob));
            window.setStatusBarColor(getResources().getColor(R.color.sob_dark));
            pagerAdapter = new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),
                    R.color.sob, evtodisplay);
            viewPager.setAdapter(pagerAdapter);
            pagerAdapter.notifyDataSetChanged();
            System.out.println(R.color.sob);
        }
        else if(schoolName.equalsIgnoreCase(getString(R.string.sod))) {
            getSupportActionBar().setTitle("School of Design");
            getSupportActionBar().setTitle("School of Design");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sod)));
            tabLayout.setBackgroundColor(getResources().getColor(R.color.sod));
            window.setStatusBarColor(getResources().getColor(R.color.sod_dark));
            pagerAdapter = new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),
                    R.color.sod, evtodisplay);
            viewPager.setAdapter(pagerAdapter);
            pagerAdapter.notifyDataSetChanged();
            System.out.println(R.color.sod);
        }
        else if(schoolName.equalsIgnoreCase(getString(R.string.sol))) {
            getSupportActionBar().setTitle("School of Law");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sol)));
            tabLayout.setBackgroundColor(getResources().getColor(R.color.sol));
            window.setStatusBarColor(getResources().getColor(R.color.sol_dark));
            pagerAdapter = new com.agarwal.ashi.upes_app.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),
                    R.color.sol, evtodisplay);
            viewPager.setAdapter(pagerAdapter);
            pagerAdapter.notifyDataSetChanged();
            System.out.println(R.color.sol);
        }
        else
            Log.i("MainActivity","displayEvents : invalid choice");
    }

    private void displayEvents(long id,ArrayList<EventsInformation> evtodisplay) {
        this.selectedGroupID=id;
        if(id==getResources().getInteger(R.integer.home))
                displayEvents(getString(R.string.home),evtodisplay);
        else if(id==getResources().getInteger(R.integer.socs))
                displayEvents(getString(R.string.socs),evtodisplay);
        else if(id==getResources().getInteger(R.integer.soe))
                displayEvents(getString(R.string.soe),evtodisplay);
        else if(id==getResources().getInteger(R.integer.sob))
                displayEvents(getString(R.string.sob),evtodisplay);
        else if(id==getResources().getInteger(R.integer.sol))
                displayEvents(getString(R.string.sol),evtodisplay);
        else if(id==getResources().getInteger(R.integer.sod))
                displayEvents(getString(R.string.sod),evtodisplay);
    }

    @Override
    public void onGroupCollapse(int groupPosition) { /*
        ViewGroup container=(ConstraintLayout)expandableListView.getChildAt(groupPosition);
        if(container==null) {
            Log.i("tag","collapse container null");
            return;
        }
        Log.i("tag","no of children : "+container.getChildCount());
        ImageView arrow=(ImageView)container.getChildAt(2);
        if(arrow.getVisibility()!=View.INVISIBLE)
            arrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_expand));
        Log.i("tag","ongroupcollapse called"); */
    }

    @Override
    public void onGroupExpand(int groupPosition) { /*
        ViewGroup container=(ConstraintLayout)expandableListView.getChildAt(groupPosition);
        if(container==null) {
            Log.i("tag","expand container null");
            return;
        }
        Log.i("tag","no of children : "+container.getChildCount());
        ImageView arrow=(ImageView)container.getChildAt(2);
        if(arrow.getVisibility()!=View.INVISIBLE)
            arrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_collapse));
        Log.i("tag","ongroupexpand called"); */
    }

    private void createNotificationChannel(String channelId) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            String name=channelId;
            String desc="Channel "+name;
            int importance= NotificationManagerCompat.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name,importance);
            NotificationManager notificationManager = (NotificationManager) getSystemService(
                    NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private int getSchoolColorId(String schoolName) {
        int actionbarColorId=0;
        if(schoolName.equalsIgnoreCase(getString(R.string.home)))
            actionbarColorId=R.integer.home;
        else if(schoolName.equalsIgnoreCase(getString(R.string.socs)))
            actionbarColorId=R.integer.socs;
        else if(schoolName.equalsIgnoreCase(getString(R.string.soe)))
            actionbarColorId=R.integer.soe;
        else if(schoolName.equalsIgnoreCase(getString(R.string.sob)))
            actionbarColorId=R.integer.sob;
        else if(schoolName.equalsIgnoreCase(getString(R.string.sol)))
            actionbarColorId=R.integer.sol;
        else if(schoolName.equalsIgnoreCase(getString(R.string.sod)))
            actionbarColorId=R.integer.sod;
        return actionbarColorId;
    }

    private void notifyForNewEvent(EventsInformation event,int notificationId) {
        Intent notifyIntent = new Intent(this, EventDetailsActivity.class);
        notifyIntent.putExtra("event",event);
        notifyIntent.putExtra("actionbarColorId",getSchoolColorId(event.getSchool()));
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL1_ID)
                .setSmallIcon(R.drawable.ic_school_black_24dp)
                .setContentTitle(event.getEventName())
                .setContentText(event.getSociety()+" has posted a new event")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notifyPendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, builder.build());
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(cbcr);
        super.onDestroy();
    }
}

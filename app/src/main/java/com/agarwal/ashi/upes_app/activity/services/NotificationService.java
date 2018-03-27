package com.agarwal.ashi.upes_app.activity.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;

import com.agarwal.ashi.upes_app.R;
import com.agarwal.ashi.upes_app.activity.EventDetailsActivity;
import com.agarwal.ashi.upes_app.pojo.Counter;
import com.agarwal.ashi.upes_app.pojo.EventsInformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationService extends Service implements ValueEventListener {
    private static final String CHANNEL1_ID="CHANNEL 1";
    public NotificationService() {
        createNotificationChannel(CHANNEL1_ID);
    }
    Runnable runnable;
    Handler handler;
    Counter counter;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference rootReference;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startID) {
        rootReference=firebaseDatabase.getReference();
        counter=new Counter();
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                Log.i("tag","service running");
                rootReference.addValueEventListener(NotificationService.this);
                handler.postDelayed(runnable,1000);
            }
        };
        handler.postDelayed(runnable,1000);
        return START_STICKY;
    }
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.i("tag","onDataChange() called");
        System.out.println("ondatachange called");
        ArrayList<EventsInformation> events=new ArrayList<>();
        for (DataSnapshot q:dataSnapshot.child("EventsDetails").getChildren()) {
            Log.i("tag","for loop running");
            events.add(q.getValue(EventsInformation.class));
        }
        Log.i("tag","events size : "+events.size());
        //noconnection.setVisibility(View.GONE);
        if(events.size()-counter.getCounter()==1)
            notifyForNewEvent(events.get(0),1);
        counter.setCounter(events.size());
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
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
            actionbarColorId=R.color.colorPrimary;
        else if(schoolName.equalsIgnoreCase(getString(R.string.socs)))
            actionbarColorId=R.color.socs;
        else if(schoolName.equalsIgnoreCase(getString(R.string.soe)))
            actionbarColorId=R.color.soe;
        else if(schoolName.equalsIgnoreCase(getString(R.string.sob)))
            actionbarColorId=R.color.sob;
        else if(schoolName.equalsIgnoreCase(getString(R.string.sol)))
            actionbarColorId=R.color.sol;
        else if(schoolName.equalsIgnoreCase(getString(R.string.sod)))
            actionbarColorId=R.color.sod;
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

        Log.i("tag","notification : "+event.getEventName());
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
}

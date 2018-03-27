package com.agarwal.ashi.upes_app.activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.agarwal.ashi.upes_app.R;
import com.agarwal.ashi.upes_app.pojo.EventsInformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CalenderView extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference eventsDataReference;
    ArrayList<EventsInformation> events=new ArrayList();
    int datef;
    int monthf;
    int yearf;
    TextView textView,textView2;
    String s;
    String output[]=new String[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_view);
        textView=findViewById(R.id.eventsofthisday);
        textView2=findViewById(R.id.events);

        CalendarView calendarView=findViewById(R.id.calenderview);

        //********************Firebase**************************
        eventsDataReference=firebaseDatabase.getReference("EventsDetails");


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view,final int year, final int month, final int dayOfMonth) {
                eventsDataReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.i("tag","onDataChange() called");
                        System.out.println("ondatachange called");
                        events=new ArrayList<>();
                        for (DataSnapshot q:dataSnapshot.getChildren()) {
                            Log.i("tag","for loop running");
                            events.add(q.getValue(EventsInformation.class));}
                            for(int i=0;i<events.size();i++) {
                                s=events.get(i).getDate();
                                output=s.split("/"); //dikkat kaha hai ?WAI SOCH RHI HUARRAY INITIALISE KAHA KRI
                                datef=Integer.parseInt(output[0]);
                                monthf=Integer.parseInt(output[1]);
                                yearf=Integer.parseInt(output[2]);
                                if(year==yearf&&month==monthf-1&&dayOfMonth==datef) {
                                    textView.setVisibility(View.VISIBLE);
                                    textView2.setVisibility(View.VISIBLE);
                                    textView2.setText(events.get(i).getEventName());
                                    for(int j=i;j<events.size();j++) {
                                        s=events.get(j).getDate();
                                        output=s.split("/"); //dikkat kaha hai ?WAI SOCH RHI HUARRAY INITIALISE KAHA KRI
                                        datef=Integer.parseInt(output[0]);
                                        monthf=Integer.parseInt(output[1]);
                                        yearf=Integer.parseInt(output[2]);
                                        if(year==yearf&&month==monthf-1&&dayOfMonth==datef) {
                                            if(i!=j)
                                            {
                                            textView2.setText(textView2.getText().toString()+"\n"+events.get(j).getEventName());
                                            }
                                        }
                                    }
                                            break;
                                }
                                else {
                                    textView.setVisibility(View.INVISIBLE);
                                    textView2.setVisibility(View.INVISIBLE);
                                }



                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                //Toast.makeText(CalenderView.this, ""+dayOfMonth+" "+month+" "+year, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
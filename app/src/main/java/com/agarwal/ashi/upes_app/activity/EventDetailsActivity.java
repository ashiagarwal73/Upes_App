package com.agarwal.ashi.upes_app.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.agarwal.ashi.upes_app.adapter.CustomAdapter;
import com.agarwal.ashi.upes_app.adapter.NavigationMenuAdapter;
import com.agarwal.ashi.upes_app.pojo.EventsInformation;
import com.agarwal.ashi.upes_app.pojo.LayoutInformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.agarwal.ashi.upes_app.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

public class EventDetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    SlidingUpPanelLayout mLayout;
    private static final String TAG = "DemoActivity";
    Thread myThread;
    ImageView imageView;
    ListView list;
    TextView description,time,venue;
    Window window;
    private EventsInformation event;
    private LayoutInformation layoutInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        setContentView(R.layout.activity_event_details_v2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layoutInformation=bundle.getParcelable("layoutinformation");
        event=bundle.getParcelable("event");
        window=getWindow();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(layoutInformation.getLayoutColorLight())));
        window.setStatusBarColor(getResources().getColor(layoutInformation.getLayoutColorDark()));
        list=findViewById(R.id.list);
        /*description=(TextView)findViewById(R.id.description);
        time=findViewById(R.id.time);
        venue=findViewById(R.id.venue);
        description.setText(event.getEventDescription());
        time.setText("Time of Event"+event.getTime());
        venue.setText("Venue of Event"+event.getVenue());*/
        String[] arrayList=new String[3];
        arrayList[0]=("Description\n"+event.getEventDescription());
        arrayList[1]=("Time of Event:  "+event.getTime());
        arrayList[2]=("Venue of Event:  "+event.getVenue());
        CustomAdapter customAdapter=new CustomAdapter(EventDetailsActivity.this,arrayList);
        list.setAdapter(customAdapter);
        //obtaining instance of SlidingUpPanelLayout
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }
            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);


            }
        });
        imageView=findViewById(R.id.imageView2);
        Glide.with(this)
             .load(event.getImage())
             .apply(new RequestOptions().placeholder(R.drawable.ic_action_picture).error(R.drawable.ic_action_picture))
             .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

            }
        });
        /*myThread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(500);
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);

                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }

            }
        };*/
//        myThread.start();
        mLayout.setAnchorPoint(.6f);
        mLayout.setPanelHeight(100);
    }
    public void onContactClick(View v)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+event.getContact()));
        startActivity(intent);
    }
}

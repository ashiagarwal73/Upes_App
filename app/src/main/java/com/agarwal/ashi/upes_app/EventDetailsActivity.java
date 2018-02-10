package com.agarwal.ashi.upes_app;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class EventDetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    SlidingUpPanelLayout mLayout;
    private static final String TAG = "DemoActivity";
    Thread myThread;
    ImageView imageView;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        setContentView(R.layout.activity_event_details_v2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int actionbarColorId=bundle.getInt("actionbarColorId");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(actionbarColorId)));
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
        myThread = new Thread()
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
        };
        myThread.start();
        mLayout.setAnchorPoint(.6f);
        mLayout.setPanelHeight(70);
    }
}

package com.agarwal.ashi.upes_app;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class EventDetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        setContentView(R.layout.activity_event_details_v2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int actionbarColorId=bundle.getInt("actionbarColorId");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(actionbarColorId)));
    }
}

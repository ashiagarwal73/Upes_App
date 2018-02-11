package com.agarwal.ashi.upes_app;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

public class CalenderView extends AppCompatActivity {
    TextView textView,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_view);
        textView=findViewById(R.id.eventsofthisday);
        textView2=findViewById(R.id.events);
        CalendarView calendarView=findViewById(R.id.calenderview);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if(year==2018&&month==1&&dayOfMonth==5) {
                    textView.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.VISIBLE);
                    textView2.setText("Capture the Flag\nBraille-Code");
                }
                else {
                    textView.setVisibility(View.INVISIBLE);
                    textView2.setVisibility(View.INVISIBLE);

                }
                //Toast.makeText(CalenderView.this, ""+dayOfMonth+" "+month+" "+year, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

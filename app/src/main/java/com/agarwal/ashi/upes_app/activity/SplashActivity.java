package com.agarwal.ashi.upes_app.activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.agarwal.ashi.upes_app.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.i("splash","splash activity");
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                SharedPreferences spref=getSharedPreferences("com.agarwal.ashi.upes_app.choice",Context.MODE_PRIVATE);
                final String choice=spref.getString("choice",null);
                System.out.println("Choice "+choice);
                Log.i("splash activity","choice : "+choice);
                if(choice!=null) {
                    Intent i=new Intent(SplashActivity.this,MainActivity.class);
                    i.putExtra("choice",choice);
                    startActivity(i);
                }
                else {
                    startActivity(new Intent(SplashActivity.this, SchoolSelectActivity.class));
                }
                SplashActivity.this.finish();
            }
        },1000);
    }
}

package com.agarwal.ashi.upes_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                SharedPreferences spref=getSharedPreferences("com.agarwal.ashi.upes_app.choice",Context.MODE_PRIVATE);
                String choice=spref.getString("choice",null);
                System.out.println("Choice "+choice);
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
        },3000);
    }
}

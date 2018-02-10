package com.agarwal.ashi.upes_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by 500060150 on 29-01-2018.
 */

public class SchoolSelectActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_select_layout);

    }

    public void onCilck(View view) {
        String choice;
        Button clickedB=(Button)view;
        switch(clickedB.getId()) {
            case  R.id.soce :
                choice="socs";
                break;
            case R.id.soe :
                choice="soe";
                break;
            case R.id.sob :
                choice="sob";
                break;
            case R.id.sol :
                choice="sol";
                break;
            case R.id.sod :
                choice="sod";
                break;
            default :
                choice=null;
                break;
        }
        SharedPreferences spreferences=getSharedPreferences("com.agarwal.ashi.upes_app.choice",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=spreferences.edit();
        editor.putString("choice",choice);
        System.out.println("choice : "+choice);
        editor.commit();

        startActivity(new Intent(this,MainActivity.class));
        this.finish();
    }
}

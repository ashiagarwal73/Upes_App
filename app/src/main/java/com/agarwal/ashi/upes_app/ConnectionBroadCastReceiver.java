package com.agarwal.ashi.upes_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;

import com.agarwal.ashi.upes_app.activity.MainActivity;

/**
 * Created by 500060150 on 17-03-2018.
 */

public class ConnectionBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
        Log.i("tag","inside on Receive of Broadcast Receiver");
        if(!isConnected) {
            ((MainActivity)context).onConnectivityStatusChanged(false);
        }
        else
            ((MainActivity)context).onConnectivityStatusChanged(true);
    }
}

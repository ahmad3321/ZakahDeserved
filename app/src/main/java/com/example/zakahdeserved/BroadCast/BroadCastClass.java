package com.example.zakahdeserved.BroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.zakahdeserved.Connection.DAL;
import com.example.zakahdeserved.Utility.Constants;

public class BroadCastClass extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (Exception ex) {
            return false;
        }
    }


}

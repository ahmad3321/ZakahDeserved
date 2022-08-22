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
    String[] allSpinners = new String[]{};

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (isOnline(context)) {
                String allSavedQueries = Constants.SQLITEDAL.getAllQueries();
                if (DAL.executeQueries(allSavedQueries))
                    Constants.SQLITEDAL.clearQueries();
//            Toast.makeText(context, "Successfull", Toast.LENGTH_LONG).show();
                Log.e("Status", "connected");
            } else {
//            Toast.makeText(context, "Bad", Toast.LENGTH_LONG).show();
                Log.e("Status", "Failure");
            }
        } catch (Exception ex) {
        }
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

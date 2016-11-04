package com.ayyash.recfonenum;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;

/**
 * Created by Ayyash on 26/10/2016.
 */
public class SimpleBootReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            // new code
            SharedPreferences sharedPreferences = context.getSharedPreferences(ConfigUmum.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            if (sharedPreferences.getBoolean("alarm_aktif", false)){
                long start_time = sharedPreferences.getLong("start_time", 0);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(start_time);

            }

        }
    }
}

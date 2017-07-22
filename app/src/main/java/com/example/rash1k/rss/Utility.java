package com.example.rash1k.rss;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.example.rash1k.rss.service.RssIntentService;

import java.util.Calendar;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static com.example.rash1k.rss.MainActivity.REQUEST_CODE;
import static com.example.rash1k.rss.service.RssIntentService.ACTION_FETCH_ITEMS;

public class Utility {

    private static final String PREF_IS_ALARM_ON = "isAlarmOn";
    private static final int START_OF_SYNCHRONIZATION_TIME = 2; //At two o'clock in the morning
    private static final int END_OF_SYNCHRONIZATION_TIME = 4; //At four o'clock in the morning

    public static long getTodayAt(int hours) {
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DATE);
        today.clear();

        today.set(year, month, day, hours, 0, 0);
        return today.getTimeInMillis();
    }

    public static void setAlarmForFetchNews(Context context, boolean isWindow) {
        Intent intent = new Intent(context, RssIntentService.class);
        intent.setAction(ACTION_FETCH_ITEMS);
        PendingIntent pendingIntent =
                PendingIntent.getService(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (isWindow) {
            long windowStartMillis = Utility.getTodayAt(START_OF_SYNCHRONIZATION_TIME);
            long windowLengthMillis = Utility.getTodayAt(END_OF_SYNCHRONIZATION_TIME);
            am.setWindow(
                    AlarmManager.RTC_WAKEUP,
                    windowStartMillis, windowLengthMillis, pendingIntent);
        } else {
            long windowLengthMillis = 5000;
            am.setWindow(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.uptimeMillis(), SystemClock.uptimeMillis()
                    , pendingIntent);
        }

    }

    public static boolean isServiceAlarmOn(Context context) {
        Intent i = new Intent(context, RssIntentService.class);
        PendingIntent pi = PendingIntent
                .getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    public static void setAlarmOn(Context context, boolean isOn) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_IS_ALARM_ON, isOn)
                .apply();
    }

    public static boolean isAlarmOn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_IS_ALARM_ON, false);
    }

    public static boolean isNetworkAvailableAndConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = cm.getActiveNetworkInfo().isConnected();

        return isNetworkAvailable && isNetworkConnected;
    }
}

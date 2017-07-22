package com.example.rash1k.rss.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import com.example.rash1k.rss.BuildConfig;
import com.example.rash1k.rss.MainActivity;
import com.example.rash1k.rss.Utility;
import com.example.rash1k.rss.model.NewsItem;
import com.example.rash1k.rss.network.NewsFetch;

import java.util.ArrayList;

import static com.example.rash1k.rss.MainActivity.BROADCAST_ACTION;


public class RssIntentService extends IntentService {

    private static final String TAG = RssIntentService.class.getSimpleName();

    public static final String ACTION_BAZ = BuildConfig.APPLICATION_ID + "action.BAZ";

    public static final String ACTION_FETCH_ITEMS =
            BuildConfig.APPLICATION_ID.concat("action.FETCH_ITEMS");

    public static final String EXTRA_PARAM_RESULT_RECEIVER =
            BuildConfig.APPLICATION_ID.concat("extra.RESULT_RECEIVER");


    public RssIntentService() {
        super("RssIntentService");
    }


    public static void startActionFetchNews(Context context, Parcelable param) {
        Intent intent = new Intent(context, RssIntentService.class);
        intent.setAction(ACTION_FETCH_ITEMS);
        intent.putExtra(EXTRA_PARAM_RESULT_RECEIVER, param);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!Utility.isNetworkAvailableAndConnected(this)) {
            return;
        }

        Log.d(TAG, "onHandleIntent: ");

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_ITEMS.equals(action)) {
//                PendingIntent pendingResult = intent.getParcelableExtra(EXTRA_PARAM_RESULT_RECEIVER);
                handleActionFetchItems(null);
            } else if (ACTION_BAZ.equals(action)) {
//                handleActionBaz(param1, param2);
            }
        }
    }


    private void handleActionFetchItems(PendingIntent pr) {
        ArrayList<NewsItem> items = new NewsFetch().fetchItems();


        Intent resultIntent =
                new Intent(BROADCAST_ACTION)
                        .putParcelableArrayListExtra(MainActivity.PARAM_RESULT, items);

     /*   try {
            pr.send(RssIntentService.this, MainActivity.STATUS_FINISH, resultIntent);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }*/
        sendBroadcast(resultIntent);
    }

    public class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
                Utility.setAlarmForFetchNews(context, true);

            }
        }
    }

}

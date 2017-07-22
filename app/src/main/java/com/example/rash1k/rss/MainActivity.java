package com.example.rash1k.rss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.rash1k.rss.model.NewsItem;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public final static String BROADCAST_ACTION = "com.example.rash1k.rss.ACTION_RESULT";
    public static final String PARAM_RESULT = "service_result";
    public static final int REQUEST_CODE = 2;
    public static final int STATUS_FINISH = 3;

    private BroadcastReceiver mReceiver;
    private List<NewsItem> mNewsItemList = new ArrayList<>();
    private ListView mNewsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNewsListView = (ListView) findViewById(R.id.rssListView);

        setupAdapter();

//        Utility.setAlarmForFetchNews(getApplicationContext(),false);

    }


    private void setupAdapter() {
        RssArrayAdapter rssArrayAdapter = new RssArrayAdapter(this, mNewsItemList);
        mNewsListView.setAdapter(rssArrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == STATUS_FINISH) {
            mNewsItemList = data.getParcelableArrayListExtra(MainActivity.PARAM_RESULT);
            setupAdapter();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

      /*  Intent startServiceIntent = new Intent(this, SchedulerService.class);
        Messenger messenger = new Messenger(mHandler);
        startServiceIntent.putExtra(MESSENGER_INTENT_KEY, messenger);
        startService(startServiceIntent);*/

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                mNewsItemList = intent.getParcelableArrayListExtra(MainActivity.PARAM_RESULT);
                setupAdapter();
            }

        };
//        RssIntentService.startActionFetchNews(this, mReceiver);
        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(mReceiver, intentFilter);

    }

    @Override
    protected void onStop() {
//        stopService(new Intent(this, SchedulerService.class));
        super.onStop();
        unregisterReceiver(mReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job_scheduler, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void syncNews(View view) {
        Utility.setAlarmForFetchNews(this, false);

//        PendingIntent pi = createPendingResult(REQUEST_CODE, new Intent(), 0);
//        RssIntentService.startActionFetchNews(this, pi);

    }
}

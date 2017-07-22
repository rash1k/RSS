package com.example.rash1k.rss.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Messenger;
import android.util.Log;

import com.example.rash1k.rss.BuildConfig;

import java.lang.ref.WeakReference;



public class SchedulerService extends JobService {

    private static final String TAG = SchedulerService.class.getSimpleName();
    private static final String MESSENGER_INTENT_KEY =
            BuildConfig.APPLICATION_ID + "MESSENGER_INTENT_KEY";

    private RssTask mRssTask;

    private Messenger mActivityMessenger;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service created: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service destroyed: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mActivityMessenger = intent.getExtras().getParcelable(MESSENGER_INTENT_KEY);
        return START_NOT_STICKY;
    }


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: ");
        if (mRssTask == null) {
            mRssTask = new RssTask(SchedulerService.this);
            mRssTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
        }

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob: ");
        if (mRssTask != null) {
            mRssTask.cancel(true);
        }
        return false;
    }



    private static class RssTask extends AsyncTask<JobParameters, Void, Void> {

        private WeakReference<SchedulerService> mSchedulerService;

        public RssTask(SchedulerService service) {
            mSchedulerService = new WeakReference<>(service);
        }

        @Override
        protected Void doInBackground(JobParameters... params) {
            JobParameters jobParameters = params[0];

            SchedulerService service = mSchedulerService.get();
            if (service == null) {
                return null;
            }

            if(!isCancelled()){

            }

            service.jobFinished(jobParameters, false);
            return null;
        }
    }
}

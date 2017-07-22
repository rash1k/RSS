package com.example.rash1k.rss.receiver;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.rash1k.rss.service.SchedulerService;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class BootReceiver extends BroadcastReceiver {

    private static final int JOB_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                JobScheduler jobScheduler =
                        (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);

                ComponentName componentName = new ComponentName(context, SchedulerService.class);

                JobInfo.Builder jobInfoBuilder = new JobInfo.Builder(JOB_ID, componentName);
                jobInfoBuilder.setPersisted(true);
                jobInfoBuilder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
//                jobInfoBuilder.setOverrideDeadline();


                jobScheduler.schedule(jobInfoBuilder.build());
            }
        }
    }
}

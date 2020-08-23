package com.cgit.medscan.BroadCast;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.cgit.medscan.Model.Constants;
import com.cgit.medscan.Services.SoundService;

import java.util.Objects;


public class StopBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        cancelAlarm(context);
        RemoveNotification(context);
    }



    private void RemoveNotification(Context context){
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(Constants.NOTIFICATION_ID);
    }

    private void cancelAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager) Objects.requireNonNull(context).getSystemService(Context.ALARM_SERVICE);
        Intent intent  = new Intent(context, MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}

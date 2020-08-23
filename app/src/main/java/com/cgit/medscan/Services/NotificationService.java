package com.cgit.medscan.Services;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.cgit.medscan.BroadCast.StopBroadCast;
import com.cgit.medscan.Model.Constants;
import com.cgit.medscan.R;

import static com.cgit.medscan.Model.Constants.NOTIFICATION_CHANNEL_ID;
import static com.cgit.medscan.Model.Constants.NOTIFICATION_ID;

public class NotificationService extends IntentService {



    public NotificationService() {
        super(NotificationService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }


}

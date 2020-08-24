package com.cgit.medscan.BroadCast;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.cgit.medscan.Activities.MedicineTime;
import com.cgit.medscan.Model.Constants;
import com.cgit.medscan.Model.Container;
import com.cgit.medscan.Model.MedicalFormData;
import com.cgit.medscan.Model.Utils;
import com.cgit.medscan.Model.timeModel;
import com.cgit.medscan.R;
import com.cgit.medscan.Room.DatabaseClient;
import com.cgit.medscan.Services.NotificationService;
import com.cgit.medscan.Services.SoundService;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static androidx.legacy.content.WakefulBroadcastReceiver.startWakefulService;
import static com.cgit.medscan.Model.Constants.ERROR;
import static com.cgit.medscan.Model.Constants.NOTIFICATION_CHANNEL_ID;
import static com.cgit.medscan.Model.Constants.NOTIFICATION_ID;

public class MyAlarm extends BroadcastReceiver {
    final String TAG = MyAlarm.class.getSimpleName();
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    private MediaPlayer mediaPlayer;
    MedicalFormData medicalFormData ;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"Alarm is on!!!");
        playMediaPlayer(context);
        Bundle bundle = intent.getExtras();
        int alarmId = bundle.getInt("key");
        Log.i(TAG,alarmId+" alarm id");
        updateStatus(context,alarmId);
        getDataByAlarmId(context,alarmId);
    }

    private void showNotification(Context context){
        Intent intent = new Intent(context, MedicineTime.class);
        intent.putExtra("model",(Serializable) medicalFormData);
        PendingIntent morePendingIntent = PendingIntent.getActivity(
                context,
                1,
                intent,
                PendingIntent.FLAG_ONE_SHOT
        );
        mBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(Constants.TITLE)
                .setContentText(Constants.MESSAGE)
                .setContentIntent(morePendingIntent)
                .setAutoCancel(false);

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, Constants.APP_NAME, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mBuilder.setAutoCancel(true);
            mNotificationManager.createNotificationChannel(notificationChannel);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }else {
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }
    }

    private void playMediaPlayer(Context context){
        mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();
        mediaPlayer.setLooping(false);
    }

    private void setNextAlarm(Context context,int alarmId){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd hh-mm-aa");
        long nextAlarm = medicalFormData.getMedicineTimeOfTaken() + getIntervalInMillis();
        Log.i(TAG,"Next Alarm Date "+simpleDateFormat.format(new Date(nextAlarm)));
        int val = Utils.getSharedPref(context,alarmId);
        val-=1;
        Log.i(TAG, "Val = "+String.valueOf(val));
        if (medicalFormData.getEndTime() != -1){
            if (IsEndTime(medicalFormData.getEndTime())){
                Log.i(TAG,"true");
                if (val > 0){
                    // set next alarm
                    setAlarm(context,nextAlarm);
                    Utils.setSharedPref(context,(int) nextAlarm,val);
                }else {
                    Log.i(TAG,"Finish Alarm!!!");
                }
            }else {
                Log.i(TAG,"false");
                if (val > 0){
                    // set next alarm
                    setAlarm(context,nextAlarm);
                    Utils.setSharedPref(context,(int) nextAlarm,val);
                }else {
                    Log.i(TAG,"Finish For Today!!!");
                    val = medicalFormData.getMedicineTimeInDay();
                    setDataToModelClass(context,val);

                    // set Next Alarm
                }
            }
        }
        else {
            Log.i(TAG,"false");
            if (val > 0){
                // set next alarm
                setAlarm(context,nextAlarm);
                Utils.setSharedPref(context,(int) nextAlarm,val);
            }else {
                Log.i(TAG,"Finish For Today!!!");
                val = medicalFormData.getMedicineTimeInDay();
                nextAlarm = getDayInterval();
                Log.i(TAG,"Next Alarm Date "+simpleDateFormat.format(new Date(nextAlarm)));
                int Id = (int) nextAlarm;
                updateInitialTime(context,Id,nextAlarm);
                setAlarm(context,nextAlarm);
                Utils.setSharedPref(context,(int) nextAlarm,val);
                // set Next Alarm
            }
        }
    }

    private void setAlarm(Context context,long timeInMillis){
        AlarmManager alarmManager = (AlarmManager) Objects.requireNonNull(context).getSystemService(Context.ALARM_SERVICE);
        Intent intent  = new Intent(context, MyAlarm.class);
        intent.putExtra("key",(int) timeInMillis);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) timeInMillis,intent,0);
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,timeInMillis,pendingIntent);
    }

    private void getDataByAlarmId(Context context,int AlarmId){
        DatabaseClient.getInstance(context).getAppDatabase().MedicineDao()
                .getByAlarmId((int) AlarmId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MedicalFormData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(MedicalFormData data) {
                        Log.i(TAG,"onNext");
                        medicalFormData = data;
                        showNotification(context);
                        setNextAlarm(context,AlarmId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG,e.getMessage());
                    }
                });
    }

    private void updateStatus(Context context,int AlarmId){
        DatabaseClient.getInstance(context).getAppDatabase().MedicineDao()
                .updateAlarmStatus(AlarmId,true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,"Alarm status update");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG,e.getMessage());
                    }
                });
    }

    private boolean IsEndTime(long EndTime){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY,MM,dd");
        String CurrentDate = sdf.format(new Date());
        String[] currentDate = CurrentDate.split(",");
        Log.i(TAG,currentDate[0]+currentDate[1]+currentDate[2]);
        String EndDate = sdf.format(new Date(EndTime));
        String[] endDate = EndDate.split(",");
        Log.i(TAG,endDate[0]+endDate[1]+endDate[2]);
        if (currentDate[0].equals(endDate[0]) && currentDate[1].equals(endDate[1]) && currentDate[2].equals(endDate[2])){
            return true;
        }else {
            return false;
        }
    }



    private long getDayInterval(){
        long hrs = 1000*60*60*24;
        long lngFinalFinalInterval = medicalFormData.getINITIAL_TIME() + hrs;
        return lngFinalFinalInterval;
    }

    private void updateInitialTime(Context context,int AlarmId,long time){
        DatabaseClient.getInstance(context).getAppDatabase().MedicineDao()
                .updateInitialTime(AlarmId,time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,"Initial Time update");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG,e.getMessage());
                    }
                });
    }

    private void setDataToModelClass(Context context,int val){
        SimpleDateFormat sdf =new SimpleDateFormat("YYYY-MM-dd hh-mm-aa");
        long startTime = getDayInterval();
        Log.i(TAG,"start time "+sdf.format(new Date(startTime)));
        long FinalTime = startTime;
        setAlarm(context,startTime);
        Utils.setSharedPref(context,(int) startTime,val);
        for (int i=0;i<medicalFormData.getMedicineTimeInDay();i++){
            Log.i(TAG,"final time "+sdf.format(new Date(FinalTime)));
            MedicalFormData model = new MedicalFormData();
            model.setMedicineName(medicalFormData.getMedicineName());
            model.setMedicineCategory(medicalFormData.getMedicineCategory());
            model.setColor(medicalFormData.getColor());
            model.setMedicineQuantity(medicalFormData.getMedicineQuantity());
            model.setMedicineTimeOfTaken(FinalTime);
            model.setMedicineStatus(false);
            model.setAlarmStatus(false);
            model.setAlarmId((int) FinalTime);
            model.setIntervalHour(medicalFormData.getIntervalHour());
            model.setIntervalMinute(medicalFormData.getIntervalMinute());
            model.setMedicineTimeInDay(medicalFormData.getMedicineTimeInDay());
            model.setEndTime(medicalFormData.getEndTime());
            model.setINITIAL_TIME(startTime);
            FinalTime = FinalTime+getIntervalInMillis();
            storeData(model,context);
        }


    }


    private long getIntervalInMillis(){
        int hrs = medicalFormData.getIntervalHour();
        int min = medicalFormData.getIntervalMinute();
        long hrsInMillis = 1000*60*60*hrs;
        long minInMillis = 1000*60*min;
        long Total  = hrsInMillis + minInMillis;
        Log.i(TAG, String.valueOf(new Date(Total)));
        return Total;
    }


    public void storeData(MedicalFormData model,Context context){
        DatabaseClient.getInstance(context).getAppDatabase().MedicineDao()
                .insert(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,"new data added");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG,e.getMessage());
                    }
                });
    }
}


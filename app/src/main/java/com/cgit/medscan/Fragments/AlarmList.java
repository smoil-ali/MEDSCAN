package com.cgit.medscan.Fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cgit.medscan.Adapter.AlarmAdapter;
import com.cgit.medscan.Adapter.TimeAdapter;
import com.cgit.medscan.BroadCast.MyAlarm;
import com.cgit.medscan.Dialogs.AlertSheet;
import com.cgit.medscan.Listeners.AlarmListener;
import com.cgit.medscan.Listeners.AlertAlarmListener;
import com.cgit.medscan.Model.Container;
import com.cgit.medscan.Model.MedicalFormData;
import com.cgit.medscan.Model.Utils;
import com.cgit.medscan.R;
import com.cgit.medscan.Room.DatabaseClient;
import com.cgit.medscan.databinding.FragmentAlarmListBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okio.Utf8;


public class AlarmList extends Fragment implements AlarmListener, AlertAlarmListener {



    final String TAG = AlarmList.class.getSimpleName();
    FragmentAlarmListBinding binding;
    AlarmAdapter adapter;
    List<MedicalFormData> list = new ArrayList<>();
    AlertSheet dialog = new AlertSheet();
    MedicalFormData model;
    Disposable disposable ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAlarmListBinding.inflate(inflater,container,false);
        binding.addButton.setOnClickListener(view -> openFragment());
        TestMode();



        setUpRecyclerView();
        getData();


        return binding.getRoot();
    }

    private void setUpRecyclerView(){
        binding.Rv.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.Rv.getContext(),
                DividerItemDecoration.HORIZONTAL);
        binding.Rv.addItemDecoration(dividerItemDecoration);
        binding.Rv.setNestedScrollingEnabled(false);
        adapter=new AlarmAdapter(getContext(),list);
        adapter.setListener(AlarmList.this);
        binding.Rv.setAdapter(adapter);
    }

    private void getData(){
        DatabaseClient.getInstance(getContext()).getAppDatabase().MedicineDao()
                .getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MedicalFormData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<MedicalFormData> medicalFormData) {
                        list.clear();
                        list.addAll(medicalFormData);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.showDialog(getContext(),"Error",e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,"complete");
                    }
                });
    }

    private void openFragment(){
        AddMedicineForm1 fragment = new AddMedicineForm1();
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left,R.anim.slide_in_left,R.anim.slide_out_right)
                .replace(R.id.container,fragment).addToBackStack("Form0").commit();
    }


    @Override
    public void OnAlarmClick(MedicalFormData model) {
        this.model = model;
        dialog.setListener(this);
        Utils.openDialog(getFragmentManager(),dialog);

    }

    private void cancelAlarm(int AlarmId){
        AlarmManager alarmManager = (AlarmManager) Objects.requireNonNull(getContext()).getSystemService(Context.ALARM_SERVICE);
        Intent intent  = new Intent(getContext(), MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), AlarmId,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    public void OnDone() {
        dialog.dismiss();
        cancelAlarm(model.getAlarmId());
        DatabaseClient.getInstance(getContext()).getAppDatabase().MedicineDao()
                .deleteById(model.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Snackbar.make(getView(),"Alarm has been cancel",1000);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(getView(),e.getMessage(),1000);
                    }
                });
    }

    @Override
    public void OnCancel() {
        dialog.dismiss();
    }

    private void TestMode(){
        long timeInMillis = new Date().getTime();
        int hrs = 1;
        int min = 39;
        long hrsInMillis= 1000*60*60*hrs;
        long minInMillis = 1000*60*min;


         for(int i =0 ;i<5;i++){
             timeInMillis = timeInMillis+hrsInMillis+minInMillis;
             Log.i(TAG, String.valueOf(new Date(timeInMillis)));
         }

    }
}
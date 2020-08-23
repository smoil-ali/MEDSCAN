package com.cgit.medscan.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.cgit.medscan.Model.MedicalFormData;
import com.cgit.medscan.R;
import com.cgit.medscan.Room.DatabaseClient;
import com.cgit.medscan.databinding.ActivityMedicineTimeBinding;

import java.io.Serializable;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MedicineTime extends AppCompatActivity {

    final String TAG = MedicineTime.class.getSimpleName();
    ActivityMedicineTimeBinding binding;
    MedicalFormData model;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicineTimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bundle = getIntent().getExtras();
        model = (MedicalFormData) bundle.getSerializable("model");
        binding.taken.setOnClickListener(view -> updateStatus());
        setData();

    }

    private void setData(){
        binding.medicineName.setText(model.getMedicineName());
    }

    private void updateStatus(){
        DatabaseClient.getInstance(this).getAppDatabase().MedicineDao()
                .updateMedicineStatus(model.getAlarmId(),true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,"update");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG,e.getMessage());
                    }
                });
    }
}
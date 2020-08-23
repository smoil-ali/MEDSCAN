package com.cgit.medscan.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.cgit.medscan.Adapter.TimeAdapter;
import com.cgit.medscan.Dialogs.IntervalSheet;
import com.cgit.medscan.Dialogs.QuantityBottomSheetDialog;
import com.cgit.medscan.Dialogs.TimePickerSheet;
import com.cgit.medscan.Listeners.QuantityListener;
import com.cgit.medscan.Listeners.adapterListener;
import com.cgit.medscan.Listeners.intervalListener;
import com.cgit.medscan.Listeners.timeListener;
import com.cgit.medscan.Model.Constants;
import com.cgit.medscan.Model.Container;
import com.cgit.medscan.Model.Utils;
import com.cgit.medscan.Model.timeModel;
import com.cgit.medscan.R;
import com.cgit.medscan.databinding.FragmentIntakeBinding;

import java.io.UTFDataFormatException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class IntakeFragment extends Fragment implements QuantityListener , intervalListener, adapterListener, timeListener {


    final String TAG = IntakeFragment.class.getSimpleName();
    FragmentIntakeBinding binding;
    QuantityBottomSheetDialog dialog = new QuantityBottomSheetDialog();
    IntervalSheet sheet = new IntervalSheet();
    TimeAdapter adapter;
    TimePickerSheet timePickerSheet = new TimePickerSheet();
    long FinalTimeInMillis = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIntakeBinding.inflate(inflater,container,false);
        binding.perDay.setText(Container.getForm2ModelClass().getMedicineTimeInDay()+" time per day");
        binding.perhour.setText("Every "+Container.getForm2ModelClass().getIntervalHour()+" hour "+Container.getForm2ModelClass().getIntervalMinute()+" min.");
        binding.perhour.setOnClickListener(view -> openIntervalSheet());
        binding.perDay.setOnClickListener(view -> openQuantityDialog());
        setUpRecyclerView();
        init();


        return binding.getRoot();
    }

    private void setUpRecyclerView(){
        Container.getForm2ModelClass().getDataList().clear();
        binding.intakeRv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.intakeRv.getContext(),
                DividerItemDecoration.HORIZONTAL);
        binding.intakeRv.addItemDecoration(dividerItemDecoration);
        binding.intakeRv.setNestedScrollingEnabled(false);
        adapter=new TimeAdapter(Container.getForm2ModelClass().getDataList(),getContext(),getFragmentManager(),true);
        adapter.setListener(this);
        binding.intakeRv.setAdapter(adapter);
    }

    public void openQuantityDialog(){
        dialog.setListener(this);
        Utils.openDialog(getFragmentManager(),dialog);
    }

    public void openIntervalSheet(){
        sheet.setListener(this);
        Utils.openDialog(getFragmentManager(),sheet);

    }

    @Override
    public void OnListener(String quantity) {
        Container.getForm2ModelClass().setMedicineTimeInDay(Integer.parseInt(quantity));
        binding.perDay.setText(quantity+" times per day");
        Container.getForm2ModelClass().getDataList().clear();
        init();
    }

    @Override
    public void OnIntervalListener(String hour, String minutes) {
        binding.perhour.setText("Every "+hour+" hour "+minutes+" min.");
        Container.getForm2ModelClass().getDataList().clear();
        Container.getForm2ModelClass().setIntervalHour(Integer.parseInt(hour));
        Container.getForm2ModelClass().setIntervalMinute(Integer.parseInt(minutes));
        init();
    }

    public void init(){
        FinalTimeInMillis = Container.getForm2ModelClass().getINITIAL_TIME();
        Container.getForm2ModelClass().setStartTime(FinalTimeInMillis);
        Container.getForm2ModelClass().getDataList().add(FinalTimeInMillis);
        LoadList();
    }

    public void changeInitTime(long timeInMillis){
        Container.getForm2ModelClass().setStartTime(timeInMillis);
        Container.getForm2ModelClass().getDataList().clear();
        Container.getForm2ModelClass().setINITIAL_TIME(timeInMillis);
        FinalTimeInMillis = timeInMillis;
        Container.getForm2ModelClass().getDataList().add(FinalTimeInMillis);
        LoadList();
    }

    public void LoadList(){
        for (int i = 0;i<Container.getForm2ModelClass().getMedicineTimeInDay() - 1;i++){
            FinalTimeInMillis = FinalTimeInMillis + getIntervalInMillis();
            Container.getForm2ModelClass().getDataList().add(FinalTimeInMillis);
        }
        adapter.notifyDataSetChanged();
    }




    @Override
    public void onClick() {
        timePickerSheet.setListener(this);
        Utils.openDialog(getFragmentManager(),timePickerSheet);
    }

    @Override
    public void OnTimeListener(long timeInMillis) {
        changeInitTime(timeInMillis);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private long getIntervalInMillis(){
        int hrs = Container.getForm2ModelClass().getIntervalHour();
        int min = Container.getForm2ModelClass().getIntervalMinute();
        long hrsInMillis = 1000*60*60*hrs;
        long minInMillis = 1000*60*min;
        long Total  = hrsInMillis + minInMillis;
        Log.i(TAG, String.valueOf(new Date(Total)));
        return Total;
    }

}
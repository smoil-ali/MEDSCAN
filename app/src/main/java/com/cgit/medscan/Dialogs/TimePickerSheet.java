package com.cgit.medscan.Dialogs;

import android.animation.TimeAnimator;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cgit.medscan.Listeners.QuantityListener;
import com.cgit.medscan.Listeners.timeListener;
import com.cgit.medscan.R;
import com.cgit.medscan.databinding.QuantitySheetBinding;
import com.cgit.medscan.databinding.TimePickerSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimePickerSheet extends BottomSheetDialogFragment {


    final String TAG = TimePickerSheet.class.getSimpleName();
    TimePickerSheetBinding binding;
    Calendar calendar = Calendar.getInstance();
    timeListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TimePickerSheetBinding.inflate(inflater,container,false);
        binding.action.setOnClickListener(view -> action());
        setDividerColor();

        return binding.getRoot();
    }

    public TimePickerSheet() {
    }



    public void action(){
        openTimePicker();
        Log.i(TAG, String.valueOf(calendar.getTimeInMillis()));

    }

    public void openTimePicker(){
        if (Build.VERSION.SDK_INT >= 23){
            calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    binding.timePicker.getHour(),
                    binding.timePicker.getMinute(),
                    0
            );
        }else {
            calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    binding.timePicker.getCurrentHour(),
                    binding.timePicker.getCurrentMinute(),
                    0
            );
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH-mm-ss");
        Log.i(TAG, String.valueOf(new Date(calendar.getTimeInMillis())));
        dismiss();
        listener.OnTimeListener(calendar.getTimeInMillis());
    }

    public void setListener(timeListener listener) {
        this.listener = listener;
    }

    private void setDividerColor () {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //pf.set(picker, getResources().getColor(R.color.my_orange));
                    //Log.v(TAG,"here");
                    pf.set(binding.timePicker, getResources().getColor(R.color.Red));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        //}
    }
}

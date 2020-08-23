package com.cgit.medscan.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cgit.medscan.Listeners.QuantityListener;
import com.cgit.medscan.Listeners.intervalListener;
import com.cgit.medscan.Model.Constants;
import com.cgit.medscan.databinding.IntervalIntakeBinding;
import com.cgit.medscan.databinding.QuantitySheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class IntervalSheet extends BottomSheetDialogFragment {


    final String TAG = IntervalSheet.class.getSimpleName();
    IntervalIntakeBinding binding;
    intervalListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = IntervalIntakeBinding.inflate(inflater,container,false);

        binding.hour.setMaxValue(12);
        binding.hour.setMinValue(0);
        binding.minutes.setMinValue(0);
        binding.minutes.setMaxValue(59);
        binding.action.setOnClickListener(view -> Action());

        return binding.getRoot();
    }

    public IntervalSheet() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void Action(){
        dismiss();
        listener.OnIntervalListener(String.valueOf(binding.hour.getValue()),String.valueOf(binding.minutes.getValue()));
    }

    public void setListener(intervalListener listener) {
        this.listener = listener;
    }
}

package com.cgit.medscan.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cgit.medscan.Listeners.AlertAlarmListener;
import com.cgit.medscan.databinding.AlertBottomNavigationBinding;
import com.cgit.medscan.databinding.IntervalIntakeBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AlertSheet extends BottomSheetDialogFragment {

    final String TAG = AlertSheet.class.getSimpleName();
    AlertBottomNavigationBinding binding;
    AlertAlarmListener listener;

    public void setListener(AlertAlarmListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AlertBottomNavigationBinding.inflate(inflater,container,false);

        binding.ok.setOnClickListener(view -> done());
        binding.cancel.setOnClickListener(view -> cancel());

        return binding.getRoot();
    }

    private void cancel(){
        listener.OnCancel();
    }
    private void done(){
        listener.OnDone();
    }

}

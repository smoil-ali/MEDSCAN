package com.cgit.medscan.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cgit.medscan.Listeners.QuantityListener;
import com.cgit.medscan.databinding.QuantitySheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class QuantityBottomSheetDialog extends BottomSheetDialogFragment  {

    final String TAG = QuantityBottomSheetDialog.class.getSimpleName();
    QuantitySheetBinding binding;
    QuantityListener listener;
    String headerText;

    public void setText(String text) {
        headerText = text;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = QuantitySheetBinding.inflate(inflater,container,false);

        binding.numberPicker.setMinValue(1);
        binding.numberPicker.setMaxValue(100);
        if (headerText != null)
            binding.headerText.setText(headerText);
        binding.action.setOnClickListener(view -> Action());


        return binding.getRoot();
    }

    public QuantityBottomSheetDialog() {
    }

    public void Action(){
        dismiss();
        listener.OnListener(String.valueOf(binding.numberPicker.getValue()));
    }

    public void setListener(QuantityListener listener) {
        this.listener = listener;
    }
}

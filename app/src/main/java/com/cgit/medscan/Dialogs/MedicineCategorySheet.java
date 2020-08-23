package com.cgit.medscan.Dialogs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.cgit.medscan.Listeners.QuantityListener;
import com.cgit.medscan.Listeners.categoryListener;
import com.cgit.medscan.Model.Constants;
import com.cgit.medscan.databinding.CategorySheetBinding;
import com.cgit.medscan.databinding.QuantitySheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MedicineCategorySheet extends BottomSheetDialogFragment {
    final String TAG = MedicineCategorySheet.class.getSimpleName();
    CategorySheetBinding binding;
    categoryListener listener;
    String headerText;

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CategorySheetBinding.inflate(inflater,container,false);
        if (headerText != null)
            binding.headerText.setText(headerText);
        binding.category.setMinValue(0);
        binding.decimal.setMinValue(1);
        binding.decimal.setMaxValue(100);
        binding.points.setMinValue(1);
        binding.points.setMaxValue(100);
        binding.category.setMaxValue(Constants.MedicineTypes.length-1);
        binding.category.setDisplayedValues(Constants.MedicineTypes);
        binding.action.setOnClickListener(view -> Action());
        return binding.getRoot();
    }

    public MedicineCategorySheet() {
    }


    public void Action(){
        dismiss();
        listener.OnCatListener(String.valueOf(binding.decimal.getValue()),
                String.valueOf(binding.points.getValue()),
                Constants.MedicineTypes[binding.category.getValue()]);
    }

    public void setListener(categoryListener listener) {
        this.listener = listener;
    }
}

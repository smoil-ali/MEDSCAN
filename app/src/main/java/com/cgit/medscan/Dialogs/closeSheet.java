package com.cgit.medscan.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cgit.medscan.Listeners.closeListener;
import com.cgit.medscan.databinding.CloseBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class closeSheet extends BottomSheetDialogFragment {

    final String TAG = closeSheet.class.getSimpleName();
    CloseBinding binding;
    closeListener listener;

    public void setListener(closeListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CloseBinding.inflate(inflater,container,false);
        binding.ok.setOnClickListener(view -> ok());
        binding.cancel.setOnClickListener(view -> cancel());

        return binding.getRoot();
    }

    private void ok(){
        listener.OnExit();
    }

    private void cancel(){
        listener.OnCloseCancel();
    }
}

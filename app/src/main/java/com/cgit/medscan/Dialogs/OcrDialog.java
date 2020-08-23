package com.cgit.medscan.Dialogs;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cgit.medscan.Listeners.OcrListener;
import com.cgit.medscan.databinding.OcrDialogBinding;

public class OcrDialog extends DialogFragment {
    final String TAG = OcrDialog.class.getSimpleName();
    OcrDialogBinding binding;
    String inputText="";
    OcrListener listener;

    public void setInputText(String inputText) {
        this.inputText = inputText;
        binding.ocrText.setText(inputText);
    }

    public void setListener(OcrListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = OcrDialogBinding.inflate(inflater,container,false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.ocrText.setText(inputText);
        binding.action.setOnClickListener(view -> Action());

        return binding.getRoot();
    }

    public void Action(){
        listener.OnAction(binding.ocrText.getText().toString());
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();

        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        // Set the width of the dialog proportional to 90% of the screen width
        window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        // Call super onResume after sizing
        super.onResume();
    }
}

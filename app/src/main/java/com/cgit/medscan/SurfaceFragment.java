package com.cgit.medscan;

import android.Manifest;
import android.bluetooth.le.ScanSettings;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cgit.medscan.databinding.FragmentSurfaceBinding;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class SurfaceFragment extends Fragment {
    final String TAG = SurfaceFragment.class.getSimpleName();
    FragmentSurfaceBinding binding;
    private CameraSource cameraSource2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSurfaceBinding.inflate(inflater, container, false);

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getContext()).build();
        cameraSource2 = new CameraSource.Builder(getContext(), textRecognizer)
                .build();



        binding.childSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    cameraSource2.start(binding.childSurfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });


        return binding.getRoot();
    }
}
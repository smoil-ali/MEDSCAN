package com.cgit.medscan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;

import com.cgit.medscan.Dialogs.OcrDialog;
import com.cgit.medscan.Listeners.OcrListener;
import com.cgit.medscan.Model.Constants;
import com.cgit.medscan.Model.Container;
import com.cgit.medscan.Model.Utils;
import com.cgit.medscan.databinding.ActivityImageScannerBinding;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ImageScannerActivity extends AppCompatActivity implements OcrListener {

    final String TAG = ImageScannerActivity.class.getSimpleName();
    ActivityImageScannerBinding binding;
    private CameraSource cameraSource2;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    OcrDialog dialog = new OcrDialog();
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageScannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setTitle(TAG);
        Utils.openDialog(getSupportFragmentManager(),dialog);
        dialog.setListener(this);
        dialog.setCancelable(false);


        TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();
        cameraSource2 = new CameraSource.Builder(this,textRecognizer)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(300,50)
                .build();




        binding.ParentSurface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(ImageScannerActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        cameraSource2.start(binding.ParentSurface.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    ActivityCompat.requestPermissions(ImageScannerActivity.this, new
                            String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });


        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                Observable<SparseArray<TextBlock>> observable = Observable.create(new ObservableOnSubscribe<SparseArray<TextBlock>>() {
                    @Override
                    public void subscribe(ObservableEmitter<SparseArray<TextBlock>> emitter) throws Exception {
                        emitter.onNext(detections.getDetectedItems());
                    }
                });

                disposable = observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<SparseArray<TextBlock>>() {
                            @Override
                            public void accept(SparseArray<TextBlock> items) throws Exception {
                                if (items.size() != 0){
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for (int i=0;i<items.size();i++){
                                        TextBlock textBlock = items.valueAt(i);
                                        stringBuilder.append(textBlock.getValue());
                                    }
                                    dialog.setInputText(stringBuilder.toString());
                                }
                            }
                        });
            }
        });
    }


    @Override
    public void OnAction(String result) {
        dialog.dismiss();
        Container.getForm1ModelClass().setMedName(result);
        disposable.dispose();
        cameraSource2.stop();
        cameraSource2.release();
        finish();
    }

    @Override
    public void onBackPressed() {

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION){
            try {
                cameraSource2.start(binding.ParentSurface.getHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
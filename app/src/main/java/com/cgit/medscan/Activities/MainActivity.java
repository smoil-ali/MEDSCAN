package com.cgit.medscan.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cgit.medscan.Fragments.AddMedicineForm;
import com.cgit.medscan.Fragments.AddMedicineForm1;
import com.cgit.medscan.Fragments.AlarmList;
import com.cgit.medscan.R;
import com.cgit.medscan.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    final String TAG = MainActivity.class.getSimpleName();
    ActivityMainBinding binding;
    AlarmList alarmList = new AlarmList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setElevation(0);

        AddMedicineForm fragment = new AddMedicineForm();
        AddMedicineForm1 form1 = new AddMedicineForm1();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,alarmList).commit();




    }

    @Override
    public void onBackPressed() {
        alarmList.onBackPressed();
    }
}
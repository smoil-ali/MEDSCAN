package com.cgit.medscan.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cgit.medscan.Fragments.DueMedicineFragment;
import com.cgit.medscan.Fragments.IntakeFragment;
import com.cgit.medscan.Model.Constants;
import com.cgit.medscan.R;
import com.cgit.medscan.databinding.ActivityWorkingBinding;

public class WorkingActivity extends AppCompatActivity {

    final String TAG = WorkingActivity.class.getSimpleName();
    ActivityWorkingBinding binding;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bundle = getIntent().getExtras();
        if (bundle.getString(Constants.KEY).equals(Constants.DUE_FRAGMENT)){
            DueMedicineFragment fragment = new DueMedicineFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,fragment).commit();
        }else {
            IntakeFragment fragment = new IntakeFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,fragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
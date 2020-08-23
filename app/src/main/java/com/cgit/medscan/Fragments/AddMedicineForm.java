package com.cgit.medscan.Fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.Toast;

import com.cgit.medscan.Activities.WorkingActivity;
import com.cgit.medscan.Adapter.TimeAdapter;
import com.cgit.medscan.BroadCast.MyAlarm;
import com.cgit.medscan.Dialogs.MedicineCategorySheet;
import com.cgit.medscan.Dialogs.QuantityBottomSheetDialog;
import com.cgit.medscan.Dialogs.TimePickerSheet;
import com.cgit.medscan.Activities.ImageScannerActivity;
import com.cgit.medscan.Listeners.QuantityListener;
import com.cgit.medscan.Listeners.categoryListener;
import com.cgit.medscan.Listeners.timeListener;
import com.cgit.medscan.Model.Constants;
import com.cgit.medscan.Model.Container;
import com.cgit.medscan.Model.Form2ModelClass;
import com.cgit.medscan.Model.MedicalFormData;
import com.cgit.medscan.Model.Utils;
import com.cgit.medscan.Model.timeModel;
import com.cgit.medscan.R;
import com.cgit.medscan.Room.DatabaseClient;
import com.cgit.medscan.databinding.FragmentAddMedicineFormBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.RandomAccess;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.observers.LambdaObserver;
import io.reactivex.schedulers.Schedulers;

import static com.cgit.medscan.Model.Constants.ERROR;
import static com.cgit.medscan.Model.Constants.KEY;
import static com.cgit.medscan.Model.Constants.SUCCESS;
import static com.cgit.medscan.Model.Utils.getIconName;


public class AddMedicineForm extends Fragment implements QuantityListener, categoryListener  {

    final String TAG = AddMedicineForm.class.getSimpleName();
    FragmentAddMedicineFormBinding binding;
    MedicalFormData model = new MedicalFormData();
    MedicineCategorySheet medicineCategorySheet = new MedicineCategorySheet();
    QuantityBottomSheetDialog quantityBottomSheetDialog = new QuantityBottomSheetDialog();
    TimePickerSheet timePickerSheet = new TimePickerSheet();
    long alarmTime = 0;
    Form2ModelClass form2ModelClass = new Form2ModelClass();
    TimeAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddMedicineFormBinding.inflate(inflater,container,false);
        binding.quantity.setOnClickListener(view -> openBottomSheet());
        binding.medicineDue.setOnClickListener(view -> OpenDueSheet());
        binding.medicineInterval.setOnClickListener(view -> OpenIntervalSheet());
        binding.back.setOnClickListener(view -> openFragment());
        binding.done.setOnClickListener(view -> validate());


        return binding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        setUpRecyclerView();
        setData();
    }

    public void setData(){
        binding.medicineName.setText(Container.getForm1ModelClass().getMedName());
        binding.medicineFor.setText(Container.getForm1ModelClass().getForUse());
        binding.medicineCategory.setText(Container.getForm1ModelClass().getCategory());
        binding.image.setBackgroundColor(getResources().getColor(Container.getForm1ModelClass().getColor()));
        setIconColor(Container.getForm1ModelClass().getColor(),Container.getForm1ModelClass().getCategory());
        binding.medicineDue.setText(Container.getForm2ModelClass().getMedicineDurationMessage());
        binding.medicineInterval.setText(Container.getForm2ModelClass().getMedicineTimeInDay()+" "+Container.getForm2ModelClass().getIntervalMessage());
        binding.quantity.setText(Container.getForm2ModelClass().getQuantity()+" "+Container.getForm1ModelClass().getCategory());
    }

    public void openBottomSheet(){
        medicineCategorySheet.setListener(this);
        medicineCategorySheet.setHeaderText("Select Dose Quantity");
        Utils.openDialog(getFragmentManager(),medicineCategorySheet);
    }



    public void OpenDueSheet(){
        Intent intent = new Intent(getContext(), WorkingActivity.class);
        intent.putExtra(KEY,Constants.DUE_FRAGMENT);
        getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        startActivity(intent);
    }

    public void OpenIntervalSheet(){
        Intent intent = new Intent(getContext(), WorkingActivity.class);
        intent.putExtra(KEY,Constants.INTAKE_FRAGMENT);
        getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        startActivity(intent);

    }






    public void storeData(MedicalFormData model){
        DatabaseClient.getInstance(getContext()).getAppDatabase().MedicineDao()
                .insert(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.showDialog(getContext(),ERROR,e.getMessage());
                    }
                });
    }

    public void setAlarm(){
        boolean check = true;
        Container.getForm2ModelClass().setEndTime(getEndTime());
        for (int i = 0;i <Container.getForm2ModelClass().getDataList().size();i++){
            Calendar calendar = Calendar.getInstance();
            MedicalFormData model = new MedicalFormData();
            AlarmManager alarmManager = (AlarmManager) Objects.requireNonNull(getContext()).getSystemService(Context.ALARM_SERVICE);



            long alarm_time=     Container.getForm2ModelClass().getDataList().get(i);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd hh-mm-aa");
            Log.i(TAG,simpleDateFormat.format(new Date(alarm_time)));
            assert alarmManager != null;

            model.setMedicineName(Container.getForm1ModelClass().getMedName());
            model.setMedicineCategory(Container.getForm1ModelClass().getCategory());
            model.setColor(Container.getForm1ModelClass().getColor());
            model.setMedicineQuantity(Container.getForm2ModelClass().getQuantity());
            model.setMedicineTimeOfTaken(alarm_time);
            model.setMedicineStatus(false);
            model.setAlarmStatus(false);
            model.setAlarmId((int) alarm_time);
            model.setIntervalHour(Container.getForm2ModelClass().getIntervalHour());
            model.setIntervalMinute(Container.getForm2ModelClass().getIntervalMinute());
            model.setMedicineTimeInDay(Container.getForm2ModelClass().getMedicineTimeInDay());
            model.setEndTime(Container.getForm2ModelClass().getEndTime());
            model.setINITIAL_TIME(Container.getForm2ModelClass().getINITIAL_TIME());
            if (check){
                Log.i(TAG, String.valueOf(new Date(alarm_time)));
                Intent intent  = new Intent(getContext(), MyAlarm.class);
                intent.putExtra("key",model.getAlarmId());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), model.getAlarmId(),intent,0);
                Utils.setSharedPref(getContext(),model.getAlarmId(),Container.getForm2ModelClass().getMedicineTimeInDay());
                alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,alarm_time,pendingIntent);
                check = false;
            }
            storeData(model);
        }

    }




    @Override
    public void OnListener(String quantity) {
        binding.quantity.setText(quantity);
    }

    @Override
    public void OnCatListener(String decimal, String points, String category) {
        Constants.Quantity = decimal+"."+points;
        Container.getForm1ModelClass().setCategory(category);
        Container.getForm2ModelClass().setQuantity(Integer.parseInt(Constants.Quantity));
        binding.quantity.setText(Constants.Quantity+" "+Container.getForm1ModelClass().getCategory());
        binding.medicineCategory.setText(Container.getForm1ModelClass().getCategory());
        setIconColor(Container.getForm1ModelClass().getColor(),Container.getForm1ModelClass().getCategory());
    }

    private void openFragment(){
        assert getFragmentManager() != null;
        getFragmentManager().popBackStack();
    }

    public void setIconColor(int color,String category){
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(Objects.requireNonNull(getContext()), getIconName(category));
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        binding.image.setImageDrawable(wrappedDrawable);
    }



    private void setUpRecyclerView(){
        binding.intervalRv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.intervalRv.getContext(),
                DividerItemDecoration.HORIZONTAL);
        binding.intervalRv.addItemDecoration(dividerItemDecoration);
        binding.intervalRv.setNestedScrollingEnabled(false);
        adapter=new TimeAdapter(Container.getForm2ModelClass().getDataList(),getContext(),getFragmentManager(),false);
        binding.intervalRv.setAdapter(adapter);
    }

    public void done(){
        clearStack();
        setAlarm();
        AlarmList fragment = new AlarmList();
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left,R.anim.slide_in_left,R.anim.slide_out_right)
                .replace(R.id.container,fragment).commit();
    }

    public void clearStack() {
        //Here we are clearing back stack fragment entries
        assert getFragmentManager() != null;
        int backStackEntry = getFragmentManager().getBackStackEntryCount();
        if (backStackEntry > 0) {
            for (int i = 0; i < backStackEntry; i++) {
                getFragmentManager().popBackStackImmediate();
            }
        }

        //Here we are removing all the fragment that are shown here
        if (getFragmentManager().getFragments().size() > 0) {
            for (int i = 0; i < getFragmentManager().getFragments().size(); i++) {
                Fragment mFragment = getFragmentManager().getFragments().get(i);
                if (mFragment != null) {
                    getFragmentManager().beginTransaction().remove(mFragment).commit();
                }
            }
        }
    }

    private void validate(){
        if (Container.getForm1ModelClass().getMedName().toString().trim().matches("")){
            Utils.showDialog(getContext(),"Error","Medicine name required!!!");
        }else if (Container.getForm2ModelClass().getDataList().size() <= 0){
            Utils.showDialog(getContext(),"Error","Interval time is required!!!");
        }else {
            done();
        }
    }

    private long getEndTime(){

        if (Container.getForm2ModelClass().getMedicineDuration() == 1){
            int size = Container.getForm2ModelClass().getDataList().size();
            return Container.getForm2ModelClass().getDataList().get(size-1);
        }else {
            Log.i(TAG, "Medicine Duration "+String.valueOf(Container.getForm2ModelClass().getMedicineDuration()));
            long endDate = Container.getForm2ModelClass().getStartTime() + getMilliSeconds(Container.getForm2ModelClass().getMedicineDuration());
            Log.i(TAG," End Date "+new SimpleDateFormat("YYYY-MM-dd hh-mm-aa").format(new Date(endDate)));
            return endDate;
        }

    }

    private long getMilliSeconds(int lastDay){
        return 1000*60*60*24*lastDay;
    }
}
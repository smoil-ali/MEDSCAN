package com.cgit.medscan.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cgit.medscan.Fragments.AlarmList;
import com.cgit.medscan.Listeners.AlarmListener;
import com.cgit.medscan.Model.MedicalFormData;
import com.cgit.medscan.Model.Utils;
import com.cgit.medscan.databinding.AlarmListItemsBinding;
import com.cgit.medscan.databinding.CategoryViewBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final String TAG = AlarmAdapter.class.getSimpleName();
    Context context;
    List<MedicalFormData> list ;
    AlarmListener listener;

    public void setListener(AlarmListener listener) {
        this.listener = listener;
    }

    public AlarmAdapter(Context context, List<MedicalFormData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AlarmListItemsBinding binding = AlarmListItemsBinding.inflate(layoutInflater,parent,false);
        return new AlarmViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((AlarmViewHolder)holder).bindView(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class AlarmViewHolder extends RecyclerView.ViewHolder {
        AlarmListItemsBinding binding;
        public AlarmViewHolder(AlarmListItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(MedicalFormData data){

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh-mm-aa");
            long time = data.getMedicineTimeOfTaken();
            binding.medicineName.setText(simpleDateFormat.format(new Date(time))+" "+data.getMedicineName());
            binding.medicineDetail.setText(data.getMedicineQuantity()+" "+data.getMedicineCategory());
            binding.image.setImageDrawable(context.getDrawable(Utils.getIconName(data.getMedicineCategory())));
            binding.image.setBackgroundColor(context.getResources().getColor(data.getColor()));
            binding.AlarmStatus.setText("Alarm Status :"+(data.getAlarmStatus()?"Ringed":"Pending"));
            binding.MedStatus.setText("Medicine Status :"+(data.getMedicineStatus()?"Taken":"Pending"));
            binding.cardView.setOnClickListener(view -> listener.OnAlarmClick(data));
        }
    }
}

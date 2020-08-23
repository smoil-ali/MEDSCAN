package com.cgit.medscan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgit.medscan.Listeners.adapterListener;
import com.cgit.medscan.Model.timeModel;
import com.cgit.medscan.databinding.TimeItemsBinding;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final String TAG = TimeAdapter.class.getSimpleName();
    List<Long> modelList;
    Context context;
    FragmentManager fragmentManager;
    adapterListener listener;
    boolean check ;

    public TimeAdapter(List<Long> modelList, Context context,FragmentManager fragmentManager,boolean check) {
        this.modelList = modelList;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.check = check;
    }

    public void setListener(adapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TimeItemsBinding binding = TimeItemsBinding.inflate(layoutInflater,parent,false);
        return new TimeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TimeViewHolder)holder).bindView(modelList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    private class TimeViewHolder extends RecyclerView.ViewHolder {
        TimeItemsBinding binding;
        public TimeViewHolder(TimeItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(long model,int pos){
            SimpleDateFormat sdf = new SimpleDateFormat("hh-mm-aa");
            binding.time.setText(sdf.format(new Date(model)));
            if (pos == 0 && check){
                binding.time.setOnClickListener(view -> listener.onClick());
            }
        }
    }
}

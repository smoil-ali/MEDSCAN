package com.cgit.medscan.Adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cgit.medscan.Fragments.AddMedicineForm1;
import com.cgit.medscan.Listeners.colorAdapterListener;
import com.cgit.medscan.R;
import com.cgit.medscan.databinding.ColorViewBinding;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final String TAG = ColorAdapter.class.getSimpleName();
    Context context;
    List<String> colorList;
    colorAdapterListener listener;

    public void setListener(colorAdapterListener listener) {
        this.listener = listener;
    }

    public ColorAdapter(Context context, List<String> colorList) {
        this.context = context;
        this.colorList = colorList;
        Log.i(TAG, String.valueOf(AddMedicineForm1.colorViewPosition)+" color position");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ColorViewBinding binding = ColorViewBinding.inflate(layoutInflater,parent,false);
        return new ColorViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ColorViewHolder)holder).bindView(colorList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    private class ColorViewHolder extends RecyclerView.ViewHolder {
        ColorViewBinding binding;
        public ColorViewHolder(ColorViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(String colorName,int pos){
            TypedArray ta = context.getResources().obtainTypedArray(R.array.colors);
            int colorToUse = ta.getResourceId(pos, 0);
            Log.i(TAG,colorToUse+" color adapter");
            binding.colorGround.setBackgroundResource(colorToUse);
            ViewGroup.LayoutParams params = binding.colorGround.getLayoutParams();

            if (AddMedicineForm1.colorViewPosition == pos){
                Log.i(TAG, String.valueOf(AddMedicineForm1.colorViewPosition)+" in bind view");
                params.height = 90;
                binding.colorGround.setLayoutParams(params);
            }else {
                Log.i(TAG, String.valueOf(pos)+" in else bind view");
                params.height = 60;
                binding.colorGround.setLayoutParams(params);
            }

            binding.colorGround.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnColor(colorToUse);
                    params.height = 90;
                    binding.colorGround.setLayoutParams(params);
                    notifyItemChanged(AddMedicineForm1.colorViewPosition);
                    AddMedicineForm1.colorViewPosition = pos;
                }
            });

        }
    }


}

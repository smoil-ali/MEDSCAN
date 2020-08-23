package com.cgit.medscan.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cgit.medscan.Fragments.AddMedicineForm;
import com.cgit.medscan.Fragments.AddMedicineForm1;
import com.cgit.medscan.Listeners.categoryAdapterListener;
import com.cgit.medscan.Model.timeModel;
import com.cgit.medscan.R;
import com.cgit.medscan.databinding.CategoryViewBinding;
import com.cgit.medscan.databinding.TimeItemsBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final String TAG = CategoryAdapter.class.getSimpleName();
    Context context;
    List<String> categoryList;
    categoryAdapterListener listener;

    public void setListener(categoryAdapterListener listener) {
        this.listener = listener;
    }

    public CategoryAdapter(Context context, List<String> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        Log.i(TAG, String.valueOf(categoryList.size()));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CategoryViewBinding binding = CategoryViewBinding.inflate(layoutInflater,parent,false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CategoryViewHolder)holder).bindView(categoryList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }


    private class CategoryViewHolder extends RecyclerView.ViewHolder {
        CategoryViewBinding binding;
        public CategoryViewHolder(CategoryViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(String category,int pos){
            binding.catName.setText(category);
            if (AddMedicineForm1.categoryBorderViewPosition == pos){
                Log.i(TAG, String.valueOf(pos));
                binding.cardView.setBackgroundResource(R.drawable.category_border);
            }else {
                binding.cardView.setBackgroundResource(R.drawable.trasnsparent_border);
            }

            binding.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnCategoryClick(category);
                    binding.cardView.setBackgroundResource(R.drawable.category_border);
                    notifyItemChanged(AddMedicineForm1.categoryBorderViewPosition);
                    AddMedicineForm1.categoryBorderViewPosition = pos;

                }
            });


            if (category.equals("Syrup")){
                binding.image.setImageDrawable(context.getDrawable(R.drawable.spoon));
            }else if (category.equals("Drops")){
                binding.image.setImageDrawable(context.getDrawable(R.drawable.water));
            }else if (category.equals("Pills")){
                binding.image.setImageDrawable(context.getDrawable(R.drawable.pill));
            }else if (category.equals("Capsule")){
                binding.image.setImageDrawable(context.getDrawable(R.drawable.capsule));
            }else {
                binding.image.setImageDrawable(context.getDrawable(R.drawable.syringe));
            }
        }
    }
}

package com.cgit.medscan.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.cgit.medscan.Activities.ImageScannerActivity;
import com.cgit.medscan.Adapter.CategoryAdapter;
import com.cgit.medscan.Adapter.ColorAdapter;
import com.cgit.medscan.Adapter.TimeAdapter;
import com.cgit.medscan.Listeners.OcrListener;
import com.cgit.medscan.Listeners.categoryAdapterListener;
import com.cgit.medscan.Listeners.colorAdapterListener;
import com.cgit.medscan.Model.Constants;
import com.cgit.medscan.Model.Container;
import com.cgit.medscan.Model.Form1ModelClass;
import com.cgit.medscan.Model.Utils;
import com.cgit.medscan.R;
import com.cgit.medscan.databinding.FragmentAddMedicineForm1Binding;

import java.util.Arrays;


public class AddMedicineForm1 extends Fragment implements categoryAdapterListener, colorAdapterListener {




    final String TAG = AddMedicineForm1.class.getSimpleName();
    FragmentAddMedicineForm1Binding binding;
    ColorAdapter colorAdapter;
    CategoryAdapter adapter;
    public static int categoryBorderViewPosition = 0;
    public static int colorViewPosition = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddMedicineForm1Binding.inflate(inflater,container,false);
        setUpRecyclerViewForCategory();
        setUpRecyclerViewForColor();
        binding.next.setOnClickListener(view -> openFragment());
        binding.moreMenu.setOnClickListener(view -> moreMenu());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Utils.HideKeyBoard(getContext());
    }

    private void setUpRecyclerViewForCategory(){
        binding.categoryRv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.categoryRv.getContext(),
                DividerItemDecoration.HORIZONTAL);
        binding.categoryRv.addItemDecoration(dividerItemDecoration);
        binding.categoryRv.setNestedScrollingEnabled(false);
        adapter=new CategoryAdapter(getContext(), Arrays.asList(Constants.MedicineTypes));
        adapter.setListener(this);
        binding.categoryRv.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        binding.medName.setText(Container.getForm1ModelClass().getMedName());
    }



    public void setData(){
        Container.getForm1ModelClass().setMedName(binding.medName.getText().toString());
        Container.getForm1ModelClass().setForUse(binding.medFor.getText().toString());

    }

    private void setUpRecyclerViewForColor(){
        binding.colorRv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.colorRv.getContext(),
                DividerItemDecoration.HORIZONTAL);
        binding.colorRv.addItemDecoration(dividerItemDecoration);
        binding.colorRv.setNestedScrollingEnabled(false);
        colorAdapter=new ColorAdapter(getContext(),Arrays.asList(getResources().getStringArray(R.array.colorNames)));
        colorAdapter.setListener(this);
        binding.colorRv.setAdapter(colorAdapter);
    }

    public void moreMenu(){
        PopupMenu menu=new PopupMenu(getContext(),binding.moreMenu);
        menu.getMenuInflater().inflate(R.menu.popup_menu,menu.getMenu());
        menu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.ocr_text){
                startActivity(new Intent(getContext(), ImageScannerActivity.class));
            }
            return true;
        });
        menu.show();
    }

    private void openFragment(){
        setData();
        AddMedicineForm fragment = new AddMedicineForm();
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left,R.anim.slide_in_left,R.anim.slide_out_right)
                .replace(R.id.container,fragment).addToBackStack("Form1").commit();
    }


    @Override
    public void OnCategoryClick(String category) {
        Container.getForm1ModelClass().setCategory(category);
    }

    @Override
    public void OnColor(int color) {
        Log.i(TAG,color+" in form");
        Container.getForm1ModelClass().setColor(color);
    }

}
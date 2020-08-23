package com.cgit.medscan.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TabHost;
import android.widget.Toast;

import com.cgit.medscan.Dialogs.QuantityBottomSheetDialog;
import com.cgit.medscan.Listeners.QuantityListener;
import com.cgit.medscan.Model.Constants;
import com.cgit.medscan.Model.Container;
import com.cgit.medscan.Model.Utils;
import com.cgit.medscan.R;
import com.cgit.medscan.databinding.FragmentDueMedicineBinding;

public class DueMedicineFragment extends Fragment implements QuantityListener {


    final String TAG = DueMedicineFragment.class.getSimpleName();
    FragmentDueMedicineBinding binding;
    QuantityBottomSheetDialog dialog = new QuantityBottomSheetDialog();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDueMedicineBinding.inflate(inflater,container,false);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, Constants.MEDICINE_DUE_LIST);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (Constants.MEDICINE_DUE_LIST.get(position).equals(Constants.FOR_X_DAYS)){
                    dialog.setListener(DueMedicineFragment.this);
                    dialog.setText("Select Days");
                    Utils.openDialog(getFragmentManager(),dialog);
                }else {
                    int duration = Utils.getDue(Constants.MEDICINE_DUE_LIST.get(position));
                    Log.i(TAG, String.valueOf(duration));
                    Container.getForm2ModelClass().setMedicineDuration(duration);
                    Container.getForm2ModelClass().setMedicineDurationMessage(Constants.MEDICINE_DUE_LIST.get(position));
                    getActivity().finish();

                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void OnListener(String quantity) {
        int duration = Integer.parseInt(quantity);
        Log.i(TAG, String.valueOf(duration));
        Container.getForm2ModelClass().setMedicineDuration(duration);
        Container.getForm2ModelClass().setMedicineDurationMessage("For "+duration+" days");
        getActivity().finish();
    }


}
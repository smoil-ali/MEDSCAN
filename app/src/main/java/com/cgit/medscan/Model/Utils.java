package com.cgit.medscan.Model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cgit.medscan.R;

public class Utils {

    public static void showDialog(Context context, String title, String message){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
    }

    public static void openDialog(FragmentManager manager, DialogFragment fragment){
        FragmentTransaction ft = manager.beginTransaction();
        Fragment prev = manager.findFragmentByTag("dialog");
        if(prev != null)
        {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        fragment.show(ft, "dialog");
    }

    public static void HideKeyBoard(Context context){
        View view = ((Activity)context).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static int getIconName(String category){
        switch (category) {
            case "Syrup":
                return R.drawable.spoon;
            case "Drops":
                return R.drawable.water;
            case "Pills":
                return R.drawable.pill;
            case "Capsule":
                return R.drawable.capsule;
            default:
                return R.drawable.syringe;
        }

    }

    public static int getDue(String due){
        Log.i("TAG",due);
        switch (due) {
            case "For week":
                return 7;
            case "Every day":
                return -1;
            default:
                return 1;
        }
    }


    public static void setSharedPref(Context context, int AlarmId, int perDayTime){
        SharedPreferences preferences = context.getSharedPreferences(String.valueOf(AlarmId),0);
        SharedPreferences.Editor editor  = preferences.edit();
        editor.putInt("perDayTime", perDayTime);
        editor.putInt("MedicineTime",perDayTime);
        editor.apply();
    }
    public static int getSharedPref(Context context,int AlarmId){
        SharedPreferences preferences = context.getSharedPreferences(String.valueOf(AlarmId),0);
        int val = preferences.getInt("perDayTime",-1);
        return val;
    }



}

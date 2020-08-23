package com.cgit.medscan.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
    public static String[] MedicineTypes = new String[]{"Syrup","Drops","Pills","Capsule","Injection"};
    public static List<String> MEDICINE_DUE_LIST = new ArrayList(Arrays.asList("Only one day","Every day","For week","For X days"));
    public static List<String> IntakeList = new ArrayList<>(Arrays.asList("3 times per day","Every 3 hour"));

    public static String MedicineCategory = "Pills";
    public static final String FOR_X_DAYS = "For X days";
    public static String dueMedicine="Only one day";
    public static String ERROR ="ERROR";
    public static final String DATABASE_NAME = "MedicalDatabase";
    public static String SUCCESS = "SUCCESS";
    public static String OCR_TEXT="";
    public static String TITLE = "Medicine Time";
    public static String MESSAGE = "Take Your Medicine!!!";
    public static final String NOTIFICATION_CHANNEL_ID = "383838";
    public static final int NOTIFICATION_ID = 99;
    public static final String APP_NAME = "MEDSCAN";
    public static final String KEY = "key";
    public static final String DUE_FRAGMENT = "due_fragment";
    public static final String INTAKE_FRAGMENT = "intake_fragment";
    public static String Quantity = "1";
    public static List<timeModel> AlarmList = new ArrayList<>();


}

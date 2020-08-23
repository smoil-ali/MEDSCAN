package com.cgit.medscan.Model;


import android.annotation.TargetApi;
import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Form2ModelClass implements Serializable {
    String medicineDurationMessage = "Only one day";
    int medicineDuration = 1;
    int medicineTimeInDay = 2;
    int quantity = 1;
    String IntervalMessage = "times per day";
    int IntervalHour = 3;
    int IntervalMinute = 30;
    long StartTime;
    long EndTime;
    long INITIAL_TIME = new Date().getTime();
    List<Long> dataList = new ArrayList<>();


    public String getMedicineDurationMessage() {
        return medicineDurationMessage;
    }

    public void setMedicineDurationMessage(String medicineDurationMessage) {
        this.medicineDurationMessage = medicineDurationMessage;
    }

    public int getMedicineDuration() {
        return medicineDuration;
    }

    public void setMedicineDuration(int medicineDuration) {
        this.medicineDuration = medicineDuration;
    }

    public int getMedicineTimeInDay() {
        return medicineTimeInDay;
    }

    public void setMedicineTimeInDay(int medicineTimeInDay) {
        this.medicineTimeInDay = medicineTimeInDay;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIntervalMessage() {
        return IntervalMessage;
    }

    public void setIntervalMessage(String intervalMessage) {
        IntervalMessage = intervalMessage;
    }

    public int getIntervalHour() {
        return IntervalHour;
    }

    public void setIntervalHour(int intervalHour) {
        IntervalHour = intervalHour;
    }

    public int getIntervalMinute() {
        return IntervalMinute;
    }

    public void setIntervalMinute(int intervalMinute) {
        IntervalMinute = intervalMinute;
    }

    public long getStartTime() {
        return StartTime;
    }

    public void setStartTime(long startTime) {
        StartTime = startTime;
    }

    public long getEndTime() {
        return EndTime;
    }

    public void setEndTime(long endTime) {
        EndTime = endTime;
    }

    public long getINITIAL_TIME() {
        return INITIAL_TIME;
    }

    public void setINITIAL_TIME(long INITIAL_TIME) {
        this.INITIAL_TIME = INITIAL_TIME;
    }

    public List<Long> getDataList() {
        return dataList;
    }

    public void setDataList(List<Long> dataList) {
        this.dataList = dataList;
    }
}

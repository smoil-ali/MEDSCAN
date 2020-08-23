package com.cgit.medscan.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class MedicalFormData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "MedicineName")
    public String MedicineName;
    @ColumnInfo(name = "MedicineCategory")
    public String MedicineCategory;
    @ColumnInfo(name = "color")
    int color;
    @ColumnInfo(name = "MedicineQuantity")
    public int MedicineQuantity;
    @ColumnInfo(name = "MedicineTimeOfTaken")
    public long MedicineTimeOfTaken;
    @ColumnInfo(name = "EndTime")
    public long EndTime;
    @ColumnInfo(name = "INITIAL_TIME")
    public long INITIAL_TIME;
    @ColumnInfo(name = "AlarmId")
    public int AlarmId;
    @ColumnInfo(name = "AlarmStatus")
    public boolean AlarmStatus;
    @ColumnInfo(name = "MedicineStatus")
    public boolean MedicineStatus;
    @ColumnInfo(name = "IntervalHour")
    public int IntervalHour;
    @ColumnInfo(name = "IntervalMinute")
    public int IntervalMinute;
    @ColumnInfo(name = "medicineTimeInDay")
    public int medicineTimeInDay;

    public int getId() {
        return id;
    }

    public String getMedicineName() {
        return MedicineName;
    }

    public void setMedicineName(String medicineName) {
        MedicineName = medicineName;
    }

    public String getMedicineCategory() {
        return MedicineCategory;
    }

    public void setMedicineCategory(String medicineCategory) {
        MedicineCategory = medicineCategory;
    }


    public long getMedicineTimeOfTaken() {
        return MedicineTimeOfTaken;
    }

    public void setMedicineTimeOfTaken(long medicineTimeOfTaken) {
        MedicineTimeOfTaken = medicineTimeOfTaken;
    }

    public long getEndTime() {
        return EndTime;
    }

    public void setEndTime(long endTime) {
        EndTime = endTime;
    }


    public boolean getAlarmStatus() {
        return AlarmStatus;
    }

    public void setAlarmStatus(boolean alarmStatus) {
        AlarmStatus = alarmStatus;
    }

    public boolean getMedicineStatus() {
        return MedicineStatus;
    }

    public void setMedicineStatus(boolean medicineStatus) {
        MedicineStatus = medicineStatus;
    }



    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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

    public int getAlarmId() {
        return AlarmId;
    }

    public void setAlarmId(int alarmId) {
        AlarmId = alarmId;
    }

    public int getMedicineQuantity() {
        return MedicineQuantity;
    }

    public void setMedicineQuantity(int medicineQuantity) {
        MedicineQuantity = medicineQuantity;
    }

    public int getMedicineTimeInDay() {
        return medicineTimeInDay;
    }

    public void setMedicineTimeInDay(int medicineTimeInDay) {
        this.medicineTimeInDay = medicineTimeInDay;
    }

    public long getINITIAL_TIME() {
        return INITIAL_TIME;
    }

    public void setINITIAL_TIME(long INITIAL_TIME) {
        this.INITIAL_TIME = INITIAL_TIME;
    }
}

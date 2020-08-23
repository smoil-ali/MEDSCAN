package com.cgit.medscan.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.cgit.medscan.Model.MedicalFormData;

@Database(entities = {MedicalFormData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MedicineDao MedicineDao();
}

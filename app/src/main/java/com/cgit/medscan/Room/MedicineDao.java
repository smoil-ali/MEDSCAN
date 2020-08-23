package com.cgit.medscan.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cgit.medscan.Model.MedicalFormData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

import static android.icu.text.MessagePattern.ArgType.SELECT;

@Dao
public interface MedicineDao {

    @Query("SELECT * FROM MedicalFormData")
    Observable<List<MedicalFormData>> getAll();

    @Query("SELECT * FROM MedicalFormData WHere AlarmId = :AlarmId")
    Single<MedicalFormData> getByAlarmId(int AlarmId);

    @Insert
    Completable insert(MedicalFormData model);

    @Delete
    void delete(MedicalFormData model);

    @Query("DELETE FROM MedicalFormData WHERE id = :id")
    Completable deleteById(int id);

    @Query("Update MedicalFormData set MedicineStatus = :status Where AlarmId = :AlarmId")
    Completable updateMedicineStatus(int AlarmId,boolean status);

    @Query("Update MedicalFormData set AlarmStatus = :status Where AlarmId = :AlarmId")
    Completable updateAlarmStatus(int AlarmId,boolean status);


    @Query("Update MedicalFormData set INITIAL_TIME = :time Where AlarmId = :AlarmId")
    Completable updateInitialTime(int AlarmId,long time);

}

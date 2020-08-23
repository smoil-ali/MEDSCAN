package com.cgit.medscan.Room;

import android.content.Context;

import androidx.room.Room;

import static com.cgit.medscan.Model.Constants.DATABASE_NAME;

public class DatabaseClient {
    private Context context;
    private static DatabaseClient mInstance;
    private AppDatabase appDatabase;

    private DatabaseClient(Context context){
        this.context = context;
        appDatabase = Room.databaseBuilder(context,AppDatabase.class,DATABASE_NAME).build();
    }

    public static synchronized DatabaseClient getInstance(Context context){
        if (mInstance == null){
            mInstance = new DatabaseClient(context);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}

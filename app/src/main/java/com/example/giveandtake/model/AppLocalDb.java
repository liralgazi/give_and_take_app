package com.example.giveandtake.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.giveandtake.MyApplication;

public class AppLocalDb{
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "AppDb.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDb(){}
}


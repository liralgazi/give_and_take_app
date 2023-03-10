package com.example.giveandtake.adapter.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.giveandtake.MyApplication;

@Database(entities = {User.class}, version = 80)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract UserDao userDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDb(){}
}


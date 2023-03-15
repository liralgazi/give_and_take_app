package com.example.giveandtake.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.giveandtake.MyApplication;


@Database(
        entities = {
                User.class,
                Post.class
        },
        version = 8
        , exportSchema = false
)
@TypeConverters({Converts.class})
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract PostDao postDao();
}

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


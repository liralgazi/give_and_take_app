package com.example.giveandtake.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 80)
public abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract UserDao userDao();
}

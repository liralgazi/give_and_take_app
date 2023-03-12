package com.example.giveandtake.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(
        entities = {
                User.class,
                Post.class
        },
        version = 3
        , exportSchema = false
)
@TypeConverters({Converts.class})
public abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract PostDao postDao();
}

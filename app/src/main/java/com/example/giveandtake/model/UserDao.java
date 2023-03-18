package com.example.giveandtake.model;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM User WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("select * from User where uid = :userId")
     User getUserById(String userId);

    @Insert (onConflict = IGNORE)
    void insertAll(User... users);

    @Delete
    void delete(User user);
}

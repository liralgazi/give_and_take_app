package com.example.giveandtake.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {
    @Query("SELECT * FROM Post")
    LiveData<List<Post>> getAll();

    @Query("SELECT * FROM post WHERE uid IN (:userIds)")
    List<Post> loadAllByIds(int[] userIds);


    @Query("select * from post where uid = :userId")
    Post getPostById(String userId);

    @Insert
    void insertAll(Post... posts);

    @Delete
    void delete(Post post);
}

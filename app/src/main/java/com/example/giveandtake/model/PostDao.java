package com.example.giveandtake.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {
    @Query("SELECT * FROM Post")
    LiveData<List<Post>> getAll();

    @Query("SELECT * FROM post WHERE postId IN (:postIds)")
    List<Post> loadAllByIds(int[] postIds);


    @Query("select * from post where postId = :postIds")
    Post getPostById(String postIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);

    @Delete
    void delete(Post post);
}

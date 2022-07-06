package com.liaudev.dicodingfa.data.room;

import androidx.lifecycle.LiveData;
import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * Created by Budiliauw87 on 2022-06-22.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("DELETE FROM user WHERE id=:id")
    void delete(String id);

    @Query("SELECT * from user")
    PagingSource<Integer, User> getUserFavorite();

    @Query("SELECT * FROM user WHERE id = :id")
    LiveData<User> getUserById(String id);
}

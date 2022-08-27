package com.liaudev.githubuser.core.data.local.room;

import androidx.lifecycle.LiveData;
import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.liaudev.githubuser.core.data.local.entity.UserEntity;

/**
 * Created by Budiliauw87 on 2022-06-22.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(UserEntity userEntity);

    @Query("DELETE FROM UserEntity")
    void deleteAll();

    @Query("DELETE FROM UserEntity WHERE id=:id")
    void delete(String id);

    @Query("SELECT * from UserEntity")
    PagingSource<Integer, UserEntity> getUserFavorite();

    @Query("SELECT * FROM UserEntity WHERE id = :id")
    LiveData<UserEntity> getUserById(String id);
}

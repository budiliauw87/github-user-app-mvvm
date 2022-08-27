package com.liaudev.githubuser.core.data.local.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.liaudev.githubuser.core.data.local.entity.UserEntity;

/**
 * Created by Budiliauw87 on 2022-06-22.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}


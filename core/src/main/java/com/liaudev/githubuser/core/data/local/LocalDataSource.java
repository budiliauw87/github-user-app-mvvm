package com.liaudev.githubuser.core.data.local;

import androidx.lifecycle.LiveData;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;

import com.liaudev.githubuser.core.data.local.entity.UserEntity;
import com.liaudev.githubuser.core.data.local.room.UserDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Budiliauw87 on 2022-08-20.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Singleton
public class LocalDataSource {
    private static final String TAG = "LocalDataSource";
    private final UserDatabase database;

    @Inject
    public LocalDataSource(UserDatabase userDatabase) {
        this.database = userDatabase;
    }

    public Pager<Integer, UserEntity> getFavorite() {
        return new Pager<>(
                new PagingConfig(10),
                null, () -> database.userDao().getUserFavorite()
        );
    }

    public void setFavorite(UserEntity userEntity) {
        database.userDao().insert(userEntity);
    }

    public void deleteFavorite(String id) {
        database.userDao().delete(id);
    }

    public LiveData<UserEntity> getFavoriteById(String id) {
        return database.userDao().getUserById(id);
    }
}

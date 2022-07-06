package com.liaudev.dicodingfa.data;

import androidx.lifecycle.LiveData;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;

import com.liaudev.dicodingfa.data.room.User;
import com.liaudev.dicodingfa.data.room.UserDatabase;
import com.liaudev.dicodingfa.network.ApiRequest;
import com.liaudev.dicodingfa.paging.UserPagingSource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Budiliauw87 on 2022-06-21.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class DataRepository implements DataSource {
    private final ApiRequest api;
    private final UserDatabase database;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private volatile static DataRepository INSTANCE = null;

    private DataRepository(ApiRequest apiRequest, UserDatabase userDatabase) {
        this.api = apiRequest;
        this.database = userDatabase;
    }

    public static DataRepository getInstance(ApiRequest apiRequest, UserDatabase userDatabase) {
        if (INSTANCE == null) {
            synchronized (DataRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataRepository(apiRequest, userDatabase);
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public Pager<String, User> getUsers(String query, int method) {
        return new Pager<String, User>(
                new PagingConfig(10),
                null,
                () -> new UserPagingSource(query, api, method));
    }

    @Override
    public Pager<Integer, User> getFavorite() {
        return new Pager<Integer, User>(
                new PagingConfig(10),
                null, () -> database.userDao().getUserFavorite()
        );
    }

    @Override
    public void setFavorite(User user) {
        executorService.execute(() -> {
            database.userDao().insert(user);
        });
    }

    @Override
    public void deleteFavorite(String id) {
        executorService.execute(() -> {
            database.userDao().delete(id);
        });
    }

    @Override
    public LiveData<User> getUserById(String id) {
        return database.userDao().getUserById(id);
    }
}

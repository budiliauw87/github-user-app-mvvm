package com.liaudev.dicodingfa.data;

import androidx.lifecycle.LiveData;
import androidx.paging.Pager;

import com.liaudev.dicodingfa.data.room.User;

/**
 * Created by Budiliauw87 on 2022-06-21.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public interface DataSource {
    Pager<String, User> getUsers(String query, int method);

    Pager<Integer, User> getFavorite();

    void setFavorite(User user);

    void deleteFavorite(String id);

    LiveData<User> getUserById(String id);
}

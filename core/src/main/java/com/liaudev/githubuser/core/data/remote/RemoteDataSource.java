package com.liaudev.githubuser.core.data.remote;


import androidx.paging.Pager;
import androidx.paging.PagingConfig;

import com.liaudev.githubuser.core.data.remote.network.ApiRequest;
import com.liaudev.githubuser.core.data.remote.paging.UserPagingSource;
import com.liaudev.githubuser.core.domain.model.User;

import javax.inject.Inject;

/**
 * Created by Budiliauw87 on 2022-08-20.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */

public class RemoteDataSource {
    private final ApiRequest api;

    @Inject
    public RemoteDataSource(ApiRequest apiRequest) {
        this.api = apiRequest;
    }

    public Pager<String, User> getUsers(String query, int method) {
        return new Pager<>(
                new PagingConfig(10),
                null,
                () -> new UserPagingSource(query, api, method));
    }
}

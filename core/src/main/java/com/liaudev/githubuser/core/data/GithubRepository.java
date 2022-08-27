package com.liaudev.githubuser.core.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.Pager;

import com.liaudev.githubuser.core.data.local.LocalDataSource;
import com.liaudev.githubuser.core.data.local.entity.UserEntity;
import com.liaudev.githubuser.core.data.remote.RemoteDataSource;
import com.liaudev.githubuser.core.domain.model.User;
import com.liaudev.githubuser.core.domain.repository.IGithubRepository;
import com.liaudev.githubuser.core.utils.DataMapper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Budiliauw87 on 2022-08-20.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */

@Singleton
public class GithubRepository implements IGithubRepository {

    private final RemoteDataSource remoteSource;
    private final LocalDataSource localSource;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Inject
    public GithubRepository(@NonNull RemoteDataSource remoteDataSource,
                            @NonNull LocalDataSource localDataSource){
        this.remoteSource = remoteDataSource;
        this.localSource = localDataSource;
    }

    @Override
    public Pager<String, User> getUsers(String query, int method) {
        return remoteSource.getUsers(query,method);
    }

    @Override
    public LiveData<User> getFavoriteById(String id) {
        LiveData<UserEntity> userEntityLiveData = localSource.getFavoriteById(id);
        LiveData<User> userLiveData = Transformations.map(userEntityLiveData,
                user-> DataMapper.mapEntityToDomain(user));
        return userLiveData;
    }

    @Override
    public Pager<Integer, UserEntity> getFavorite() {
        return  localSource.getFavorite();
    }

    @Override
    public void setFavorite(User user) {
        if(user!=null){
            executorService.execute(() -> {
                localSource.setFavorite(DataMapper.mapDomainToEntity(user));
            });
        }
    }

    @Override
    public void deleteFavorite(String id) {
        if(id!=null){
            executorService.execute(() -> {
                localSource.deleteFavorite(id);
            });
        }
    }
}

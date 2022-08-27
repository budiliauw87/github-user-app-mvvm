package com.liaudev.githubuser.core.domain.usecase;

import androidx.lifecycle.LiveData;
import androidx.paging.Pager;

import com.liaudev.githubuser.core.data.local.entity.UserEntity;
import com.liaudev.githubuser.core.domain.model.User;
import com.liaudev.githubuser.core.domain.repository.IGithubRepository;

import javax.inject.Inject;

/**
 * Created by Budiliauw87 on 2022-08-21.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class UserInteractor implements UserUseCase {
    private final IGithubRepository repository;

    @Inject
    public UserInteractor(IGithubRepository iGithubRepository) {
        this.repository = iGithubRepository;
    }

    @Override
    public Pager<String, User> getUsers(String query, int method) {
        return repository.getUsers(query, method);
    }

    @Override
    public Pager<Integer, UserEntity> getFavorite() {
        return repository.getFavorite();
    }

    @Override
    public LiveData<User> getFavoriteById(String id) {
        return repository.getFavoriteById(id);
    }

    @Override
    public void setFavorite(User user) {
         repository.setFavorite(user);
    }

    @Override
    public void deleteFavorite(String id) {
        repository.deleteFavorite(id);
    }
}

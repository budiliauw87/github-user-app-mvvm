package com.liaudev.githubuser.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.liaudev.githubuser.core.domain.model.User;
import com.liaudev.githubuser.core.domain.usecase.UserUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * Created by Budiliauw87 on 2022-08-21.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */

@HiltViewModel
public class DetailViewModel extends ViewModel {

    private final UserUseCase useCase;

    @Inject
    public DetailViewModel(UserUseCase userUseCase) {
        this.useCase = userUseCase;
    }

    public LiveData<User> getUser(String id) {
        return useCase.getFavoriteById(id);
    }

    public void setFavorite(User user) {
        useCase.setFavorite(user);
    }

    public void deleteFavorite(String id) {
        useCase.deleteFavorite(id);
    }
}

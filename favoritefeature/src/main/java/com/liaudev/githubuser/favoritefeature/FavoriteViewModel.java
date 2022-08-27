package com.liaudev.githubuser.favoritefeature;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.ExperimentalPagingApi;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.liaudev.githubuser.core.data.local.entity.UserEntity;
import com.liaudev.githubuser.core.domain.usecase.UserUseCase;

import javax.inject.Inject;

import kotlinx.coroutines.CoroutineScope;

/**
 * Created by Budiliauw87 on 2022-08-22.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class FavoriteViewModel extends ViewModel {
    private final UserUseCase useCase;

    @Inject
    public FavoriteViewModel(UserUseCase userUseCase) {
        this.useCase = userUseCase;
    }

    @ExperimentalPagingApi
    public LiveData<PagingData<UserEntity>> getFavorite() {
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(useCase.getFavorite()), coroutineScope);
    }


}

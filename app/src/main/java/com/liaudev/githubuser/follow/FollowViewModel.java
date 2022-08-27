package com.liaudev.githubuser.follow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.ExperimentalPagingApi;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.liaudev.githubuser.core.domain.model.User;
import com.liaudev.githubuser.core.domain.usecase.UserUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.CoroutineScope;

/**
 * Created by Budiliauw87 on 2022-08-24.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@HiltViewModel
public class FollowViewModel extends ViewModel {

    private final MutableLiveData<String> queryLiveData = new MutableLiveData<>();
    private final UserUseCase useCase;

    @Inject
    public FollowViewModel(UserUseCase userUseCase) {
        this.useCase = userUseCase;
    }

    @ExperimentalPagingApi
    public LiveData<PagingData<User>> getFollow(int methodQuery) {
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(useCase.getUsers(queryLiveData.getValue(), methodQuery)), coroutineScope);
    }

    public void setLoginName(String loginName) {
        queryLiveData.setValue(loginName);
    }
}

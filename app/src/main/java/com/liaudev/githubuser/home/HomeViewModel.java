package com.liaudev.githubuser.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
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
 * Created by Budiliauw87 on 2022-08-21.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final UserUseCase useCase;
    private final MutableLiveData<String> queryLiveData = new MutableLiveData<>();
    private final LiveData<PagingData<User>> pagingDataLiveData;

    @Inject
    public HomeViewModel(UserUseCase userUseCase) {
        this.useCase = userUseCase;
        final int METHOD_QUERY_USER = 0;
        queryLiveData.setValue("");
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        pagingDataLiveData = Transformations.switchMap(queryLiveData, query ->
                PagingLiveData.cachedIn(PagingLiveData.getLiveData(useCase.getUsers(query, METHOD_QUERY_USER)), coroutineScope)
        );
    }

    @ExperimentalPagingApi
    public LiveData<PagingData<User>> getUsers() {
        return pagingDataLiveData;
    }

    public void findUser(String query) {
        final String querySearch = query == null ? "" : query;
        queryLiveData.setValue(querySearch);
    }
}

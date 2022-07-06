package com.liaudev.dicodingfa.ui.follower;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.ExperimentalPagingApi;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.liaudev.dicodingfa.data.DataRepository;
import com.liaudev.dicodingfa.data.room.User;

import kotlinx.coroutines.CoroutineScope;

/**
 * Created by Budiliauw87 on 2022-06-26.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class FollowerViewModel extends ViewModel {

    private final DataRepository dataRepository;
    private final MutableLiveData<String> queryLiveData = new MutableLiveData<>();
    private final int METHOD_QUERY_FOLLOWERS = 1;

    public FollowerViewModel(DataRepository repository) {
        this.dataRepository = repository;
    }

    @ExperimentalPagingApi
    public LiveData<PagingData<User>> getFollowers() {
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(dataRepository.getUsers(queryLiveData.getValue(), METHOD_QUERY_FOLLOWERS)), coroutineScope);
    }

    public void setLoginName(String loginName) {
        queryLiveData.setValue(loginName);
    }
}

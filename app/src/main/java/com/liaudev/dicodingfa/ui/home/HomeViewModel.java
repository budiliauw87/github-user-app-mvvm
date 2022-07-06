package com.liaudev.dicodingfa.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.ExperimentalPagingApi;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.liaudev.dicodingfa.data.DataRepository;
import com.liaudev.dicodingfa.data.room.User;

import kotlinx.coroutines.CoroutineScope;

/**
 * Created by Budiliauw87 on 2022-06-22.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class HomeViewModel extends ViewModel {
    private final DataRepository dataRepository;
    private final MutableLiveData<String> queryLiveData = new MutableLiveData<>();
    private final LiveData<PagingData<User>> pagingDataLiveData;
    private final int METHOD_QUERY_USER = 0;

    public HomeViewModel(DataRepository repository) {
        this.dataRepository = repository;
        queryLiveData.setValue("");
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        pagingDataLiveData = Transformations.switchMap(queryLiveData, query ->
                PagingLiveData.cachedIn(PagingLiveData.getLiveData(dataRepository.getUsers(query, METHOD_QUERY_USER)), coroutineScope)
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

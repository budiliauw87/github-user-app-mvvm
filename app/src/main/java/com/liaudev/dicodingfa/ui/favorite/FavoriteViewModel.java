package com.liaudev.dicodingfa.ui.favorite;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.ExperimentalPagingApi;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.liaudev.dicodingfa.data.DataRepository;
import com.liaudev.dicodingfa.data.room.User;

import kotlinx.coroutines.CoroutineScope;

/**
 * Created by Budiliauw87 on 2022-06-23.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class FavoriteViewModel extends ViewModel {
    private final DataRepository dataRepository;

    public FavoriteViewModel(DataRepository repository) {
        this.dataRepository = repository;
    }

    @ExperimentalPagingApi
    public LiveData<PagingData<User>> getFavorite() {
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(dataRepository.getFavorite()), coroutineScope);
    }

    public void setFavorite(User user) {
        dataRepository.setFavorite(user);
    }

    public void deleteFavorite(String id) {
        dataRepository.deleteFavorite(id);
    }
}

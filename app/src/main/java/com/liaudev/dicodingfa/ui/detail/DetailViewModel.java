package com.liaudev.dicodingfa.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.liaudev.dicodingfa.data.DataRepository;
import com.liaudev.dicodingfa.data.room.User;

/**
 * Created by Budiliauw87 on 2022-06-23.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class DetailViewModel extends ViewModel {
    private final DataRepository repository;

    public DetailViewModel(DataRepository dataRepository) {
        this.repository = dataRepository;
    }

    public LiveData<User> getUser(String id) {
        return repository.getUserById(id);
    }

    public void setFavorite(User user) {
        repository.setFavorite(user);
    }

    public void deleteFavorite(String id) {
        repository.deleteFavorite(id);
    }

}

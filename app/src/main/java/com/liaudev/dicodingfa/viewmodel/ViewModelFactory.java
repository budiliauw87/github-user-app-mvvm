package com.liaudev.dicodingfa.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.liaudev.dicodingfa.data.DataRepository;
import com.liaudev.dicodingfa.di.Injection;
import com.liaudev.dicodingfa.ui.detail.DetailViewModel;
import com.liaudev.dicodingfa.ui.favorite.FavoriteViewModel;
import com.liaudev.dicodingfa.ui.follower.FollowerViewModel;
import com.liaudev.dicodingfa.ui.following.FollowingViewModel;
import com.liaudev.dicodingfa.ui.home.HomeViewModel;

/**
 * Created by Budiliauw87 on 2022-06-22.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;
    private final DataRepository dataRepository;

    private ViewModelFactory(DataRepository repository) {
        dataRepository = repository;
    }

    public static ViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(Injection.provideRepository(context));
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(dataRepository);
        } else if (modelClass.isAssignableFrom(FavoriteViewModel.class)) {
            return (T) new FavoriteViewModel(dataRepository);
        } else if (modelClass.isAssignableFrom(DetailViewModel.class)) {
            return (T) new DetailViewModel(dataRepository);
        } else if (modelClass.isAssignableFrom(FollowerViewModel.class)) {
            return (T) new FollowerViewModel(dataRepository);
        } else if (modelClass.isAssignableFrom(FollowingViewModel.class)) {
            return (T) new FollowingViewModel(dataRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}

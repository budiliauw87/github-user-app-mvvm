package com.liaudev.githubuser.favoritefeature;

import android.content.Context;

import com.liaudev.githubuser.di.FavoriteModuleDependencies;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by Budiliauw87 on 2022-08-23.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Component(dependencies = {FavoriteModuleDependencies.class})
public interface FavoriteComponent {
    void inject(FavoriteFragment favoriteFragment);

    @Component.Builder
    interface Builder  {
        Builder context(@BindsInstance Context context);
        Builder appDependencies(FavoriteModuleDependencies favoriteModuleDependencies);
        FavoriteComponent build();
    }
}

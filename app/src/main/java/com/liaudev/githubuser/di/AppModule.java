package com.liaudev.githubuser.di;

import com.liaudev.githubuser.core.domain.usecase.UserInteractor;
import com.liaudev.githubuser.core.domain.usecase.UserUseCase;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * Created by Budiliauw87 on 2022-08-21.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Module
@InstallIn(SingletonComponent.class)
public abstract class AppModule {
    @Binds
    @Singleton
    abstract UserUseCase provideUserUseCase(UserInteractor userInteractor);
}

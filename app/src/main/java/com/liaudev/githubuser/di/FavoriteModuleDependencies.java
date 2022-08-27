package com.liaudev.githubuser.di;

import com.liaudev.githubuser.core.domain.usecase.UserUseCase;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * Created by Budiliauw87 on 2022-08-22.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@EntryPoint
@InstallIn(SingletonComponent.class)
public interface FavoriteModuleDependencies { UserUseCase usercase();}

package com.liaudev.githubuser.core.di;


import com.liaudev.githubuser.core.data.GithubRepository;
import com.liaudev.githubuser.core.domain.repository.IGithubRepository;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * Created by Budiliauw87 on 2022-08-17.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Module
@InstallIn({SingletonComponent.class})
abstract class RepositoryModule {

    @Binds
    abstract IGithubRepository provideRepository(GithubRepository githubRepository);
}

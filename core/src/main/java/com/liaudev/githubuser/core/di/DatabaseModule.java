package com.liaudev.githubuser.core.di;

import android.content.Context;

import androidx.room.Room;

import com.liaudev.githubuser.core.data.local.room.UserDao;
import com.liaudev.githubuser.core.data.local.room.UserDatabase;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SupportFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

/**
 * Created by Budiliauw87 on 2022-08-17.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */

@Module
@InstallIn({SingletonComponent.class})
public class DatabaseModule {

    @Singleton
    @Provides
    static UserDatabase provideDatabase(@ApplicationContext Context context){
        final byte[] passphrase = SQLiteDatabase.getBytes("github_user".toCharArray());
        final SupportFactory factory = new SupportFactory(passphrase);
        return Room.databaseBuilder(context.getApplicationContext(),
                UserDatabase.class, "usergithubapp.db")
                .openHelperFactory(factory)
                .build();
    }

    @Provides
    static UserDao provideUserDao(UserDatabase database){
        return database.userDao();
    }

}

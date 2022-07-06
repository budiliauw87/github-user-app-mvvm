package com.liaudev.dicodingfa.di;

import android.content.Context;

import com.liaudev.dicodingfa.data.DataRepository;
import com.liaudev.dicodingfa.data.room.UserDatabase;
import com.liaudev.dicodingfa.network.ApiRequest;


/**
 * Created by Budiliauw87 on 2022-06-21.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class Injection {
    public static DataRepository provideRepository(Context context) {
        ApiRequest apiRequest = new ApiRequest(context);
        UserDatabase userDatabase = UserDatabase.getInstance(context);
        return DataRepository.getInstance(apiRequest, userDatabase);
    }
}

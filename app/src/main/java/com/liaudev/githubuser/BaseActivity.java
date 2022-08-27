package com.liaudev.githubuser;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

/**
 * Created by Budiliauw87 on 2022-06-21.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(useCustomConfig(newBase));
    }

    private Context useCustomConfig(Context context) {
        final Locale locale = new Locale(MyApp.getInstance().getLangApp());
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
        return context;
    }



}

package com.liaudev.githubuser.core.security;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceDataStore;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.liaudev.githubuser.core.R;
import com.securepreferences.SecurePreferences;

import java.util.Set;

/**
 * Created by Budiliauw87 on 2022-08-25.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class EncryptedPref extends PreferenceDataStore {
    private SharedPreferences sharedPref;

    public EncryptedPref(Context context) {
        try {
            final String settingPrefName = context.getString(R.string.setting_pref_name);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
                sharedPref = EncryptedSharedPreferences.create(
                        settingPrefName,
                        masterKeyAlias,
                        context,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );
            } else {
                sharedPref = new SecurePreferences(context, "GithubUser", settingPrefName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void putString(String key, @Nullable String value) {
        sharedPref.edit().putString(key, value).apply();
    }

    @Override
    public void putInt(String key, int value) {
        sharedPref.edit().putInt(key, value).apply();
    }

    @Override
    public void putStringSet(String key, @Nullable Set<String> values) {
        sharedPref.edit().putStringSet(key, values).apply();
    }

    @Override
    public void putLong(String key, long value) {
        sharedPref.edit().putLong(key, value).apply();
    }

    @Override
    public void putFloat(String key, float value) {
        sharedPref.edit().putFloat(key, value).apply();
    }

    @Override
    public void putBoolean(String key, boolean value) {
        sharedPref.edit().putBoolean(key, value).apply();
    }

    @Nullable
    @Override
    public String getString(String key, @Nullable String defValue) {
        return sharedPref.getString(key, defValue);
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        return sharedPref.getStringSet(key, defValues);
    }

    @Override
    public int getInt(String key, int defValue) {
        return sharedPref.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return sharedPref.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return sharedPref.getFloat(key, defValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return sharedPref.getBoolean(key, defValue);
    }

    public SharedPreferences getSharedPref(){
        return this.sharedPref;
    }
}

package com.liaudev.githubuser.setting;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import com.liaudev.githubuser.MyApp;
import com.liaudev.githubuser.R;
import com.liaudev.githubuser.core.security.EncryptedPref;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Created by Budiliauw87 on 2022-08-21.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@AndroidEntryPoint
public class SettingFragment extends PreferenceFragmentCompat {
    private static final String TAG = "SettingFragment";
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        getPreferenceManager().setPreferenceDataStore(new EncryptedPref(requireContext()));
        setPreferencesFromResource(R.xml.preferences, rootKey);
        try {
            //Select Theme
            findPreference("theme_mode").setOnPreferenceChangeListener((preference, newValue) -> {
                String themeOption = (String) newValue;
                if (!MyApp.getInstance().getThemeMode().equals(themeOption)) {
                    MyApp.getInstance().setThemeMode(themeOption);
                    MyApp.getInstance().applyTheme();
                }
                return true;
            });
            //Select Language
            findPreference("language_app").setOnPreferenceChangeListener((preference, newValue) -> {
                String languageOption = (String) newValue;
                if (!MyApp.getInstance().getLangApp().equals(languageOption)) {
                    MyApp.getInstance().setLangApp(languageOption);
                    getActivity().recreate();
                }
                return true;
            });
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }
}

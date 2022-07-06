package com.liaudev.dicodingfa.ui.settings;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.liaudev.dicodingfa.App;
import com.liaudev.dicodingfa.R;

/**
 * Created by Budiliauw87 on 2022-06-21.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class SettingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        //Select Theme
        ((ListPreference) findPreference("theme_mode")).setOnPreferenceChangeListener((preference, newValue) -> {
            String themeOption = (String) newValue;
            if (!App.getInstance().getThemeMode().equals(themeOption)) {
                App.getInstance().saveThemeMode(themeOption);
                App.getInstance().applyTheme();
            }
            return true;
        });

        ((ListPreference) findPreference("language_app")).setOnPreferenceChangeListener((preference, newValue) -> {
            String languageOption = (String) newValue;
            if (!App.getInstance().getLangApp().equals(languageOption)) {
                App.getInstance().saveLanguage(languageOption);
                getActivity().recreate();
            }
            return true;
        });
    }
}

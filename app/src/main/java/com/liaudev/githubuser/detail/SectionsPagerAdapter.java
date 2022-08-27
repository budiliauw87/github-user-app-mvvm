package com.liaudev.githubuser.detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.liaudev.githubuser.core.data.remote.network.Constants;
import com.liaudev.githubuser.follow.FollowFragment;

/**
 * Created by Budiliauw87 on 2022-06-26.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class SectionsPagerAdapter extends FragmentStateAdapter {
    private String loginName;

    public SectionsPagerAdapter(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
    }

    public void setLoginName(String login) {
        this.loginName = login;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        FollowFragment fragment = new FollowFragment();
        Bundle arguments = new Bundle();
        arguments.putString(Constants.LOGIN_NAME, loginName);
        arguments.putInt(Constants.METHOD_FOLLOW_ARG, position);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

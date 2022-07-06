package com.liaudev.dicodingfa.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.liaudev.dicodingfa.network.Constants;
import com.liaudev.dicodingfa.ui.follower.FollowerFragment;
import com.liaudev.dicodingfa.ui.following.FollowingFragment;

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
    public void setLoginName(String login){
        this.loginName =login;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle arguments = new Bundle();
        arguments.putString(Constants.LOGIN_NAME,loginName);
        if (position == 0) {
            FollowerFragment followerFragment = new FollowerFragment();
            followerFragment.setArguments(arguments);
            return followerFragment;
        } else if (position == 1) {
            FollowingFragment followingFragment = new FollowingFragment();
            followingFragment.setArguments(arguments);
            return followingFragment;
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

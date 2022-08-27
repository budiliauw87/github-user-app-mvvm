package com.liaudev.githubuser.core.adapter;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.liaudev.githubuser.core.data.local.entity.UserEntity;

/**
 * Created by Budiliauw87 on 2022-08-23.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class ComparatorUserEntity extends DiffUtil.ItemCallback<UserEntity> {
    @Override
    public boolean areItemsTheSame(@NonNull UserEntity oldItem, @NonNull UserEntity newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull UserEntity oldItem, @NonNull UserEntity newItem) {
        return oldItem.equals(newItem);
    }
}
package com.liaudev.githubuser.core.adapter;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.liaudev.githubuser.core.domain.model.User;


/**
 * Created by Budiliauw87 on 2022-06-22.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class ComparatorUser extends DiffUtil.ItemCallback<User> {
    @Override
    public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
        return oldItem.equals(newItem);
    }
}

package com.liaudev.dicodingfa.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;

import com.liaudev.dicodingfa.data.room.User;
import com.liaudev.dicodingfa.databinding.RowItemUserBinding;

/**
 * Created by Budiliauw87 on 2022-06-22.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class AdapterUser extends PagingDataAdapter<User, UserViewHolder> {

    public static final int LOADING_ITEM = 0;
    public static final int STORY_ITEM = 1;

    public AdapterUser() {
        super(new ComparatorUser());
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(RowItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = getItem(position);
        // Check for null
        holder.bind(user);
    }

    @Override
    public int getItemViewType(int position) {
        // set ViewType
        return position == getItemCount() ? STORY_ITEM : LOADING_ITEM;
    }


}

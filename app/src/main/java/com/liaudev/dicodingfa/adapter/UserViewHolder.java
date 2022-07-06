package com.liaudev.dicodingfa.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.liaudev.dicodingfa.GlideApp;
import com.liaudev.dicodingfa.R;
import com.liaudev.dicodingfa.data.room.User;
import com.liaudev.dicodingfa.databinding.RowItemUserBinding;
import com.liaudev.dicodingfa.ui.detail.DetailActivity;

/**
 * Created by Budiliauw87 on 2022-06-22.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class UserViewHolder extends RecyclerView.ViewHolder {
    RowItemUserBinding itemUserBinding;

    public UserViewHolder(@NonNull RowItemUserBinding binding) {
        super(binding.getRoot());
        itemUserBinding = binding;
    }

    public void bind(User user) {
        if (user != null) {
            itemView.setOnClickListener((v) -> {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra("userObject", user);
                itemView.getContext().startActivity(intent);
            });
            itemUserBinding.tvName.setText(user.getLogin());
            itemUserBinding.tvCompany.setText(user.getCompany());
            itemUserBinding.tvEmail.setText(user.getEmail());
            if(user.getEmail().isEmpty()){
                itemUserBinding.tvEmail.setVisibility(View.GONE);
            }else{
                itemUserBinding.tvEmail.setText(user.getEmail());
            }
            itemUserBinding.tvTotalFollower.setText(String.valueOf(user.getFollower()));
            itemUserBinding.tvTotalFollowing.setText(String.valueOf(user.getFollowing()));
            itemUserBinding.tvTotalRepo.setText(String.valueOf(user.getRepositories()));

            RequestOptions options = new RequestOptions();
            RequestBuilder<Drawable> requestBuilder = GlideApp.with(itemView.getContext())
                    .asDrawable().sizeMultiplier(0.25f);
            options.placeholder(R.drawable.ic_launcher_foreground);
            options.circleCrop();
            GlideApp.with(itemView.getContext())
                    .load(user.getAvatarUrl()) // Load the image
                    .thumbnail(requestBuilder)
                    .apply(options)
                    .into(itemUserBinding.thumbnail);
        }

    }
}

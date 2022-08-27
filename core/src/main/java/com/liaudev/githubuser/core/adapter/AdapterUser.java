package com.liaudev.githubuser.core.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.liaudev.githubuser.core.R;
import com.liaudev.githubuser.core.databinding.RowItemUserBinding;
import com.liaudev.githubuser.core.domain.model.User;

/**
 * Created by Budiliauw87 on 2022-06-22.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class AdapterUser extends PagingDataAdapter<User, AdapterUser.UserViewHolder> {

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

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        RowItemUserBinding itemUserBinding;

        public UserViewHolder(@NonNull RowItemUserBinding binding) {
            super(binding.getRoot());
            itemUserBinding = binding;
        }

        public void bind( User user ) {
            if (user != null) {
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
                RequestBuilder<Drawable> requestBuilder = Glide.with(itemView.getContext())
                        .asDrawable().sizeMultiplier(0.25f);
                options.placeholder(R.drawable.ic_launcher_foreground);
                options.circleCrop();
                Glide.with(itemView.getContext())
                        .load(user.getAvatarUrl()) // Load the image
                        .thumbnail(requestBuilder)
                        .apply(options)
                        .into(itemUserBinding.thumbnail);
                itemUserBinding.getRoot().setOnClickListener((v) -> {
                    Intent intent = null;
                    try {
                        intent = new Intent(itemView.getContext(), Class.forName("com.liaudev.githubuser.detail.DetailActivity"));
                        intent.putExtra("userObject", user);
                        itemView.getContext().startActivity(intent);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(itemView.getContext(), R.string.something_error, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
    }
}

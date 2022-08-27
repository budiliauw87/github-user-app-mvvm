package com.liaudev.githubuser.detail;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.liaudev.githubuser.BaseActivity;
import com.liaudev.githubuser.R;
import com.liaudev.githubuser.core.domain.model.User;
import com.liaudev.githubuser.databinding.ActivityDetailBinding;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailActivity extends BaseActivity {
    private ActivityDetailBinding binding;
    private boolean isFavorite = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DetailViewModel detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        User user = getIntent().getParcelableExtra("userObject");
        if (user != null) {
            binding.tvName.setText(user.getLogin());
            binding.tvUserName.setText(user.getName());
            binding.tvCompany.setText(user.getCompany());
            binding.tvEmail.setText(user.getEmail());
            binding.tvLocation.setText(user.getLocation());

            RequestOptions options = new RequestOptions();
            RequestBuilder<Drawable> requestBuilder = Glide.with(DetailActivity.this)
                    .asDrawable().sizeMultiplier(0.25f);
            options.placeholder(R.drawable.ic_launcher_foreground);
            options.circleCrop();
            Glide.with(DetailActivity.this)
                    .load(user.getAvatarUrl()) // Load the image
                    .thumbnail(requestBuilder)
                    .apply(options)
                    .into(binding.thumbnail);

            binding.tvTotalRepo.setText(String.format(getString(R.string.repo_format), user.getRepositories()));

            TabLayout tabs = binding.tablayout;
            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this);
            sectionsPagerAdapter.setLoginName(user.getLogin());
            ViewPager2 viewPager2 = binding.viewpager;
            viewPager2.setAdapter(sectionsPagerAdapter);
            new TabLayoutMediator(tabs, viewPager2, (tab, position) -> {
                tab.setCustomView(R.layout.row_tab_item);
                View v = tab.getCustomView();
                if (position == 0) {
                    ((TextView) Objects.requireNonNull(v).findViewById(R.id.tabTitle)).setText(String.valueOf(user.getFollower()));
                    ((TextView) v.findViewById(R.id.tabSubTitle)).setText(getString(R.string.follower));
                } else if (position == 1) {
                    ((TextView) Objects.requireNonNull(v).findViewById(R.id.tabTitle)).setText(String.valueOf(user.getFollowing()));
                    ((TextView) v.findViewById(R.id.tabSubTitle)).setText(getString(R.string.following));
                }
            }).attach();


            binding.btnFavorite.setOnClickListener((v) -> {
                if (isFavorite) {
                    detailViewModel.deleteFavorite(user.getId());
                } else {
                    detailViewModel.setFavorite(user);
                }
            });

            detailViewModel.getUser(user.getId()).observe(this, userObj -> {
                if (userObj.getId() == null) {
                    binding.btnFavorite.setIconTint(ContextCompat.getColorStateList(this, R.color.white));
                    isFavorite = false;
                } else {
                    binding.btnFavorite.setIconTint(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
                    isFavorite = true;
                }
            });
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}
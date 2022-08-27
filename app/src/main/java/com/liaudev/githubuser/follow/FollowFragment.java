package com.liaudev.githubuser.follow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.ExperimentalPagingApi;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.liaudev.githubuser.core.adapter.AdapterLoadState;
import com.liaudev.githubuser.core.adapter.AdapterUser;
import com.liaudev.githubuser.core.data.remote.network.Constants;
import com.liaudev.githubuser.databinding.FragmentFollowBinding;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Created by Budiliauw87 on 2022-08-24.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@AndroidEntryPoint
public class FollowFragment extends Fragment {
    private FragmentFollowBinding binding;

    public FollowFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFollowBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @ExperimentalPagingApi
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FollowViewModel viewModel = new ViewModelProvider(this).get(FollowViewModel.class);
        binding.swipeRefresh.setRefreshing(true);
        AdapterUser adapterUser = new AdapterUser();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapterUser.withLoadStateFooter(
                new AdapterLoadState((v) -> adapterUser.retry())
        ));
        adapterUser.addLoadStateListener(combinedLoadStates -> {
            if (combinedLoadStates.getRefresh() instanceof LoadState.NotLoading
                    && combinedLoadStates.getPrepend().getEndOfPaginationReached()) {
                if (adapterUser.getItemCount() > 0) {
                    hideErrorLayout();
                } else {
                    showErrorLayout();
                }
            } else if (combinedLoadStates.getRefresh() instanceof LoadState.Error) {
                showErrorLayout();
            }

            return null;
        });
        binding.swipeRefresh.setOnRefreshListener(() -> {
            if (binding.layoutError.getVisibility() == View.VISIBLE) {
                binding.layoutError.setVisibility(View.GONE);
            }
            if (binding.recyclerView.getVisibility() == View.VISIBLE) {
                binding.recyclerView.setVisibility(View.GONE);
            }
            adapterUser.refresh();
        });
        binding.btnRefresh.setOnClickListener((v) -> {
            binding.swipeRefresh.setRefreshing(true);
            binding.layoutError.setVisibility(View.GONE);
            adapterUser.refresh();
        });
        Bundle args = getArguments();
        if (args != null) {
            final String loginName = args.getString(Constants.LOGIN_NAME);
            final int methodFollow = args.getInt(Constants.METHOD_FOLLOW_ARG, 0) == 0 ? 1 : 2;
            viewModel.setLoginName(loginName);
            viewModel.getFollow(methodFollow).observe(getViewLifecycleOwner(), (pagingData -> adapterUser.submitData(getLifecycle(), pagingData)));
        }

    }

    private void showErrorLayout() {
        try {
            if (binding.recyclerView.getVisibility() == View.VISIBLE) {
                binding.recyclerView.setVisibility(View.GONE);
            }
            if (binding.layoutError.getVisibility() == View.GONE) {
                binding.layoutError.setVisibility(View.VISIBLE);
            }
            if (binding.swipeRefresh.isRefreshing()) {
                binding.swipeRefresh.setRefreshing(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void hideErrorLayout() {
        try {
            if (binding.layoutError.getVisibility() == View.VISIBLE) {
                binding.layoutError.setVisibility(View.GONE);
            }
            if (binding.recyclerView.getVisibility() == View.GONE) {
                binding.recyclerView.setVisibility(View.VISIBLE);
            }
            if (binding.swipeRefresh.isRefreshing()) {
                binding.swipeRefresh.setRefreshing(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}

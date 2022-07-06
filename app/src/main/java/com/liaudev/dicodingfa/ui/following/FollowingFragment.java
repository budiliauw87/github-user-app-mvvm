package com.liaudev.dicodingfa.ui.following;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.liaudev.dicodingfa.adapter.AdapterLoadState;
import com.liaudev.dicodingfa.adapter.AdapterUser;
import com.liaudev.dicodingfa.databinding.FragmentFollowingBinding;
import com.liaudev.dicodingfa.network.Constants;
import com.liaudev.dicodingfa.viewmodel.ViewModelFactory;

/**
 * Created by Budiliauw87 on 2022-06-26.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class FollowingFragment extends Fragment {

    private FragmentFollowingBinding binding;
    private FollowingViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFollowingBinding.inflate(inflater, container, false);
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
        viewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factory).get(FollowingViewModel.class);
        return binding.getRoot();
    }

    @SuppressLint("UnsafeOptInUsageError")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.swipeRefresh.setRefreshing(true);
        AdapterUser adapterUser = new AdapterUser();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapterUser.withLoadStateFooter(
                new AdapterLoadState((v) -> {
                    adapterUser.retry();
                })
        ));
        adapterUser.addLoadStateListener(combinedLoadStates -> {
            if (combinedLoadStates.getRefresh() instanceof LoadState.NotLoading && combinedLoadStates.getPrepend().getEndOfPaginationReached() == true) {
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
        if(args!=null){
            String loginName = args.getString(Constants.LOGIN_NAME);
            viewModel.setLoginName(loginName);
        }

        viewModel.getFollowers().observe(getViewLifecycleOwner(), (pagingData -> {
            adapterUser.submitData(getLifecycle(), pagingData);
        }));
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
}

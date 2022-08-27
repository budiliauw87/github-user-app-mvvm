package com.liaudev.githubuser.favoritefeature;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.liaudev.githubuser.core.adapter.AdapterFavorite;
import com.liaudev.githubuser.core.adapter.AdapterLoadState;
import com.liaudev.githubuser.di.FavoriteModuleDependencies;
import com.liaudev.githubuser.favoritefeature.databinding.FragmentFavoriteBinding;

import javax.inject.Inject;

import dagger.hilt.android.EntryPointAccessors;

public class FavoriteFragment extends Fragment {
    private static final String TAG = "FavoriteFragment";
    private FragmentFavoriteBinding binding;
    @Inject
    FavoriteViewModel viewModel;

    public FavoriteFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initCoreDependentInjection();
        super.onCreate(savedInstanceState);
    }

    private void initCoreDependentInjection() {
        if (getActivity() != null) {
            DaggerFavoriteComponent.builder()
                    .context(requireActivity())
                    .appDependencies(
                            EntryPointAccessors.fromApplication(
                                    getActivity().getApplicationContext(),
                                    FavoriteModuleDependencies.class
                            )
                    ).build().inject(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AdapterFavorite adapterFavorite = new AdapterFavorite();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapterFavorite.withLoadStateFooter(
                new AdapterLoadState((v) -> {
                    adapterFavorite.retry();
                })
        ));

        adapterFavorite.addLoadStateListener(combinedLoadStates -> {
            if (combinedLoadStates.getRefresh() instanceof LoadState.NotLoading
                    && combinedLoadStates.getPrepend().getEndOfPaginationReached()) {
                if (adapterFavorite.getItemCount() > 0) {
                    hideErrorLayout();
                } else {
                    showErrorLayout();
                }
            }

            return null;
        });
        viewModel.getFavorite().observe(getViewLifecycleOwner(), (pagingData -> {
            adapterFavorite.submitData(getLifecycle(), pagingData);
        }));

    }

    private void showErrorLayout() {
        try {
            if (binding != null) {
                if (binding.recyclerView.getVisibility() == View.VISIBLE) {
                    binding.recyclerView.setVisibility(View.GONE);
                }
                if (binding.tvError.getVisibility() == View.GONE) {
                    binding.tvError.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void hideErrorLayout() {
        try {
            if (binding != null) {
                if (binding.tvError.getVisibility() == View.VISIBLE) {
                    binding.tvError.setVisibility(View.GONE);
                }
                if (binding.recyclerView.getVisibility() == View.GONE) {
                    binding.recyclerView.setVisibility(View.VISIBLE);
                }
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
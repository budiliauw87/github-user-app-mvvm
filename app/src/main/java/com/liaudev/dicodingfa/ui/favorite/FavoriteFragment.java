package com.liaudev.dicodingfa.ui.favorite;

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
import com.liaudev.dicodingfa.databinding.FragmentFavoriteBinding;
import com.liaudev.dicodingfa.viewmodel.ViewModelFactory;

/**
 * Created by Budiliauw87 on 2022-06-21.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class FavoriteFragment extends Fragment {
    private FragmentFavoriteBinding binding;
    private FavoriteViewModel favoriteViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
        favoriteViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factory).get(FavoriteViewModel.class);
        return binding.getRoot();
    }

    @SuppressLint("UnsafeOptInUsageError")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
            }

            return null;
        });
        favoriteViewModel.getFavorite().observe(getViewLifecycleOwner(), (pagingData -> {
            adapterUser.submitData(getLifecycle(), pagingData);
        }));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void showErrorLayout() {
        try {
            if (binding.recyclerView.getVisibility() == View.VISIBLE) {
                binding.recyclerView.setVisibility(View.GONE);
            }
            if (binding.layoutError.getVisibility() == View.GONE) {
                binding.layoutError.setVisibility(View.VISIBLE);
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

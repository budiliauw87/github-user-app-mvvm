package com.liaudev.dicodingfa.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.liaudev.dicodingfa.R;
import com.liaudev.dicodingfa.adapter.AdapterLoadState;
import com.liaudev.dicodingfa.adapter.AdapterUser;
import com.liaudev.dicodingfa.custom.MySearchView;
import com.liaudev.dicodingfa.databinding.FragmentHomeBinding;
import com.liaudev.dicodingfa.viewmodel.ViewModelFactory;

/**
 * Created by Budiliauw87 on 2022-06-21.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private long lastBackPressed;
    private String QuerySearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
        homeViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factory).get(HomeViewModel.class);
        return binding.getRoot();
    }

    @SuppressLint("UnsafeOptInUsageError")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (lastBackPressed + 1000 > System.currentTimeMillis()) {
                    getActivity().finish();
                    return;
                }
                lastBackPressed = System.currentTimeMillis();
                Toast.makeText(getActivity(), R.string.press_twice_for_exit, Toast.LENGTH_SHORT).show();
            }
        });
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

        homeViewModel.getUsers().observe(getViewLifecycleOwner(), (pagingData -> {
            adapterUser.submitData(getLifecycle(), pagingData);
        }));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        MySearchView searchView = (MySearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint(getString(R.string.search_title));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (binding.layoutError.getVisibility() == View.VISIBLE) {
                    binding.layoutError.setVisibility(View.GONE);
                }
                if (binding.recyclerView.getVisibility() == View.VISIBLE) {
                    binding.recyclerView.setVisibility(View.GONE);
                }
                binding.swipeRefresh.setRefreshing(true);
                homeViewModel.findUser(QuerySearch);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    QuerySearch = newText;
                } else {
                    QuerySearch = null;
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
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
        super.onDestroyView();
        binding = null;
    }
}
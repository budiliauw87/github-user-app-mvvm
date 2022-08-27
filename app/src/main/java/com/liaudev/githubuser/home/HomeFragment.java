package com.liaudev.githubuser.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.liaudev.githubuser.R;
import com.liaudev.githubuser.core.adapter.AdapterLoadState;
import com.liaudev.githubuser.core.adapter.AdapterUser;
import com.liaudev.githubuser.customview.MySearchView;
import com.liaudev.githubuser.databinding.FragmentHomeBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private long lastBackPressed;
    private String QuerySearch;
    private AdapterUser adapterUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getActivity()!=null){

            requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    if (lastBackPressed + 1000 > System.currentTimeMillis()) {
                        getActivity().finish();
                        return;
                    }
                    lastBackPressed = System.currentTimeMillis();
                    Toast.makeText(getActivity(), R.string.press_again_for_exit, Toast.LENGTH_SHORT).show();
                }
            });

            //menu
            requireActivity().addMenuProvider(new MenuProvider() {
                @Override
                public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                    menuInflater.inflate(R.menu.menu_home, menu);
                    MySearchView searchView = (MySearchView) menu.findItem(R.id.search).getActionView();
                    searchView.setQueryHint(getString(R.string.search_title));
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            hideAllLayout();
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

                }

                @Override
                public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                    return false;
                }
            }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);


            binding.swipeRefresh.setRefreshing(true);
            adapterUser = new AdapterUser();
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            binding.recyclerView.setAdapter(adapterUser.withLoadStateFooter(
                    new AdapterLoadState((v) -> adapterUser.retry())
            ));

            adapterUser.addLoadStateListener(combinedLoadStates -> {
                if (combinedLoadStates.getRefresh() instanceof LoadState.NotLoading &&
                        combinedLoadStates.getPrepend().getEndOfPaginationReached()) {
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
                hideAllLayout();
                adapterUser.refresh();
            });
            binding.layoutError.btnRefresh.setOnClickListener((v) -> {
                binding.swipeRefresh.setRefreshing(true);
                binding.layoutError.getRoot().setVisibility(View.GONE);
                adapterUser.refresh();
            });
            homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
            homeViewModel.getUsers().observe(getViewLifecycleOwner(), (pagingData -> adapterUser.submitData(getLifecycle(), pagingData)));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.swipeRefresh.setEnabled(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.swipeRefresh.setEnabled(false);
    }

    @Override
    public void onDestroyView() {
        binding.recyclerView.setAdapter(null);
        adapterUser = null;
        binding = null;
        super.onDestroyView();

    }

    private void hideAllLayout() {

        try {
            if (binding != null) {
                if (binding.layoutError.getRoot().getVisibility() == View.VISIBLE) {
                    binding.layoutError.getRoot().setVisibility(View.GONE);
                }
                if (binding.recyclerView.getVisibility() == View.VISIBLE) {
                    binding.recyclerView.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showErrorLayout() {
        try {
            if (binding != null) {
                if (binding.recyclerView.getVisibility() == View.VISIBLE) {
                    binding.recyclerView.setVisibility(View.GONE);
                }
                if (binding.layoutError.getRoot().getVisibility() == View.GONE) {
                    binding.layoutError.getRoot().setVisibility(View.VISIBLE);
                }
                if (binding.swipeRefresh.isRefreshing()) {
                    binding.swipeRefresh.setRefreshing(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void hideErrorLayout() {
        try {
            if (binding != null) {
                if (binding.layoutError.getRoot().getVisibility() == View.VISIBLE) {
                    binding.layoutError.getRoot().setVisibility(View.GONE);
                }
                if (binding.recyclerView.getVisibility() == View.GONE) {
                    binding.recyclerView.setVisibility(View.VISIBLE);
                }
                if (binding.swipeRefresh.isRefreshing()) {
                    binding.swipeRefresh.setRefreshing(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
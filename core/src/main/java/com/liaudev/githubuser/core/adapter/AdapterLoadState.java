package com.liaudev.githubuser.core.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.liaudev.githubuser.core.R;
import com.liaudev.githubuser.core.databinding.RowLoadStateBinding;

/**
 * Created by Budiliauw87 on 2022-06-22.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class AdapterLoadState extends LoadStateAdapter<AdapterLoadState.LoadStateViewHolder> {
    final private View.OnClickListener mRetryCallback;

    public AdapterLoadState(View.OnClickListener retryCallback) {
        mRetryCallback = retryCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull LoadStateViewHolder loadStateViewHolder, @NonNull LoadState loadState) {
        loadStateViewHolder.bind(loadState);
    }

    @NonNull
    @Override
    public LoadStateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull LoadState loadState) {
        return new LoadStateViewHolder(viewGroup, mRetryCallback);
    }

    public static class LoadStateViewHolder extends RecyclerView.ViewHolder {
        private final ProgressBar mProgressBar;
        private final TextView mErrorMsg;
        private final Button mRetry;

        LoadStateViewHolder(@NonNull ViewGroup parent, @NonNull View.OnClickListener retryCallback) {
            super(LayoutInflater.from(parent.getContext()) .inflate(R.layout.row_load_state, parent, false));
            RowLoadStateBinding binding = RowLoadStateBinding.bind(itemView);
            mProgressBar = binding.progressBar;
            mErrorMsg = binding.errorMsg;
            mRetry = binding.retryButton;
            mRetry.setOnClickListener(retryCallback);
        }

        public void bind(LoadState loadState) {
            if (loadState instanceof LoadState.Error) {
                LoadState.Error loadStateError = (LoadState.Error) loadState;
                mErrorMsg.setText(loadStateError.getError().getLocalizedMessage());
            }
            mProgressBar.setVisibility(loadState instanceof LoadState.Loading
                    ? View.VISIBLE : View.GONE);
            mRetry.setVisibility(loadState instanceof LoadState.Error
                    ? View.VISIBLE : View.GONE);
            mErrorMsg.setVisibility(loadState instanceof LoadState.Error
                    ? View.VISIBLE : View.GONE);
        }
    }
}

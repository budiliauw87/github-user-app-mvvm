package com.liaudev.githubuser.customview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

/**
 * Created by Budiliauw87 on 2022-06-22.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class MySearchView extends SearchView {
    SearchAutoComplete mSearchSrcTextView;
    OnQueryTextListener listener;

    public MySearchView(@NonNull Context context) {
        super(context);
    }

    public MySearchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MySearchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnQueryTextListener(OnQueryTextListener listener) {
        super.setOnQueryTextListener(listener);
        this.listener = listener;
        mSearchSrcTextView = this.findViewById(androidx.appcompat.R.id.search_src_text);
        mSearchSrcTextView.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (listener != null) {
                listener.onQueryTextSubmit(getQuery().toString());
            }
            return true;
        });
    }
}

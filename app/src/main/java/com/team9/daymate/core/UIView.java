package com.team9.daymate.core;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public abstract class UIView extends Fragment {
    private ViewModel viewModel;

    public UIView(@Nullable Bundle InstanceState, @LayoutRes int layoutRes) {
        super(layoutRes);
        if (InstanceState != null) {
            this.setArguments(InstanceState);
        }
    }

    public abstract void onViewAction(View view);

    public void setViewModel(Class<? extends ViewModel> cls) {
        this.viewModel = (new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())).get(cls);
    }

    public ViewModel getViewModel() {
        return this.viewModel;
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.onViewAction(view);
    }
}


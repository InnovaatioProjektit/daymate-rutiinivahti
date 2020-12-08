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

import com.team9.daymate.example.TestViewModel;

import java.lang.reflect.Type;

public abstract class UIView extends Fragment {
    protected ViewModel viewModel;

    public UIView(@Nullable Bundle InstanceState, @LayoutRes int layoutRes) {
        super(layoutRes);
        if (InstanceState != null) {
            this.setArguments(InstanceState);
        }
    }

    public abstract void onViewAction(View view);

    public  <T extends ViewModel> void setViewModel(Class<T> cls) {
        this.viewModel = (T)(new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())).get(cls);
    }


    @SuppressWarnings("unchecked")
    public<T extends ViewModel> T getViewModel(Class<T> cls) {
        return (T) this.viewModel;
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


package com.team9.daymate.example;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.team9.daymate.R;
import com.team9.daymate.core.UIView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;


// voi kopioida tästä alaspäin

public class TestFragment extends UIView {

    public TestFragment(@Nullable Bundle InstanceState) {
        super(InstanceState, R.layout.routine_daily_fragment);

    }

    public void onViewAction(View view) {
        // Täällä muokataan ja hallitaan ViewLayouttia:

        // TextView tv = view.findViewById(R.id.testView)
        // tv.setText("Hello World");

        // tai vaikkapa näin:

        //String teksti = getViewModel(TestViewModel.class).dataString;

        //tv.setText(teksti);


    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        // Täällä lisätään ViewModel ja muokataan toisesta fragmentista tai aktivitista tulleita argumentteja
        // ViewModel on hyvä käyttää ActivityCreated metoodissa
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
        }

        setViewModel(TestViewModel.class);


    }
}
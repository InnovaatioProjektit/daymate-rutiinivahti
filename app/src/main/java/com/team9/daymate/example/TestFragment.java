package com.team9.daymate.example;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.team9.daymate.R;
import com.team9.daymate.core.UIView;


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

        //tv.setText(viewModel.teksti)


    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        // Täällä ainoastaan lisätään ViewModel ja muokataan toisesta fragmentista tai aktivitista tulleita argumentteja
        // ViewModel on hyvä käyttää ActivityCreated metoodissa
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
        }

       this.setViewModel(TestViewModel.class);
    }
}
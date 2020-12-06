package com.team9.daymate.shop;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.team9.daymate.R;
import com.team9.daymate.core.UIView;
import com.team9.daymate.routines.RoutineViewModel;

public class ShopStoreFragment extends UIView {

    public ShopStoreFragment(@Nullable Bundle InstanceState) {
        super(InstanceState, R.layout.shop_catalogue_fragment);
    }

    public void onViewAction(View view) {

    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
        }

        this.setViewModel(ShopViewModel.class);
    }
}

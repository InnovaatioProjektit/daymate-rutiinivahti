package com.team9.daymate.routines;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.team9.daymate.R;
import com.team9.daymate.core.UIView;
import com.team9.daymate.viewModels.RoutineViewModel;

/**
 * Avaa rutiinien kategorialistan jossa valitaan minkä luokan rutiini halutaan lisätä
 *
 * @author Alexander L
 *
 */
public class RoutineCreationFragment extends UIView {

    public RoutineCreationFragment(@Nullable Bundle InstanceState) {
        super(InstanceState, R.layout.routine_category_fragment);
    }

    public void onViewAction(View view) {
        ListView lv = getViewModel(RoutineViewModel.class).populateCategoryList(getContext(), view);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getViewModel(RoutineViewModel.class).selectCategory(position);
                ((RoutineActivity)getContext()).replaceView(R.id.fragment_container, RoutinePickerFragment.class);
            }
        });


    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setViewModel(RoutineViewModel.class);

        getViewModel(RoutineViewModel.class).setCategories(getResources());
    }
}

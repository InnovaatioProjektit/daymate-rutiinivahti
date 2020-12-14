package com.team9.daymate.routines;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;

import com.team9.daymate.R;
import com.team9.daymate.core.AppDataLogic;
import com.team9.daymate.core.Presenter;
import com.team9.daymate.core.UIView;
import com.team9.daymate.viewModels.RoutineEditViewModel;
import com.team9.daymate.viewModels.RoutineViewModel;


/**
 * Näyttää kategoriassa olevat rutiinit ja lisää rutiinin käyttäjän listaan klikkauksesta
 *
 * @author Jaakko Buchelnikov
 */
public class RoutinePickerFragment extends UIView {

    public RoutinePickerFragment(@Nullable Bundle InstanceState) {
        super(InstanceState, R.layout.routine_grid_fragment);
    }

    public void onViewAction(View view) {
        GridView gv = getViewModel(RoutineViewModel.class).populateRoutineGrid(getContext(), view);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getSharedViewModel(RoutineEditViewModel.class).setSharedData(getViewModel(RoutineViewModel.class).sendToEditFragment(position));
                ((Presenter)getContext()).replaceView(R.id.fragment_container, RoutineEditFragment.class);
            }
        });
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setViewModel(RoutineViewModel.class);
    }
}

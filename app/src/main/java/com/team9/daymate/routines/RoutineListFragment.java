package com.team9.daymate.routines;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.team9.daymate.R;
import com.team9.daymate.core.AppDataLogic;
import com.team9.daymate.core.Presenter;
import com.team9.daymate.core.UIView;
import com.team9.daymate.viewModels.RoutineEditViewModel;
import com.team9.daymate.viewModels.RoutineViewModel;
import com.team9.daymate.viewModels.UserViewModel;


/**
 * Näytä kaikki käytössä olevat rutiinit
 *
 * @author Alexander L
 */
public class RoutineListFragment extends UIView {

    public RoutineListFragment(@Nullable Bundle InstanceState) {
        super(InstanceState, R.layout.routine_list_fragment);
    }

    public void onViewAction(View view) {
        ListView lv = getViewModel(UserViewModel.class).populateCategoryList(getContext(), view);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Presenter ra = (Presenter) getContext();
                getSharedViewModel(RoutineEditViewModel.class).setEditData(AppDataLogic.routines.get(position));
                ra.loadView(R.id.fragment_container, RoutineEditFragment.class);
            }
        });
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setViewModel(UserViewModel.class);
    }
}

package com.team9.daymate.routines;

import android.net.MailTo;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.team9.daymate.R;
import com.team9.daymate.adapters.MultiViewAdapter;
import com.team9.daymate.adapters.RoutineAdapter;
import com.team9.daymate.core.AppDataLogic;
import com.team9.daymate.core.RoutineObject;
import com.team9.daymate.core.UIView;
import com.team9.daymate.elements.CircularImageView;
import com.team9.daymate.viewModels.RoutineEditViewModel;

import java.util.ArrayList;


/**
 * Näytä päivittäiset rutiinit palkeissa
 *
 * @author Alexander L
 */
public class RoutineDailyFragment extends UIView {

    RecyclerView rv;
    MultiViewAdapter mad;


    public RoutineDailyFragment(@Nullable Bundle InstanceState) {
        super(InstanceState, R.layout.routine_daily_fragment);
    }

    public void onViewAction(View view) {
        rv = (RecyclerView) getView().findViewById(R.id.carousel);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(mad);

    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mad = new MultiViewAdapter(4, AppDataLogic.routines);


        mad.setObserver(new MultiViewAdapter.Observer() {
            @Override
            protected boolean OnViewCycle(MultiViewAdapter.ViewHolder holder, int position) {
                RoutineEditViewModel.FLAGS[] flags = {RoutineEditViewModel.FLAGS.ANYTIME, RoutineEditViewModel.FLAGS.MORNING,
                                                      RoutineEditViewModel.FLAGS.DAY, RoutineEditViewModel.FLAGS.EVENING};

                ArrayList<RoutineObject> roal = new ArrayList<>();

                for(RoutineObject rob : AppDataLogic.routines){
                    if(rob.getFlags().contains(flags[position])){
                        roal.add(rob);
                    }
                }

                mad.setRoutinas(roal);
                switch(position){
                    case 0: holder.iv.setImageResource(R.drawable.ic_time_56); break;
                    case 1: holder.iv.setImageResource(R.drawable.ic_morn_56); break;
                    case 2: holder.iv.setImageResource(R.drawable.ic_sun_56); break;
                    case 3: holder.iv.setImageResource(R.drawable.ic_night_56); break;
                }


                return false;
            }
        });

    }
}

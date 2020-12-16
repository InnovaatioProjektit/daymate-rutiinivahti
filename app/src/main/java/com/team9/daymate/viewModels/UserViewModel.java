package com.team9.daymate.viewModels;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import androidx.lifecycle.ViewModel;

import com.team9.daymate.R;
import com.team9.daymate.adapters.CategoryAdapter;
import com.team9.daymate.adapters.RoutineAdapter;
import com.team9.daymate.core.AppDataLogic;


/**
 * logiikka joka auttaa tallentamaan tietoja ja näyttämään sisäisessä muistissa olevat rutiinitiedot
 *
 * @author Jaakko Buchelnikov
 */
public class UserViewModel extends ViewModel {


    public String getPoints(int day){
        return "" + AppDataLogic.weekly_points[day];
    }

    public String getPoints(){
        return "" + AppDataLogic.weekly_points[AppDataLogic.today - 1];
    }

    public void addPoints(int day, int sum){
        AppDataLogic.weekly_points[day] += sum;
    }

    public ListView populateCategoryList(Context context, View view){
        ListView lv = view.findViewById(R.id.RoutineList);
        lv.setAdapter(new RoutineAdapter(context, R.layout.routine_card_mini, AppDataLogic.routines));
        return lv;
    }


}

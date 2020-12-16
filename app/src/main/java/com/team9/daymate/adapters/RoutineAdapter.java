package com.team9.daymate.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.team9.daymate.R;
import com.team9.daymate.core.AppDataLogic;
import com.team9.daymate.elements.CircularImageView;
import com.team9.daymate.core.RoutineObject;
import com.team9.daymate.viewModels.RoutineEditViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Muuttaa rutiinien ilmentymät käyttöliittymän listaksi
 *
 * @author Alexander L
 */
public class RoutineAdapter extends ArrayAdapter<RoutineObject> {

    private Context context;
    private List<RoutineObject> routines;
    @LayoutRes int resource;


    public RoutineAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public RoutineAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<RoutineObject> list) {
        super(context, 0 , list);
        this.context = context;
        this.routines = list;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        Context mContext;

        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(resource, parent,false);

        RoutineObject currentRoutine = routines.get(position);

        TextView tv = (TextView) listItem.findViewById(R.id.title);
        tv.setText(currentRoutine.getTitle());

        CircularImageView civ = (CircularImageView) listItem.findViewById(R.id.thumbnail);
        civ.setProgressMax((int)currentRoutine.getProgressMax());
        civ.setProgressWithAnimation(currentRoutine.getProgress());
        civ.setImageResource(currentRoutine.getIcon());
        civ.setColorFilter(Color.WHITE);
        civ.setCircleBackgroundColor(currentRoutine.getColor());


        //TODO: HACKING TO SYNC RESOURCE TO DATA, TOO LAZY TO SOLVE PROPER WAY
        civ.attachObject(currentRoutine);

        return listItem;
    }
}

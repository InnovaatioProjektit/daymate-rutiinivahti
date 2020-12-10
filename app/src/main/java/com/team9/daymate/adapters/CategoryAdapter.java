package com.team9.daymate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.team9.daymate.R;
import com.team9.daymate.elements.CircularImageView;
import com.team9.daymate.routines.RoutineObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends ArrayAdapter<RoutineObject> {

    private Context context;
    private List<RoutineObject> routines = new ArrayList<>();
    @LayoutRes int resource;


    public CategoryAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public CategoryAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<RoutineObject> list) {
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

        CardView cv = (CardView) listItem.findViewById(R.id.thumbnail);
        //cv.setCardBackgroundColor(currentRoutine.getColor());

        return listItem;
    }
}

package com.team9.daymate.adapters;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import androidx.recyclerview.widget.RecyclerView;

import com.team9.daymate.R;
import com.team9.daymate.core.AppDataLogic;
import com.team9.daymate.core.RoutineObject;
import com.team9.daymate.elements.CircularImageView;
import com.team9.daymate.elements.WrappedGridView;
import com.team9.daymate.viewModels.RoutineEditViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * Luo käyttöliittymän jossa on useita ainutlaatuisia kortteja vähän kuin ListView
 *
 * @author Alexander L
 */
public class MultiViewAdapter extends RecyclerView.Adapter<MultiViewAdapter.ViewHolder> {

    private RoutineEditViewModel.FLAGS currentCategoryFlag;
    private ArrayList<RoutineObject> routinas;
    private int carouselCounter;
    private Observer eventObserver;
    Context context;


    public <T> MultiViewAdapter(List<T> list){
        super();

    }

    public <T> MultiViewAdapter(int howMany, List<T> list){
        super();
        carouselCounter = howMany;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_card_daily, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);


        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return carouselCounter;
        //return routineList.size();
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        eventObserver.OnViewCycle(holder, position);

        holder.grid.setAdapter(new RoutineAdapter(context, R.layout.routine_card_grid, routinas));
        holder.grid.setOnItemClickListener((parent, view, position1, id) -> {

            RoutineObject ro = AppDataLogic.routines.get(position1);
            CircularImageView cv = view.findViewById(R.id.thumbnail);

            Log.d("VARASTO cv ", ""+ cv.getProgress());
            Log.d("VARASTO ro", ""+ ro.getProgress());

            ro.setProgress(ro.getProgress() + 1);
            cv.setProgressWithAnimation(ro.getProgress());


            if( (ro.getProgress() >= ro.getProgressMax()) && !ro.finished() ){

                View layout = LayoutInflater.from(context).inflate(R.layout.toast_message, (ViewGroup) view.findViewById(R.id.toast_root));

                Toast t = new Toast(context);
                t.setView(layout);
                t.setDuration(Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                t.show();
            }
        });
    }


    public void setRoutinas(ArrayList<RoutineObject> routinas) {
        this.routinas = routinas;
    }

    public void setObserver(Observer observer){
        eventObserver = observer;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView iv;
        public CardView  card;
        WrappedGridView grid;

        public ViewHolder(View itemView)
        {
            super(itemView);

            grid = itemView.findViewById(R.id.RoutineGrid);
            iv = itemView.findViewById(R.id.card_title);
            card = itemView.findViewById(R.id.card_view);
        }
    }



    abstract public static class Observer{
        protected abstract boolean OnViewCycle(ViewHolder holder, final int position);
        public <T> ArrayList<T> bindData(ArrayList<T> data){
            return data;
        }
    }
}

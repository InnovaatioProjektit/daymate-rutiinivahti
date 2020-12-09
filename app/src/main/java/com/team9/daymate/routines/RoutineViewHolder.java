package com.team9.daymate.routines;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class RoutineViewHolder extends RecyclerView.ViewHolder {
    private int position;
    public RoutineViewHolder(View view) {
        super(view);
    }
    protected abstract void clear();
    public void onBind(int position) {
        this.position = position;
        clear();
    }
    public int getCurrentPosition() {
        return position;
    }
}
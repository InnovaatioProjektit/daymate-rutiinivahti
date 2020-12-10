package com.team9.daymate.routines;

import androidx.annotation.ColorRes;

public class RoutineObject {
    private String title;
    private int icon;
    private int color;
    private int backgroundColor;
    private float progress;

    public RoutineObject(String title, int icon){
        this.title = title;
        this.icon = icon;
        progress = 0;
    }

    public RoutineObject(String title, int icon, int color, int backgroundColor){
        this.title = title;
        this.icon = icon;
        progress = 0;
    }

    public float getProgress() {
        return progress;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}

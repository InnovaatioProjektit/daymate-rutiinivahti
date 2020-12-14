package com.team9.daymate.core;

import androidx.core.math.MathUtils;

import com.team9.daymate.viewModels.RoutineEditViewModel;

import java.util.EnumSet;


/**
 * Tehtäväluokka sisältää yksittäisen rutiinin tiedot
 *
 * @author Alexander L
 */
public class RoutineObject {
    private String title;
    private int icon;
    private int color;
    private int backgroundColor;
    private float progress;
    private float progressMax;

    private EnumSet<RoutineEditViewModel.FLAGS> flags;

    public RoutineObject(String title, int icon){
        this.title = title;
        this.icon = icon;

        init();
    }

    public RoutineObject(String title, int icon, int color, int backgroundColor){
        this.title = title;
        this.icon = icon;
        this.color = color;
        this.backgroundColor = backgroundColor;

        init();
    }

    private void init(){
        progressMax = 1;
        progress = 0;
        flags = EnumSet.of(RoutineEditViewModel.FLAGS.NONE);
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

    public void setProgress(float progress) { this.progress = MathUtils.clamp(progress,0.0f, progressMax); }

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

    public void setProgressMax(float progressMax) { this.progressMax = progressMax; }

    public float getProgressMax() { return progressMax; }

    public void setFlags(EnumSet<RoutineEditViewModel.FLAGS> flags) {
        this.flags = flags;
    }

    public EnumSet<RoutineEditViewModel.FLAGS> getFlags() {
        return flags;
    }

    public boolean finished(){
        if(flags.contains(RoutineEditViewModel.FLAGS.FINISHED)){
            return true;
        }

        if(progress >= getProgressMax()){
            flags.add(RoutineEditViewModel.FLAGS.FINISHED);
            return false;
        }

        return false;
    }
}

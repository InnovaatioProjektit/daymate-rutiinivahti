package com.team9.daymate.core;

import androidx.annotation.Nullable;
import androidx.core.math.MathUtils;

import com.team9.daymate.viewModels.RoutineEditViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;
import java.util.UUID;


/**
 * Tehtäväluokka sisältää yksittäisen rutiinin tiedot
 *
 * @author Alexander L
 */
public class RoutineObject implements Serializable {
    public String uuid = UUID.randomUUID().toString();

    private String title;
    private int icon;
    private int color;
    private int backgroundColor;
    private float progress;
    private float progressMax;


    private EnumSet<RoutineEditViewModel.FLAGS> flags;

    public RoutineObject(){ }

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

    public void reset(){
        this.progress = 0;
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

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        try {
            json.putOpt("title", this.title);
            json.put("icon", this.icon);
            json.put("color", this.color);
            json.put("backgroundColor", this.color);
            json.put("progress", this.progress);
            json.put("max", this.progressMax);
            json.put("uuid", this.uuid);

            // encode flags
            int ret = 0;
            for (RoutineEditViewModel.FLAGS val : flags) {
                ret |= 1 << val.ordinal();
            }

            json.putOpt("flags", ret);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;

    }

    public static RoutineObject FromJSON(JSONObject json){
        RoutineObject rob = new RoutineObject();

        try {
            rob.title = (String) json.getString("title");
            rob.icon = (int) json.getInt("icon");
            rob.color = (int) json.getInt("color");
            rob.backgroundColor = (int) json.getInt("backgroundColor");
            rob.progress = (float) json.getDouble("progress");
            rob.progressMax = (float) json.getDouble("max");
            rob.uuid = json.getString("uuid");

            int code = json.getInt("flags");

            RoutineEditViewModel.FLAGS[] values = (RoutineEditViewModel.FLAGS[]) RoutineEditViewModel.FLAGS.class.getMethod("values").invoke(null);
            EnumSet<RoutineEditViewModel.FLAGS> result = EnumSet.noneOf(RoutineEditViewModel.FLAGS.class);
            while (code != 0) {
                int ordinal = Integer.numberOfTrailingZeros(code);
                code ^= Integer.lowestOneBit(code);
                result.add(values[ordinal]);
            }

            rob.flags = result;

        } catch (JSONException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return rob;
    }
}

package com.team9.daymate.viewModels;



import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.team9.daymate.core.AppDataLogic;
import com.team9.daymate.core.Counter;
import com.team9.daymate.core.RoutineObject;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;


/**
 * Muokkaa yhden rutiinin tavoitetta, toistoa ja aikaväliä.
 *
 * @author Alexander L
 */
public class RoutineEditViewModel extends ViewModel {

    public static enum FLAGS implements Serializable {
        NONE,
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY,             // androidissa sunnuntai = 1
        MORNING, DAY, EVENING, ANYTIME,
        FINISHED;

        public static final EnumSet<FLAGS> DAILY = EnumSet.of(MORNING, DAY, EVENING, ANYTIME);
        public static final EnumSet<FLAGS> DAYS = EnumSet.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY);

    }


    // Käyttöliittymän klikkilaskuri
    private Counter counter;

    // Rutiini jonka asetuksia muokataan
    private RoutineObject sharedData;

    // Rutiinin binääriset lipukkeet joka sisältää toistojen määrän ja aikavälin
    private EnumSet<FLAGS> flags;

    // luodaan uusi rutiini kun null, muokataan olemassa oleva muuten
    private boolean num;


    public void populateFlags(){

        flags = EnumSet.of(FLAGS.NONE);
    }

    public void setCounterView(TextView tv){

        counter = new Counter(tv);
        counter.set_count((int)sharedData.getProgressMax());
        renderCounter();
    }

    public void increment(){
        counter.increment();
    }

    public void decrement(){
        counter.decrement();
    }

    public void renderCounter(){
        counter.updateLayoutCounter();
    }

    public void setSharedData(Object data){
        sharedData = (RoutineObject)data;
        flags = sharedData.getFlags();
        num = false;
    }

    public void setEditData(Object data){
        sharedData = (RoutineObject)data;
        flags = sharedData.getFlags();
        num = true;

    }

    public void setFlag(FLAGS day){
        flags.add(day);
    }

    public void unsetFlag(FLAGS day){
        flags.remove(day);
    }

    public void unsetFlag(EnumSet<FLAGS> day){
        flags.removeAll(day);
    }

    /**
     * Päivä ja aika väli valittuna?
     * @return boolean Palauttaa true kun kahden lipun ENUM, muuten false
     */
    public boolean hasFlags(){ return !Collections.disjoint(flags, FLAGS.DAYS) && !Collections.disjoint(flags, FLAGS.DAILY); }
    
    public boolean hasFlags(FLAGS flag){
        return flags.contains(flag);
    }

    /**
     * Lisää uusi rutiini JSON tiedostoon
     */
    public void applyChanges(){
        sharedData.setFlags(flags);
        sharedData.setProgressMax(counter.get_count());

        if(!num){
            // Create mode, ylhäällä on edit mode
            if(flags.contains(FLAGS.valueOf(AppDataLogic.todayToString()))){
                AppDataLogic.target++;
                AppDataLogic.routines.add(sharedData);
            }
        }

    }

    public boolean removeChanges() {
        return AppDataLogic.routines.remove(sharedData);
    }










}

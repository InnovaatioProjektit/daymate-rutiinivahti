package com.team9.daymate.routines;


import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team9.daymate.R;
import com.team9.daymate.core.AppDataLogic;
import com.team9.daymate.core.Presenter;
import com.team9.daymate.core.UIView;
import com.team9.daymate.elements.RadioGroupView;
import com.team9.daymate.viewModels.RoutineEditViewModel;
import com.team9.daymate.viewModels.RoutineViewModel;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Muokkaa rutiiniObjektia {@see RoutineObject}
 *
 * @author Alexander L
 */
public class RoutineEditFragment extends UIView {

    public RoutineEditFragment(@Nullable Bundle InstanceState) {
        super(InstanceState, R.layout.routine_edit_fragment);
    }

    public void onViewAction(View view) {
        final RadioGroupView rg = view.findViewById(R.id.radiogroup);

        final ImageButton plus = view.findViewById(R.id.counter_plus);
        final ImageButton minus = view.findViewById(R.id.counter_minus);
        final ImageButton delete = view.findViewById(R.id.tab_delete);

        final CardView btn = view.findViewById(R.id.btn_apply);


        final CheckBox[] checkboxes = { view.findViewById(R.id.checkbox_sunnuntai), view.findViewById(R.id.checkbox_maanantai), view.findViewById(R.id.checkbox_tiistai), view.findViewById(R.id.checkbox_keskiviikko),
                view.findViewById(R.id.checkbox_torstai), view.findViewById(R.id.checkbox_perjantai), view.findViewById(R.id.checkbox_lauantai)};

        getViewModel(RoutineEditViewModel.class).setCounterView(view.findViewById(R.id.counter_value));


        // Lisää asetukset
        int index = 0;
        for (RoutineEditViewModel.FLAGS flag : RoutineEditViewModel.FLAGS.DAYS) {
            checkboxes[index++].setChecked(getViewModel(RoutineEditViewModel.class).hasFlags(flag));
        }

        RadioButton cha = view.findViewById(R.id.routines_check);
        RadioButton chb = view.findViewById(R.id.morning_check);
        RadioButton chc = view.findViewById(R.id.day_check);
        RadioButton chd = view.findViewById(R.id.evening_check);

        cha.setChecked(getViewModel(RoutineEditViewModel.class).hasFlags(RoutineEditViewModel.FLAGS.ANYTIME));
        chb.setChecked(getViewModel(RoutineEditViewModel.class).hasFlags(RoutineEditViewModel.FLAGS.MORNING));
        chc.setChecked(getViewModel(RoutineEditViewModel.class).hasFlags(RoutineEditViewModel.FLAGS.DAY));
        chd.setChecked(getViewModel(RoutineEditViewModel.class).hasFlags(RoutineEditViewModel.FLAGS.EVENING));

        if(cha.isChecked()){
            rg.setCurrentradio(cha);
        }else if(chb.isChecked()){
            rg.setCurrentradio(chb);
        }else if(chc.isChecked()){
            rg.setCurrentradio(chc);
        }else if(chd.isChecked()){
            rg.setCurrentradio(chd);
        }

        toggleButton(btn);


        // Seuraa muutoksia

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getViewModel(RoutineEditViewModel.class).removeChanges();
                ((Presenter)getContext()).loadActivity(RoutineActivity.class);
            }
        });


        // Vahvista uusi rutiini
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getViewModel(RoutineEditViewModel.class).hasFlags()){
                    getViewModel(RoutineEditViewModel.class).applyChanges();
                    ((Presenter)getContext()).loadActivity(RoutineActivity.class);
                }
            }
        });

        // Klikkilaskurin plussa
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getViewModel(RoutineEditViewModel.class).increment();
                getViewModel(RoutineEditViewModel.class).renderCounter();
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getViewModel(RoutineEditViewModel.class).decrement();
                getViewModel(RoutineEditViewModel.class).renderCounter();
            }
        });

        // Kuuntele toistopäivät
        for (CheckBox cb : checkboxes) {

            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                RoutineEditViewModel.FLAGS flag;

                switch(buttonView.getId()){
                    case R.id.checkbox_maanantai: flag = RoutineEditViewModel.FLAGS.MONDAY; break;
                    case R.id.checkbox_tiistai: flag = RoutineEditViewModel.FLAGS.TUESDAY; break;
                    case R.id.checkbox_keskiviikko: flag = RoutineEditViewModel.FLAGS.WEDNESDAY; break;
                    case R.id.checkbox_torstai: flag = RoutineEditViewModel.FLAGS.THURSDAY; break;
                    case R.id.checkbox_perjantai: flag = RoutineEditViewModel.FLAGS.FRIDAY; break;
                    case R.id.checkbox_lauantai: flag = RoutineEditViewModel.FLAGS.SATURDAY; break;
                    case R.id.checkbox_sunnuntai: flag = RoutineEditViewModel.FLAGS.SUNDAY; break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + buttonView.getId());
                }

                if(isChecked){
                    getViewModel(RoutineEditViewModel.class).setFlag(flag);
                }else{
                    getViewModel(RoutineEditViewModel.class).unsetFlag(flag);
                }

                toggleButton(btn);


            });
        }

        // Kuuntele aikaväli
        rg.setOneCheckedChanged(new RadioGroupView.OnCheckedChangedListener(){

            @Override
            protected void onChangedCallback(int index) {
                RoutineEditViewModel.FLAGS flag;

                //Pyyhkäise aikavälin lipukkeet ja lisää nykyinen
                getViewModel(RoutineEditViewModel.class).unsetFlag(RoutineEditViewModel.FLAGS.DAILY);

                switch (index){
                    case 0: flag = RoutineEditViewModel.FLAGS.ANYTIME; break;
                    case 1: flag = RoutineEditViewModel.FLAGS.MORNING; break;
                    case 2: flag = RoutineEditViewModel.FLAGS.DAY; break;
                    case 3: flag = RoutineEditViewModel.FLAGS.EVENING; break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + index);
                }
                getViewModel(RoutineEditViewModel.class).setFlag(flag);
                toggleButton(btn);

            }
        });

    }

    public void toggleButton(CardView btn){

        if(getViewModel(RoutineEditViewModel.class).hasFlags()){
            btn.setCardBackgroundColor(getContext().getColor(R.color.pacific500));

        }else{
            btn.setCardBackgroundColor(getContext().getColor(R.color.gray400));
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setViewModel(RoutineEditViewModel.class);
        //getViewModel(RoutineEditViewModel.class).setSharedData(getSharedViewModel(RoutineViewModel.class).getShaderEditData());




    }
}

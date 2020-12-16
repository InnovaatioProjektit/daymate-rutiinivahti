package com.team9.daymate.elements;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * Luo RadioGroup elementin, joka hakee RadioButtoneita lapsielementeistä ja kuuntelee niitä.
 * Googlen RadioGroup ei katso lapsielementtejä kuin suoraan lapsiryhmästä. Tämän Komponentin avulla
 * RadioButton kompponenteja voi käyttää syvemmällä rootissa.
 *
 * @author Alexander L
 */
public class RadioGroupView extends LinearLayout implements
        View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private RadioButton currentradio;
    private OnCheckedChangedListener onCheckedChangedCallback;

    //RadioButtonien järjestys hashin avulla;
    private List<Integer> _hashCodedChildren = new ArrayList<>();

    public RadioGroupView(Context context) {
        super(context);
    }

    public RadioGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadioGroupView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



    @Override
    public void onClick(View view) {
        RadioButton v = (RadioButton)view;

        // vain yksi radio button
        ((Checkable)view).setChecked(v != currentradio);

        if(v != currentradio){
            if(currentradio != null){currentradio.setChecked(false);}
            onCheckedChangedCallback.onChangedCallback(  _hashCodedChildren.indexOf(view.hashCode()) );
        }
        currentradio = v;

    }

    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        setChildrenOnClickListener((ViewGroup) child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        setChildrenOnClickListener((ViewGroup) child);
    }

    private void setChildrenOnClickListener(ViewGroup tr) {
        final int c = tr.getChildCount();
        for (int i = 0; i < c; i++) {
            final View v = tr.getChildAt(i);
            if (v instanceof RadioButton) {
                v.setOnClickListener(this);
                if(!_hashCodedChildren.contains(v.hashCode())){
                    _hashCodedChildren.add(v.hashCode());
                }

            }
        }
    }

    public void setCurrentradio(RadioButton currentradio) {
        this.currentradio = currentradio;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO: maybe to clear bits in RoutineEditViewModel hmm
    }

    public void setOneCheckedChanged(@NonNull OnCheckedChangedListener callback){
        onCheckedChangedCallback = callback;
    }

    abstract public static class OnCheckedChangedListener{
        protected abstract void onChangedCallback(@IdRes int index);
    }
}

/**
 * @deprecated Ei käytössä
 */
class xRadioGroupView extends LinearLayout {

    private ArrayList<View> checkables = new ArrayList<View>();

    public xRadioGroupView(Context context) {
        super(context);
    }

    public xRadioGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public xRadioGroupView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        parseChild(child);
    }

    public void parseChild(final View child)
    {
        if(child instanceof Checkable)
        {
            checkables.add(child);
            child.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    for(int i = 0; i < checkables.size();i++)
                    {
                        Checkable view = (Checkable) checkables.get(i);
                        ((Checkable)view).setChecked(view == v);
                    }
                }
            });
        }
        else if(child instanceof ViewGroup)
        {
            parseChildren((ViewGroup)child);
        }
    }

    public void parseChildren(final ViewGroup child)
    {
        for (int i = 0; i < child.getChildCount();i++)
        {
            parseChild(child.getChildAt(i));
        }
    }
}
package com.team9.daymate.core;


import android.widget.TextView;

import androidx.core.math.MathUtils;

import org.w3c.dom.Text;

/**
 * Lisäys- ja vähennys laskuri
 * @author Alexander L
 */
public class Counter {
    private int _count;
    private int _range;
    private final TextView _counter;

    public Counter(TextView counter){
        _counter = counter;
        _range = 10;
        _count = 1;
    }
    public Counter(TextView counter, int maxRange){
        _count = 1;
        _range = maxRange;
        _counter = counter;
    }

    public void set_range(int _range) {
        this._range = _range;
    }

    public void increment(){
        _count = MathUtils.clamp(_count+1, 1, this._range);
    }

    public void decrement(){
        _count = MathUtils.clamp(_count-1, 1, this._range);
    }

    public int get_count() {
        return _count;
    }

    public void set_count(int _count) { this._count = _count; }

    public void updateLayoutCounter(){
        _counter.setText("" + _count);
    }
}

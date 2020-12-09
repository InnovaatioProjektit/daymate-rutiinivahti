package com.team9.daymate.example;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;


// voi kopioida tästä alaspäin

public class TestViewModel extends ViewModel {
    /*
    Täällä ylläpidetään UIView ja Presentor (Presentation) välisiä datasiirtoja ja yhteyksiä,
    myös UIViewin käyttöliittymän käyttämä logiikka.

    HUOM: LUODAAN PER YKSI (1) ACTIVITY JA KÄYTETÄÄN SAMA KAIKISSA ACTIVITYN ALAISISSA FRAGMENTEISSA,
    niin kuin sovittiin!
     */
    public String stringData = "hello world";


}

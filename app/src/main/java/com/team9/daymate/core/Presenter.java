package com.team9.daymate.core;

import android.app.Presentation;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * Presenter on osana MVVM hybridin sovelluksen käyttöliittymän pääluokka.
 * Luokka perii AppCompatActivity aliluokan {@see AppCompatActivity}
 *
 * @author Alexander L
 */
public class Presenter extends AppCompatActivity {
    protected ViewModel viewModel;

    /**
     * Yhdistää ViewMddel {@see ViewModel} olion käyttöliittymään palveluntuottajan {@see ViewModelProvider} avulla.
     * ViewModel ilmentyy session aikana vain kerran. Sama olemassa oleva olio palautuu.
     *
     * @param cls Geneerinen  yläluokka
     * @param <T> Geneerinen aliluoken ViewModel tyyppi
     */
    public  <T extends ViewModel> void setViewModel(Class<T> cls) {
        this.viewModel = (T)(new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())).get(cls);
    }


    /**
     * Palauttaa yhdistetyn ViewModel {@see ViewModel} olion käyttäjäliittymän {@see ViewModelProvider} avulla.
     * ViewModel ilmentyy session aikana kerran. Sama olemassa oleva olio palautuu.
     *
     * @param cls Geneerinen  yläluokka
     * @param <T> Geneerinen aliluokan VIewModel tyyppi
     * @return T tyyppinen ViewModel
     */
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T getViewModel(Class<T> cls) {
        return (T) this.viewModel;
    }



    /**
     * Alustaa uuden Fragment-tyyppisen ilmentymän ja ohjautuu sen kontekstiin
     *
     * @param view Sovelluksen käyttöliittymä
     * @param fragmentInstance Fragment tyyppinen olio
     */
    public void loadView(@IdRes int view, Fragment fragmentInstance) {
        this.getSupportFragmentManager().beginTransaction().add(view, fragmentInstance, (String)null).commit();
    }

    /**
     * Alustaa uuden Fragment-tyyppisen ilmentymän ja ohjautuu sen kontekstiin.
     *
     * @param view  Sovelluksen käyttöliittymä
     * @param fragmentClass Kappaleen {@see Fragment} Käyttöliittymän kappaleen luokka
     */
    public void loadView(@IdRes int view, Class<? extends Fragment> fragmentClass) {
        this.loadView(view, fragmentClass, (Bundle)null);
    }

    /**
     * Alustaa uuden Fragment-tyyppisen ilmentymän ja ohjautuu sen kontekstiin.
     *
     * @param view Sovelluksen käyttöliittymä
     * @param fragmentClass Kappaleen {@see Fragment} Käyttöliittymän kappaleen luokka
     * @param InstanceState Tietonippu ilmentymän tilasta
     */
    public void loadView(@IdRes int view, Class<? extends Fragment> fragmentClass, @Nullable Bundle InstanceState) {
        try {
            Constructor<? extends Fragment> fragmentInstance = fragmentClass.getDeclaredConstructor(Bundle.class);
            this.getSupportFragmentManager().beginTransaction().add(view, (Fragment)fragmentInstance.newInstance(InstanceState), fragmentClass.getSimpleName()).commit();
        } catch (IllegalAccessException var5) {
            var5.printStackTrace();
        } catch (InstantiationException var6) {
            var6.printStackTrace();
        } catch (NoSuchMethodException var7) {
            var7.printStackTrace();
        } catch (InvocationTargetException var8) {
            var8.printStackTrace();
        }
    }


    /**
     *
     * @param view @LayoutRes Sovelluksen käyttöliittymä
     * @param fragmentClass
     */
    public void replaceView(@IdRes int view, Class<? extends Fragment> fragmentClass) {
        this.replaceView(view, fragmentClass, (Bundle)null);
    }


    /**
     *  Tekee kaksi operaatiota. Ensin poistaa edellisen kappaleen {@see }
     *
     * @param view Sovelluksen käyttöliittymä
     * @param fragmentClass Kappaleen {@see Fragment} Käyttöliittymän kappaleen luokka
     * @param InstanceState Tietonippu ilmentymän tilasta
     */
    public void replaceView(@IdRes int view, Class<? extends Fragment> fragmentClass, @Nullable Bundle InstanceState) {
        try {
            Constructor<? extends Fragment> fragmentInstance = fragmentClass.getDeclaredConstructor(Bundle.class);
            this.getSupportFragmentManager().beginTransaction().replace(view, (Fragment)fragmentInstance.newInstance(InstanceState), fragmentClass.getSimpleName()).addToBackStack((String)null).commit();
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        } catch (InstantiationException exception) {
            exception.printStackTrace();
        } catch (NoSuchMethodException exception) {
            exception.printStackTrace();
        } catch (InvocationTargetException exception) {
            exception.printStackTrace();
        }
    }


    /**
     *
     *
     * @param cls
     */
    public void loadActivity(Class<? extends Presenter> cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        this.startActivity(intent);
    }

    public void loadActivity(Class<? extends Presenter> cls, Bundle extras) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtras(extras);
        this.startActivity(intent);
    }
}

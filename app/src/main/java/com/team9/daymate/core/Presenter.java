package com.team9.daymate.core;

import android.app.Presentation;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Presenter extends AppCompatActivity {
    protected ViewModel viewModel;

    public  <T extends ViewModel> void setViewModel(Class<T> cls) {
        this.viewModel = (T)(new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())).get(cls);
    }

    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T getViewModel(Class<T> cls) {
        return (T) this.viewModel;
    }

    public void loadView(int view, Fragment fragmentInstance) {
        this.getSupportFragmentManager().beginTransaction().add(view, fragmentInstance, (String)null).commit();
    }

    public void loadView(int view, Class<? extends Fragment> fragmentClass) {
        this.loadView(view, fragmentClass, (Bundle)null);
    }

    public void loadView(int view, Class<? extends Fragment> fragmentClass, @Nullable Bundle InstanceState) {
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

    public void replaceView(int view, Class<? extends Fragment> fragmentClass) {
        this.replaceView(view, fragmentClass, (Bundle)null);
    }

    public void replaceView(int view, Class<? extends Fragment> fragmentClass, @Nullable Bundle InstanceState) {
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

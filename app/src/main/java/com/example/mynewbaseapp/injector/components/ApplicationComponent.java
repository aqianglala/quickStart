package com.example.mynewbaseapp.injector.components;

import android.content.Context;

import com.example.mynewbaseapp.injector.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tzqiang on 2016/8/19.
 * Application Component
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

//    void inject(BaseActivity baseActivity);

    // provide
    Context getContext();
}

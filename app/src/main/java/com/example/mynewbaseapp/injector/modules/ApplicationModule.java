package com.example.mynewbaseapp.injector.modules;

import android.content.Context;

import com.example.mynewbaseapp.base.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tzqiang on 2016/8/19.
 * Application Module
 */
@Module
public class ApplicationModule {

    private final BaseApplication mApplication;

    public ApplicationModule(BaseApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.mApplication;
    }

}

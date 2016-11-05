package com.cwenhui.dagger.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.subjects.BehaviorSubject;


@Module
public class ApplicationModule {
    private Application mApp;
    public ApplicationModule(Application app){
        mApp = app;
    }

    @Provides
    public SharedPreferences providesPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(mApp);
    }

    @Provides
    public Resources providesResources(){
        return mApp.getResources();
    }

    @Provides
    public Context provideContext(){
        return mApp;
    }

    @Provides
    public BehaviorSubject provideBehaviorSubject(){
        return BehaviorSubject.create();
    }

    @Provides
    @Named("device_id")
    public String provideDeviceId(Context context){
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String DEVICE_ID = tm.getDeviceId();
        return DEVICE_ID;
    }

}

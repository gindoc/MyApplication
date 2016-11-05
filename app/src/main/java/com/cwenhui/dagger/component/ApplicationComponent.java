package com.cwenhui.dagger.component;

import android.content.Context;


import com.cwenhui.base.App;
import com.cwenhui.dagger.module.ActivityModule;
import com.cwenhui.dagger.module.ApplicationModule;
import com.cwenhui.dagger.module.DataManagerModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class,DataManagerModule.class})
public interface ApplicationComponent {

    void inject(App application);

    ActivityComponent plus(ActivityModule module);

    Context getContext();
}

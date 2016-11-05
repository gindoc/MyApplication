package com.cwenhui.dagger.component;


import com.cwenhui.dagger.module.ActivityModule;
import com.cwenhui.dagger.module.FragmentModule;
import com.cwenhui.module.main.MainActivity;

import dagger.Subcomponent;


@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    FragmentComponent plus(FragmentModule module);


}

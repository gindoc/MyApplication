package com.cwenhui.dagger.component;


import com.cwenhui.dagger.module.ActivityModule;
import com.cwenhui.dagger.module.FragmentModule;
import com.cwenhui.module.SideBarListView.SideBarListActivity;
import com.cwenhui.module.autoVisibilityHeader.AutoHeaderVisibilityHeaderActivity;
import com.cwenhui.module.main.MainActivity;

import dagger.Subcomponent;


@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    FragmentComponent plus(FragmentModule module);

    void inject(SideBarListActivity sideBarListActivity);

    void inject(AutoHeaderVisibilityHeaderActivity autoHeaderVisibilityHeaderActivity);
}

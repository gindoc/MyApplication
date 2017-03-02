package com.cwenhui.dagger.component;


import com.cwenhui.dagger.module.FragmentModule;
import com.cwenhui.module.bezier.fragments.BezierListFragment;

import dagger.Subcomponent;


@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(BezierListFragment bezierListFragment);

//    void inject(HomeFragment homeFragment);
}

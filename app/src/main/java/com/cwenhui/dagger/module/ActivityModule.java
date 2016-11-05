package com.cwenhui.dagger.module;

import android.support.v4.app.FragmentManager;


import com.cwenhui.base.BaseActivity;

import java.lang.ref.WeakReference;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule {
    private WeakReference<BaseActivity> mActivity;
    public ActivityModule(BaseActivity activity){
        mActivity = new WeakReference<BaseActivity>(activity);
    }

    @Provides
    public BaseActivity  providesActivity(){
        return mActivity.get();
    }

    @Provides
    public FragmentManager providesFragmentManager(){
        return mActivity.get().getSupportFragmentManager();
    }

}

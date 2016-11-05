package com.cwenhui.utils;


import com.cwenhui.dagger.component.ApplicationComponent;

/**
 * Created by xiaochuang on 5/14/16.
 */
public class ComponentHolder {
    private static ApplicationComponent sAppComponent;

    public static void setAppComponent(ApplicationComponent appComponent) {
        sAppComponent = appComponent;
    }

    public static ApplicationComponent getAppComponent() {
        return sAppComponent;
    }

}

package com.cwenhui.module.main;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by cwenhui on 2016/11/4.
 */

public class ExampleViewModel extends BaseObservable {
    private String exampleName;

    @Bindable
    public String getExampleName() {
        return exampleName;
    }

    public void setExampleName(String exampleName) {
        this.exampleName = exampleName;
    }
}

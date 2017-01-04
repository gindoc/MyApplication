package com.cwenhui.module.PercentArc;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cwenhui.base.BaseActivity;
import com.cwenhui.base.BasePresenter;
import com.cwenhui.test.R;
import com.trello.rxlifecycle.LifecycleTransformer;

import javax.annotation.Nonnull;

/**
 * Author: GIndoc on 2016/11/20 18:19
 * email : 735506583@qq.com
 * FOR   :
 */
public class PercentArcActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percent_arc);
    }

    @Override
    protected BasePresenter createPresent() {
        return null;
    }

    @Nonnull
    @Override
    public LifecycleTransformer bindUntilEvent(Object event) {
        return bindToLifecycle();
    }
}

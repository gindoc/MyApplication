package com.cwenhui.module.stickyScrollView;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cwenhui.base.BaseActivity;
import com.cwenhui.base.BasePresenter;
import com.cwenhui.test.R;
import com.cwenhui.test.databinding.ActivityStickyScrollviewBinding;
import com.trello.rxlifecycle.LifecycleTransformer;

import javax.annotation.Nonnull;

/**
 * Author: GIndoc on 2016/12/19 23:33
 * email : 735506583@qq.com
 * FOR   :
 */

public class StickyScrollViewActivity extends BaseActivity {
    private ActivityStickyScrollviewBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sticky_scrollview);
    }

    @Override
    protected BasePresenter createPresent() {
        return null;
    }

    @Nonnull
    @Override
    public LifecycleTransformer bindUntilEvent(@Nonnull Object event) {
        return null;
    }
}

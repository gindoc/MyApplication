package com.cwenhui.module.bezier;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.cwenhui.base.BaseActivity;
import com.cwenhui.base.BasePresenter;
import com.cwenhui.module.bezier.fragments.BezierListFragment;
import com.cwenhui.test.R;
import com.cwenhui.test.databinding.ActivityBezierBinding;
import com.trello.rxlifecycle.LifecycleTransformer;

import javax.annotation.Nonnull;

/**
 * 作者: GIndoc
 * 日期: 2017/3/1 17:14
 * 作用:
 */

public class BezierActivity extends BaseActivity {
    private ActivityBezierBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_bezier);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        BezierListFragment bezierListFragment = new BezierListFragment();
//        transaction.add(R.id.container, bezierListFragment);
//        transaction.commit();
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

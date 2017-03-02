package com.cwenhui.module.bezier.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cwenhui.base.BaseFragment;
import com.cwenhui.base.BasePresenter;
import com.cwenhui.test.R;
import com.cwenhui.test.databinding.FragmentBezierListBinding;
import com.cwenhui.widget.dragBubbleView.DragBubbleView;
import com.trello.rxlifecycle.LifecycleTransformer;

import javax.annotation.Nonnull;

/**
 * 作者: GIndoc
 * 日期: 2017/3/1 17:38
 * 作用:
 */

public class BezierListFragment extends BaseFragment {
    private FragmentBezierListBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getComponent().inject(this);
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBezierListBinding.inflate(inflater);
        binding.setView(this);

        return binding.getRoot();
    }

    public void two() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BezierView1Fragment bezierView1Fragment = new BezierView1Fragment();
        transaction.replace(R.id.container, bezierView1Fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void three() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BezierView2Fragment bezierView2Fragment = new BezierView2Fragment();
        transaction.replace(R.id.container, bezierView2Fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void dragBubbleView() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        DragBubbleViewFragment dragBubbleViewFragment = new DragBubbleViewFragment();
        transaction.replace(R.id.container, dragBubbleViewFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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

package com.cwenhui.module.autoVisibilityHeader;

import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.cwenhui.base.BaseActivity;
import com.cwenhui.base.BasePresenter;
import com.cwenhui.test.R;
import com.cwenhui.test.databinding.ActivityAutoHeaderVisibilityHeaderBinding;
import com.cwenhui.utils.LogUtil;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * Author: GIndoc on 2016/12/20 20:49
 * email : 735506583@qq.com
 * FOR   :
 */

public class AutoHeaderVisibilityHeaderActivity extends BaseActivity implements View.OnTouchListener{
    private ActivityAutoHeaderVisibilityHeaderBinding binding;
    private int mDownY, mMoveY;
    private int mTouchSlop;
    private DERECTION derection;
    private boolean isToolBarShow = true;
    private ObjectAnimator animator;

    @Inject
    AutoHeaderVisibilityHeaderAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getComponent().inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auto_header_visibility_header);
        initRecyclerView();
        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();

        binding.toolbar.setTitle("测试");
    }

    private void initRecyclerView() {
        View header = new View(this);
        header.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)
                getResources
                ().getDimension(R.dimen.header_height)));

        List<String> datas = new ArrayList<>();
        for (int i=0;i<20; i++) {
            datas.add("String " + i);
        }
        adapter.setData(datas);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addHeader(header);
        binding.recyclerView.setOnTouchListener(this);
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = (int) event.getY();
                LogUtil.e("mDownY  : " + mDownY);
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveY = (int) event.getY();
                LogUtil.e("mMoveY  :" + mMoveY);
                if (mMoveY - mDownY > mTouchSlop) {
                    LogUtil.e("> touchSlop  -------   down");
                    derection = DERECTION.DOWN;
                } else if (mDownY - mMoveY > mTouchSlop) {
                    LogUtil.e("> touchSlop  -------   up");
                    derection = DERECTION.UP;
                }
                if (derection == DERECTION.UP) {
                    if (isToolBarShow) {
                        LogUtil.e("is showing, being to hide");
                        toolbarAnim(FLAG.HIDE);
                        isToolBarShow = !isToolBarShow;
                    }
                } else if (derection == DERECTION.DOWN) {
                    if (!isToolBarShow) {
                        LogUtil.e("is hiding, being to show");
                        toolbarAnim(FLAG.SHOW);
                        isToolBarShow = !isToolBarShow;
                    }
                }
                break;

        }
        return false;
    }

    private void toolbarAnim(FLAG hide) {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        if (hide == FLAG.SHOW) {
            animator = ObjectAnimator.ofFloat(binding.toolbar, "translationY", binding
                            .toolbar.getTranslationY(),
                    0);
            LogUtil.e("being to showing");
        }else {
            animator = ObjectAnimator.ofFloat(binding.toolbar, "translationY", binding.toolbar
                    .getTranslationY(), -binding.toolbar.getHeight());
            LogUtil.e("being to hiding");
        }
        animator.start();

    }

    enum DERECTION{
        DOWN, UP
    }
    enum FLAG{
        HIDE, SHOW
    }
}

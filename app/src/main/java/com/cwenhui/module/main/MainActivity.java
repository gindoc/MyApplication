package com.cwenhui.module.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.cwenhui.base.BaseActivity;
import com.cwenhui.base.BasePresenter;
import com.cwenhui.module.PercentArc.PercentArcActivity;
import com.cwenhui.module.SideBarListView.SideBarListActivity;
import com.cwenhui.module.autoVisibilityHeader.AutoHeaderVisibilityHeaderActivity;
import com.cwenhui.module.flowlayout.FlowLayoutActivity;
import com.cwenhui.module.fragmentTest.FragmentActivity;
import com.cwenhui.module.motionevent.MotionEventActivity;
import com.cwenhui.module.motionevent.TestActivity;
import com.cwenhui.module.stickyScrollView.StickyScrollViewActivity;
import com.cwenhui.test.R;
import com.cwenhui.test.databinding.ActivityMainBinding;
import com.cwenhui.widget.DividerItemDecoration;
import com.cwenhui.widget.recyclerview.BaseRecyclerViewAdapter;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.util.Arrays;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * Created by cwenhui on 2016/11/4.
 */
public class MainActivity extends BaseActivity implements BaseRecyclerViewAdapter.OnItemClickListener{
    private ActivityMainBinding mBinding;

    @Inject
    ExampleAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getComponent().inject(this);
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mAdapter.setData(Arrays.asList(getResources().getStringArray(R.array.activities)));
        mBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }


    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "pos:" + position, Toast.LENGTH_SHORT).show();
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, FlowLayoutActivity.class);
                break;
            case 1:
                intent = new Intent(this, FragmentActivity.class);
                break;
            case 2:
                intent = new Intent(this, MotionEventActivity.class);
                break;
            case 3:
                intent = new Intent(this, TestActivity.class);
                break;
            case 4:
                intent = new Intent(this, SideBarListActivity.class);
                break;
            case 5:
                intent = new Intent(this, PercentArcActivity.class);
                break;
            case 6:
                intent = new Intent(this, StickyScrollViewActivity.class);
                break;
            case 7:
                intent = new Intent(this, AutoHeaderVisibilityHeaderActivity.class);
                break;
        }
        startActivity(intent);

    }

    @Override
    protected BasePresenter createPresent() {
        return null;
    }

    @Nonnull
    @Override
    public LifecycleTransformer bindUntilEvent(@Nonnull Object event) {
        return bindToLifecycle();
    }
}

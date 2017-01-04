package com.cwenhui.module.stickyScrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Author: GIndoc on 2016/12/19 21:53
 * email : 735506583@qq.com
 * FOR   :
 */

public class StickyScrollView extends ViewGroup {
    private Scroller mScroller;
    private int mScreenHeight;
    /**
     * 手机按下时的屏幕坐标
     */
    private float mYDown;

    /**
     * 手机当时所处的屏幕坐标
     */
    private float mYMove;

    /**
     * 上次触发ACTION_MOVE事件时的屏幕坐标
     */
    private float mYLastMove;

    /**
     * 界面可滚动的上边界
     */
    private int topBorder;

    /**
     * 界面可滚动的下边界
     */
    private int bottomBorder;
    private int mTouchSlop;


    public StickyScrollView(Context context) {
        this(context, null);
    }

    public StickyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenHeight = dm.heightPixels;
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        // 获取TouchSlop值
        mTouchSlop = configuration.getScaledPagingTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i=0; i<count; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
//        LayoutParams layoutParams = getLayoutParams();
//        layoutParams.height = mScreenHeight * childCount;
//        setLayoutParams(layoutParams);
        for (int i=0; i<childCount;i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                child.layout(0, i * child.getMeasuredHeight(), child.getMeasuredWidth(), (i + 1) * child.getMeasuredHeight());
            }
        }
        topBorder = getChildAt(0).getTop();
        bottomBorder = getChildAt(childCount - 1).getBottom();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mYDown = ev.getRawY();
                mYLastMove = mYDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mYMove = ev.getRawY();
                float diff = Math.abs(mYMove - mYDown);
                mYLastMove = mYMove;
                // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
//                if (!mScroller.isFinished()) {
//                    mScroller.abortAnimation();
//                }
                mYMove = event.getRawY();
                int scrolledY = (int) (mYLastMove - mYMove);
                if (getScrollY() + scrolledY < topBorder) {
                    scrollTo(0, topBorder);
                    return true;
                } else if (getScrollY() +getHeight() + scrolledY > bottomBorder) {
                    scrollTo(0, bottomBorder - getHeight());
                    return true;
                }
                scrollBy(0, scrolledY);
                mYLastMove = mYMove;
                break;
            case MotionEvent.ACTION_UP:
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                int targetIndex = (getScrollY() + getHeight() / 2) / getHeight();
                int dy = targetIndex * getHeight() - getScrollY();
                // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                mScroller.startScroll(0, getScrollY(), 0, dy);
                invalidate();
                break;

        }
        postInvalidate();
        return true;
    }

    /**
     * 系统在绘制View的时候会在draw()方法中调用该方法，这个方法实际上是使用scrollTo方法，通过不断的瞬间移动一个小的
     * 距离来实现整体上的平滑移动效果。
     * 但computeScroll()方法是不会自动调用的，只能通过invalidate()->draw()->computeScroll()来间接
     * 调用computeScroll()，所以要在该方法中调用invalidate()或postInvalidate()，实现循环获取scrollX/Y的目的
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());       // 获取当前的滑动坐标
            postInvalidate();
        }
    }
}

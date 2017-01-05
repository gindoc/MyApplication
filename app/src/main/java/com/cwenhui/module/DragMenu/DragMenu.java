package com.cwenhui.module.DragMenu;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Author: GIndoc on 2017/1/5 20:52
 * email : 735506583@qq.com
 * FOR   :
 */

public class DragMenu extends FrameLayout {
    private ViewDragHelper viewDragHelper;
    private View menuView, mainView;
    private int mWidth;

    public DragMenu(Context context) {
        this(context, null);
    }

    public DragMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        viewDragHelper = ViewDragHelper.create(this, callback);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        //何时开始检测触摸事件
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //如果当前触摸的child是mainView时开始检测
            return child == mainView;
        }

        /**
         * 处理水平滑动
         * @param child
         * @param left      水平方向上child移动的距离
         * @param dx        表示比较前一次的增量
         * @return          通常情况下返回left就行，当需要更加精确的计算padding等属性时，需要对left进行处理，并
         *                  返回合适大小的值
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            final int newLeft = Math.min(Math.max(left, 0), 500);
            return newLeft;
        }

        //处理垂直滑动
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);     //默认返回0
        }

        //拖动结束后调用，相当于监听ACTION_UP事件，这个方法内部是通过scroller类来实现，这是重
        // 写computeScroll()方法的原因
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            // 手指抬起后缓慢移动到指定位置
            if (mainView.getLeft() < 500) {
                //关闭菜单
                //相当于scroller的startScroll方法
                viewDragHelper.smoothSlideViewTo(mainView, 0, 0);
                ViewCompat.postInvalidateOnAnimation(DragMenu.this);
            }else {
                //打开菜单
                viewDragHelper.smoothSlideViewTo(mainView, 300, 0);
                ViewCompat.postInvalidateOnAnimation(DragMenu.this);
            }
        }
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menuView = getChildAt(0);
        mainView = getChildAt(1);
    }

    //需要重新事件拦截方法，将事件传递给viewDragHelper进行处理
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将触摸事件传递给viewDragHelper，此操作必不可少
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //viewDragHelper内部也是通过scroller来实现平滑移动的
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}

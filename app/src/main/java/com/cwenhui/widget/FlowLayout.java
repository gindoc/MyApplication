package com.cwenhui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by cwenhui on 2016.02.23
 */
public class FlowLayout extends LinearLayout {
    private Context mContext;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return (LayoutParams) new MarginLayoutParams(mContext, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int count = getChildCount();
        int lineWidth = 0;  //当前行的宽度
        int lineHeight = 0; //当前行的高度
        int realWidth = 0;  //在wrapContent条件下的宽度
        int realHeight = 0; //在wrapContent条件下的高度

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = lp.leftMargin + child.getMeasuredWidth() + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (lineWidth + childWidth > sizeWidth) {
                realWidth = Math.max(lineWidth, childWidth);
                lineWidth = 0;
                realHeight += lineHeight;
                lineHeight = 0;
            }
            lineWidth = lineWidth + childWidth;
            lineHeight = Math.max(lineHeight, childHeight);
        }
        realWidth = Math.max(realWidth, lineWidth);
        realHeight += lineHeight;

        realWidth = modeWidth == MeasureSpec.EXACTLY ? sizeWidth : realWidth;
        realHeight = modeHeight == MeasureSpec.EXACTLY ? sizeHeight : realHeight;
        setMeasuredDimension(realWidth, realHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);

    }
}

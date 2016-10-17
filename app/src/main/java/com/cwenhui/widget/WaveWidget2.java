package com.cwenhui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cwenhui on 2016.02.23
 */
public class WaveWidget2 extends View {
    private Bitmap mSrcBitmap, mDestBitmap;
    private Paint mSrcPaint, mDestPaint;
    private Rect mBitmapSrcRect, mBitmapDestRect;
    private Rect mPicSrcRect, mPicDestRect;
    private int currentPos;
    private int totalWidth, totalHeight;

    public WaveWidget2(Context context) {
        this(context, null);
    }

    public WaveWidget2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveWidget2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSrcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSrcPaint.setFilterBitmap(true);
        mSrcPaint.setDither(true);
        mDestPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDestPaint.setDither(true);
        mDestPaint.setFilterBitmap(true);


    }
}

package com.cwenhui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.cwenhui.test.R;

/**
 * Created by cwenhui on 2016.02.23
 */
public class PoterDuffLoadingView extends View {
    private Context mContext;
    private int bitWidth, bitHeight;
    private Rect mSrcRect, mDestRect;
    private Rect mDynamicRect;
    private Paint mPaint;
    private int totalWidth, totalHeight;
    private int mCurrentTop;
    private Bitmap mBitmap;
    private int limit;

    public PoterDuffLoadingView(Context context) {
        this(context, null);
    }

    public PoterDuffLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PoterDuffLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setFilterBitmap(true);
        mPaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        bitWidth = mBitmap.getWidth();
        bitHeight = mBitmap.getHeight();
        limit = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources()
                .getDisplayMetrics());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        totalWidth = w;
        totalHeight = h;

        mSrcRect = new Rect(0, 0, bitWidth, bitHeight);
        mDestRect = new Rect(limit, limit, limit + bitWidth, limit + bitHeight);
        mCurrentTop = limit + bitHeight;
        mDynamicRect = new Rect(limit, limit+bitHeight, limit + bitWidth, limit + bitHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saveLayerCount = canvas.saveLayer(0, 0, totalWidth, totalHeight, mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mBitmap, mSrcRect, mDestRect, mPaint);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.test));
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(mDynamicRect, mPaint);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        mPaint.setXfermode(null);
        canvas.restoreToCount(saveLayerCount);
        mCurrentTop -= 8;
        if (mCurrentTop <= limit) {
            mCurrentTop = limit + bitHeight;
        }
        mDynamicRect.top = mCurrentTop;
        postInvalidate();
    }
}

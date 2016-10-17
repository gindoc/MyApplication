package com.cwenhui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by cwenhui on 2016.02.23
 */
public class FlashTextView extends TextView {
    private Paint mPaint1;
    private Paint mPaint2;
    private Context mContext;
    private Paint mPaint;
    private int mWidth;
    private LinearGradient mLinearGradient;
    private Matrix mMatrix;
    private int mTranslate;

    public FlashTextView(Context context) {
        super(context);
        mContext = context;
        initPaint();
    }

    public FlashTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }

    public FlashTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    private void initPaint() {
        mPaint1 = new Paint();
        mPaint2 = new Paint();
        mPaint1.setColor(ContextCompat.getColor(mContext, android.R.color.holo_red_dark));
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint2.setColor(ContextCompat.getColor(mContext, android.R.color.darker_gray));
        mPaint2.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint1);
        canvas.drawRect(10, 10, getMeasuredWidth() - 10, getMeasuredHeight() - 10, mPaint2);
        canvas.save();
        canvas.translate(10, 0);
        super.onDraw(canvas);
        canvas.restore();

        if (mLinearGradient != null) {
            Log.e("mWidth", "mWidth:   " + mWidth);
            mTranslate += mWidth / 5;
            Log.e("mTranslate:", "mTranslate:" + mTranslate);
            if (mTranslate > 2 * mWidth) {
                mTranslate = -mWidth;
            }
            mMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mMatrix);
            postInvalidateDelayed(100);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mWidth == 0) {
            mWidth = getMeasuredWidth();
            if (mWidth > 0) {
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(0, 0, mWidth, 0,
                        new int[]{Color.BLACK, 0xffffffff, Color.BLACK},
                        null, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mMatrix = new Matrix();

            }
        }
    }
}

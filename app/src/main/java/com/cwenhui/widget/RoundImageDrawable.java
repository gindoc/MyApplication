package com.cwenhui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

/**
 * Created by cwenhui on 2016.02.23
 */
public class RoundImageDrawable extends Drawable {
    private Bitmap mBitmap;
    private float borderRadius;
    private RectF mRectF;
    private Paint mPaint;
    private float viewWidth;
    private float viewHeight;

    /**
     * 如果不设置角度，则默认角度是图片的半径，即为圆
     * @param context
     * @param mBitmap
     */
    public RoundImageDrawable(Context context, Bitmap mBitmap) {
        this(context, mBitmap, mBitmap.getWidth()/2.0f);
    }

    /**
     * @param context
     * @param mBitmap
     * @param borderRadius 四个角的半径
     */
    public RoundImageDrawable(Context context, Bitmap mBitmap, float borderRadius) {
        this.mBitmap = mBitmap;
        this.borderRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, borderRadius,
                context.getResources().getDisplayMetrics());
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
//        BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        mPaint.setShader(shader);
    }

    /**
     * 设置控件的宽，用于缩放图片，使图片填满控件
     * @param viewWidth
     */
    public void setViewWidth(float viewWidth) {
        this.viewWidth = viewWidth;
    }

    /**
     * 设置控件的高，用于缩放图片，使图片填满控件
     * @param viewHeight
     */
    public void setViewHeight(float viewHeight) {
        this.viewHeight = viewHeight;
    }

    /**
     * 为了在View使用wrap_content的时候，提供一下尺寸
     * @return
     */
    @Override
    public int getIntrinsicWidth() {
        return mBitmap.getWidth();
    }

    /**
     * 为了在View使用wrap_content的时候，提供一下尺寸
     * @return
     */
    @Override
    public int getIntrinsicHeight() {
        return mBitmap.getHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        if (viewWidth != 0 && viewHeight != 0) {
            float scale = Math.max(getIntrinsicWidth() * 1.0f / mBitmap.getWidth(),
                    getIntrinsicHeight() * 1.0f / mBitmap.getHeight());
            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);
            shader.setLocalMatrix(matrix);
        }
        mPaint.setShader(shader);
        canvas.drawRoundRect(mRectF, borderRadius, borderRadius, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    /**
     * 设置下绘制的范围
     */
    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mRectF = new RectF(left, top, right, bottom);
    }
}

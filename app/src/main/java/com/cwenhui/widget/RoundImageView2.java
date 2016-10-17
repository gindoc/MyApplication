package com.cwenhui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import com.cwenhui.test.R;

/**
 * Created by cwenhui on 2016.02.23
 */
public class RoundImageView2 extends ImageView {
    private int type;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;
    private int mRadius;
    private int mWidth;
    private Paint mPaint;
    private RectF mRectF;
    private BitmapShader mBitmapShader;
    /**
     * 3x3 矩阵，主要用于缩小放大
     */
    private Matrix mMatrix;

    public RoundImageView2(Context context) {
        this(context, null);
    }

    public RoundImageView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView2);
        type = ta.getInt(R.styleable.RoundImageView2_type, TYPE_CIRCLE);
        mRadius = ta.getDimensionPixelSize(R.styleable.RoundImageView2_borderRadius, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, getResources().getDisplayMetrics()));
        Log.e("0000", "radius：    " + mRadius);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mMatrix = new Matrix();

        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 如果类型是圆形，则强制改变view的宽高一致，以小值为准
         */
        if (type == TYPE_CIRCLE) {
            mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());
            mRadius = mWidth/2;
            Log.e("ttt", "tttt" + mRadius);
            setMeasuredDimension(mWidth, mWidth);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if (getDrawable() == null)
        {
            return;
        }
        setUpShader();
//        if (type == TYPE_CIRCLE) {
//            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
//        }else {
//        Log.e("111111111", "radius：    " + mRadius);
//            canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaint);
//        }
        Log.e("111111111", "radius：    " + mRadius);
        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(mRectF, mRadius, mRadius,
                    mPaint);
        }else {
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        }
    }

    private void setUpShader() {
        Drawable drawable = getDrawable();
        if (drawable == null)
        {
            return;
        }

        Bitmap bmp = drawable2Bitmap(drawable);
        // 将bmp作为着色器，就是在指定区域内绘制bmp
        mBitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        if (type == TYPE_CIRCLE) {
            // 拿到bitmap宽或高的小值,这样scale才是较大的值（放大倍数较大才能填满view）
            int bitmapSize = Math.min(bmp.getWidth(), bmp.getHeight());
            scale = mWidth * 1.0f / bitmapSize;
        }else if (type == TYPE_ROUND) {
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；
            // 缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
            //因为要填满整个view
            scale = Math.max(getWidth() * 1.0f / bmp.getWidth(), getHeight() * 1.0f / bmp.getHeight());
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale);
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        // 设置shader
        mPaint.setShader(mBitmapShader);
    }

    private Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (type == TYPE_ROUND) {
            mRectF = new RectF(0, 0, getWidth(), getHeight());
            Log.e("22222", "22222222222");
        }
    }
}

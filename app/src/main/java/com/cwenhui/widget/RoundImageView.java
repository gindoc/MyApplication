package com.cwenhui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.cwenhui.test.R;

/**
 * Created by cwenhui on 2016.02.23
 */
public class RoundImageView extends View {
    private Bitmap mSrc;
    private int type;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;
    private int mRadius;
    private int mWidth;
    private int mHeight;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        type = ta.getInt(R.styleable.RoundImageView_type, 0);       //默认是圆形图片
        mRadius = ta.getDimensionPixelSize(R.styleable.RoundImageView_borderRadius, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, getResources().getDisplayMetrics()));
        mSrc = BitmapFactory.decodeResource(getResources(), ta.getResourceId(R.styleable
                .RoundImageView_src, R.mipmap.ic_launcher));

        ta.recycle();
    }

    public void setmSrc(int mSrc) {
        this.mSrc = BitmapFactory.decodeResource(getResources(), mSrc);
    }

    private Bitmap drawCircleImage(Bitmap source, int minWidth) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Bitmap target = Bitmap.createBitmap(minWidth, minWidth, Bitmap.Config.ARGB_8888);//?
        Canvas canvas = new Canvas(target);                                 //产生一个同样大小的画布
        canvas.drawCircle(minWidth / 2, minWidth / 2, minWidth / 2, paint); //首先绘制圆形

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));  //使用SRC_IN

        canvas.drawBitmap(source, 0, 0, paint);                             //绘制图片
        return target;
    }

    private Bitmap drawRoundImage(Bitmap source) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rect, mRadius, mRadius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 设置宽度
         */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {              // match_parent , accurate
            mWidth = specSize;
        }else{
            // 由图片决定的宽
            int desireByImg = getPaddingLeft() + getPaddingRight()
                    + mSrc.getWidth();
            if (specMode == MeasureSpec.AT_MOST){           // wrap_content
                mWidth = Math.min(desireByImg, specSize);
            }else {
                mWidth = desireByImg;
            }
        }

        /***
         * 设置高度
         */

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            mHeight = specSize;
        }else {
            int desireByImg = getPaddingTop() + getPaddingBottom() + mSrc.getHeight();
            if (specMode == MeasureSpec.AT_MOST) {
                mHeight = Math.min(desireByImg, specSize);
            }else {
                mHeight = desireByImg;
            }
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (type) {
            case TYPE_CIRCLE:
                int min = Math.min(mWidth, mHeight);
                //长度如果不一致，按小的值进行压缩,如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
                mSrc = Bitmap.createScaledBitmap(mSrc, min, min, false);
                canvas.drawBitmap(drawCircleImage(mSrc, min), 0, 0, null);
                break;

            case TYPE_ROUND:
                canvas.drawBitmap(drawRoundImage(mSrc), 0, 0, null);
                break;
        }
    }
}

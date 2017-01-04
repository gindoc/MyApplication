package com.cwenhui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.cwenhui.test.R;
import com.cwenhui.utils.DensityUtil;

/**
 * Author: GIndoc on 2016/11/20 17:37
 * email : 735506583@qq.com
 * FOR   : 百分比弧形图
 */
public class PercentArc extends View {
    private int mCircleXY;
    private int mRadius;
    private RectF mArcRectF;
    private float arcWidth;
    private String text;
    private float textSize;
    private float sweepValue;

    public void setSweepValue(float sweepValue) {
        this.sweepValue = sweepValue;
        invalidate();
    }

    public PercentArc(Context context) {
        this(context, null);
    }

    public PercentArc(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentArc(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PercentArc);
        arcWidth = ta.getDimension(R.styleable.PercentArc_arcWidth, 25);
        text = ta.getString(R.styleable.PercentArc_percentText);
        textSize = ta.getDimension(R.styleable.PercentArc_textSize, DensityUtil.sp2px(context, 14));

        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);

        mCircleXY = sizeWidth / 2;

        mRadius = sizeWidth / 4;

        mArcRectF = new RectF(sizeWidth * 0.1f, sizeWidth * 0.1f, sizeWidth * 0.9f, sizeWidth * 0.9f);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);

        paint.setColor(Color.parseColor("#cccccc"));
        paint.setStyle(Paint.Style.STROKE);
        if (arcWidth >= mCircleXY - mRadius) {
            arcWidth = mCircleXY / 4;
        }
        paint.setStrokeWidth(arcWidth);
        canvas.drawArc(mArcRectF, 270, sweepValue, false, paint);

        paint.setColor(Color.parseColor("#424242"));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mCircleXY, mCircleXY, mRadius, paint);

        if (!TextUtils.isEmpty(text)) {
            paint.setColor(Color.BLUE);
            paint.setTextSize(textSize);
            canvas.drawText(text, mCircleXY-text.length()/2f*textSize, mCircleXY+textSize/2, paint);
        }
    }
}

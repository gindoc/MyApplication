package com.cwenhui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * 作者: GIndoc
 * 日期: 2017/3/1 16:51
 * 作用:
 */

public class BezierView1 extends View {
    private float mControlX = 300;
    private float mControlY = 500;
    private float mStartX = 100, mEndX = 500;
    private float mStartY = 500, mEndY = 500;
    private Paint mLinePaint, mBezierPaint;
    private Path mBezierPath;

    public BezierView1(Context context) {
        this(context, null);
    }

    public BezierView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setDither(true);
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(10);

        mBezierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBezierPaint.setDither(true);
        mBezierPaint.setStyle(Paint.Style.STROKE);
        mBezierPaint.setColor(Color.RED);
        mBezierPaint.setStrokeWidth(10);

        mBezierPath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mControlX = event.getX();
                mControlY = event.getY();
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                ValueAnimator animX = ValueAnimator.ofFloat(mControlX, 300);
                animX.setDuration(500);
                animX.setInterpolator(new OvershootInterpolator());
                animX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mControlX = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                animX.start();
                ValueAnimator animY = ValueAnimator.ofFloat(mControlY, 500);
                animY.setDuration(500);
                animY.setInterpolator(new OvershootInterpolator());
                animY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mControlY = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                animY.start();
                break;

        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mStartX, mStartY, 8, mLinePaint);
        canvas.drawText("起点", mStartX, mStartY, mLinePaint);
        canvas.drawCircle(mEndX, mEndY, 8, mLinePaint);
        canvas.drawText("终点", mEndX, mEndY, mLinePaint);
        canvas.drawCircle(mControlX, mControlY, 8, mLinePaint);
        canvas.drawText("控制点", mControlX, mControlY, mLinePaint);
        canvas.drawLine(mStartX, mStartY, mControlX, mControlY, mLinePaint);
        canvas.drawLine(mControlX, mControlY, mEndX, mEndY, mLinePaint);

        mBezierPath.reset();//因为不断重绘，path的路径也要重置，不然页面上会显示很多条线
        mBezierPath.moveTo(mStartX, mStartY);//移至起点
        mBezierPath.quadTo(mControlX, mControlY, mEndX, mEndY);//二阶贝塞尔曲线，传入控制点和终点坐标
        canvas.drawPath(mBezierPath, mBezierPaint);
    }
}

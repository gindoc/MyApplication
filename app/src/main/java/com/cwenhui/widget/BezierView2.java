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

public class BezierView2 extends View {
    private float mControlX1 = 200;
    private float mControlX2 = 400;
    private float mControlY1 = 500;
    private float mControlY2 = 500;
    private float mStartX = 100, mEndX = 500;
    private float mStartY = 500, mEndY = 500;
    private Paint mLinePaint, mBezierPaint;
    private Path mBezierPath;
    private boolean mIsSecondPoint;

    public BezierView2(Context context) {
        this(context, null);
    }

    public BezierView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView2(Context context, AttributeSet attrs, int defStyleAttr) {
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
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                mIsSecondPoint = true;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mIsSecondPoint = false;
                break;
            case MotionEvent.ACTION_MOVE:
                mControlX1 = event.getX(0);
                mControlY1 = event.getY(0);
                if (mIsSecondPoint) {
                    mControlX2 = event.getX(1);
                    mControlY2 = event.getY(1);
                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                ValueAnimator animX1 = ValueAnimator.ofFloat(mControlX1, 200);
                animX1.setDuration(500);
                animX1.setInterpolator(new OvershootInterpolator());
                animX1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mControlX1 = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                animX1.start();
                ValueAnimator animY1 = ValueAnimator.ofFloat(mControlY1, 500);
                animY1.setDuration(500);
                animY1.setInterpolator(new OvershootInterpolator());
                animY1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mControlY1 = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                animY1.start();

                ValueAnimator animX2 = ValueAnimator.ofFloat(mControlX2, 400);
                animX2.setDuration(500);
                animX2.setInterpolator(new OvershootInterpolator());
                animX2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mControlX2 = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                animX2.start();
                ValueAnimator animY2 = ValueAnimator.ofFloat(mControlY2, 500);
                animY2.setDuration(500);
                animY2.setInterpolator(new OvershootInterpolator());
                animY2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mControlY2 = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                animY2.start();
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
        canvas.drawCircle(mControlX1, mControlY1, 8, mLinePaint);
        canvas.drawText("控制点1", mControlX1, mControlY1, mLinePaint);
        canvas.drawCircle(mControlX2, mControlY2, 8, mLinePaint);
        canvas.drawText("控制点2", mControlX2, mControlY2, mLinePaint);
        canvas.drawLine(mStartX, mStartY, mControlX1, mControlY1, mLinePaint);
        canvas.drawLine(mControlX1, mControlY1, mControlX2, mControlY2, mLinePaint);
        canvas.drawLine(mControlX2, mControlY2, mEndX, mEndY, mLinePaint);

        mBezierPath.reset();//因为不断重绘，path的路径也要重置，不然页面上会显示很多条线
        mBezierPath.moveTo(mStartX, mStartY);//移至起点
        mBezierPath.cubicTo(mControlX1, mControlY1, mControlX2, mControlY2, mEndX, mEndY);//二阶贝塞尔曲线，传入控制点和终点坐标
        canvas.drawPath(mBezierPath, mBezierPaint);
    }
}

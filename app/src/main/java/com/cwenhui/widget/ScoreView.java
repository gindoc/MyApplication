package com.cwenhui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Created by cwenhui on 2016.02.23
 */
public class ScoreView extends View {
    private Paint whitePaint;
    private Paint blackPaint;
    private RectF mRectF;
    private int score;
    private int currentScore;
    private float arc = 0f;
    //以10dp作为单位量进行计算就可以很好的保证我们的自定义View在不同分辨率的手机上保持不变形
    private float unitage = 10f;

    public ScoreView(Context context, int score) {
        this(context, null);
        this.score = score;
    }

    public ScoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        blackPaint = new Paint();
        blackPaint.setAntiAlias(true); //设置抗锯齿，优化绘制效果的精细度
        blackPaint.setDither(true); //设置图像抖动处理,也是用于优化图像的显示效果
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.STROKE);
        blackPaint.setStrokeWidth(unitage * 0.2f);

        whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStyle(Paint.Style.STROKE);
        whitePaint.setStrokeWidth(unitage * 0.2f);
        whitePaint.setAntiAlias(true);
        whitePaint.setDither(true);

        whitePaint.setTextSize(unitage * 6);
        whitePaint.setTextAlign(Paint.Align.CENTER);

        //初始化圆弧所需条件（及设置圆弧的外接矩形的四边）
        mRectF = new RectF();
        mRectF.set(unitage*0.5f,unitage*0.5f,unitage*18.5f,unitage*18.5f);

        //设置整个控件的宽高配置参数
        setLayoutParams(new ViewGroup.LayoutParams((int) (unitage * 19.5f), (int) (unitage * 19.5f)));

        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                new DrawThread();
                getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //3.绘制数据

        //绘制弧形
        //黑笔画的是一个整圆所有从哪里开始都一样
        canvas.drawArc(mRectF, 0, 360, false, blackPaint);

        //白笔之所以从-90度开始，是因为0度其实使我们的3点钟的位置，所以-90才是我们的0点的位置
        canvas.drawArc(mRectF, -90, arc, false, whitePaint);
        //绘制文本, 第二个参数是文字左侧距离Y轴的距离（如果设置了文字居中，即
        // whitePaint.setTextAlign(Paint.Align.CENTER)，则是中心点距离Y轴的距离），
        //第三个参数是baseline距离X轴的距离
        canvas.drawText(currentScore + "", 9.7f * unitage, 11.7f * unitage, whitePaint);

    }

    class DrawThread implements Runnable {
        private Thread mDrawThread;
        private int state;

        public DrawThread() {
            mDrawThread = new Thread(this);
            mDrawThread.start();
        }

        @Override
        public void run() {
            while (true) {
                switch (state) {
                    case 0:
                        try {
                            Thread.sleep(200);
                            state=1;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        try {
                            Thread.sleep(20);
                            arc += 3.6f;
                            currentScore++;
                            postInvalidate();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                if (currentScore >= score) {
                    break;
                }
            }
        }
    }
}

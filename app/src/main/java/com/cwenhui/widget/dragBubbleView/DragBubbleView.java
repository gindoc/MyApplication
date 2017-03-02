package com.cwenhui.widget.dragBubbleView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.cwenhui.test.R;
import com.cwenhui.utils.DensityUtil;

/**
 * 作者: GIndoc
 * 日期: 2017/3/2 11:11
 * 作用:
 */

public class DragBubbleView extends View {
    private Paint bubblePaint;                          // 黏连小球和气泡的画笔
    private Path bezierPath;                            // 贝塞尔曲线的路径
    private Paint textPaint;                            // 气泡文字画笔
    private Rect textRect;                             // 气泡文字绘制区域
    private Paint explosionPaint;                      // 气泡爆炸图片画笔
    private RectF explosionRect;                       // 气泡爆炸图片绘制区域

    private float textSize;                             // 气泡文字大小
    private int textColor;                              // 气泡文字颜色
    private String text;                                 // 气泡文字

    private int bubbleColor;                            // 气泡颜色
    private float bubbleRadius;                         // 气泡半径
    private float circleRadius;                         // 黏连小球半径
    private float circleCenterX;                        // 黏连小球圆心
    private float circleCenterY;
    private float bubbleCenterX;                        // 气泡圆心
    private float bubbleCenterY;

    private float distanceOfCircle;                     // 黏连小球和气泡圆心之间的距离
    private float maxDistance;                          // 两圆圆心间距的最大距离，超出此值黏连小圆消失

    private float circleStartX, circleStartY;           // 黏连小球的贝塞尔曲线起点坐标
    private float bubbleEndX, bubbleEndY;               // 黏连小球的贝塞尔曲线终点坐标（在气泡上，详见博客http://blog.csdn.net/qq_31715429/article/details/54386934）
    private float bubbleStartX, bubbleStartY;           // 气泡的贝塞尔曲线起点坐标
    private float circleEndX, circleEndY;               // 气泡的贝塞尔曲线终点坐标
    private float controlX, controlY;                    // 贝塞尔曲线控制点横坐标

    private int[] explosionDrawable = {R.drawable.explosion_one, R.drawable.explosion_two,              //气泡爆炸图片id数组
            R.drawable.explosion_three, R.drawable.explosion_four, R.drawable.explosion_five,};
    private Bitmap[] explosionBitmaps;                    // 气泡爆炸的bitmap数组
    private int curExplosionIndex;                       // 气泡爆炸当前进行到第几张
    private boolean isExplosionAnimStart = false;       //气泡爆炸动画是否开始

    private int state;                                      // 气泡的状态
    private static final int STATE_DEFAULT = 0X00;          //默认，无法拖拽
    private static final int STATE_DRAG = 0X01;             // 拖拽
    private static final int STATE_MOVE = 0X02;             // 移动（超出两圆圆心间距的最大距离）
    private static final int STATE_DISMISS = 0X03;          // 消失


    public DragBubbleView(Context context) {
        this(context, null);
    }

    public DragBubbleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragBubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DragBubbleView);
        text = ta.getString(R.styleable.DragBubbleView_bubbleText);
        textColor = ta.getColor(R.styleable.DragBubbleView_bubbleTextColor, Color.WHITE);
        textSize = ta.getDimensionPixelOffset(R.styleable.DragBubbleView_bubbleTextSize, DensityUtil.dip2px(context, 12));

        bubbleRadius = ta.getDimension(R.styleable.DragBubbleView_bubbleRadius, DensityUtil.dip2px(context, 12));
        bubbleColor = ta.getColor(R.styleable.DragBubbleView_bubbleColor, Color.RED);
        circleRadius = bubbleRadius;

        state = STATE_DEFAULT;
        maxDistance = 8 * bubbleRadius;

        ta.recycle();

        bubblePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bubblePaint.setDither(true);
        bubblePaint.setColor(bubbleColor);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setDither(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textRect = new Rect();

        explosionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        explosionPaint.setDither(true);
        explosionPaint.setFilterBitmap(true);               // 进行滤波处理
        explosionRect = new RectF();
        explosionBitmaps = new Bitmap[explosionDrawable.length];
        for (int i = 0; i < explosionDrawable.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), explosionDrawable[i]);
            explosionBitmaps[i] = bitmap;
        }

        bezierPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measuredDimension(widthMeasureSpec), measuredDimension(heightMeasureSpec));
    }

    private int measuredDimension(int measureSpec) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = (int) (2 * bubbleRadius);
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initCenter(w, h);
//        state = STATE_DEFAULT;
    }

    private void initCenter(int w, int h) {
        // 设置圆心坐标
        bubbleCenterX = w / 2;
        bubbleCenterY = h / 2;
        circleCenterX = bubbleCenterX;
        circleCenterY = bubbleCenterY;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (state != STATE_DISMISS) {
                    //如果控件外面有嵌套ListView、RecyclerView等拦截焦点的控件，那就在ACTION_DOWN中请求父控件不拦截事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                    distanceOfCircle = (float) Math.hypot(event.getX() - bubbleCenterX, event.getY() - bubbleCenterY);
                    if (distanceOfCircle < bubbleRadius + maxDistance / 4) {
                        //当指尖坐标在圆内的时候，才认为是可拖拽的
                        //一般气泡比较小，增加(maxD/4)像素是为了更轻松的拖拽
                        state = STATE_DRAG;
                    } else {
                        state = STATE_DEFAULT;
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (state != STATE_DEFAULT) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    bubbleCenterX = event.getX();
                    bubbleCenterY = event.getY();
                    //计算气泡圆心与黏连小球圆心的间距
                    distanceOfCircle = (float) Math.hypot(bubbleCenterX - circleCenterX, bubbleCenterY - circleCenterY);
                    if (state == STATE_DRAG) {      // 如果可拖拽
                        //间距小于可黏连的最大距离
                        if (distanceOfCircle < maxDistance - maxDistance / 4) {//减去(maxD/4)的像素大小，是为了让黏连小球半径到一个较小值快消失时直接消失
                            circleRadius = bubbleRadius - distanceOfCircle / 8;//使黏连小球半径渐渐变小
                            if (mOnBubbleStateListener != null) {
                                mOnBubbleStateListener.onDrag();
                            }
                        } else {//间距大于于可黏连的最大距离
                            state = STATE_MOVE;//改为移动状态
                            if (mOnBubbleStateListener != null) {
                                mOnBubbleStateListener.onMove();
                            }
                        }
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                if (state == STATE_DRAG) {          // 正在拖拽时松开手指，气泡恢复原来位置并颤动一下
                    setBubbleRestoreAnim();
                } else if (state == STATE_MOVE) {   // 正在移动时松开手指
                    // 如果在移动状态下间距回到两倍半径之内，我们认为用户不想取消该气泡
                    if (distanceOfCircle < 2 * bubbleRadius) { // 那么气泡恢复原来位置并颤动一下
                        setBubbleRestoreAnim();
                    } else {                             // 气泡消失
                        setBubbleDismissAnim();
                    }
                }
                break;
        }
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画气泡
        if (state != STATE_DISMISS) {
            canvas.drawCircle(bubbleCenterX, bubbleCenterY, bubbleRadius, bubblePaint);
        }

        // 减去(maxD/4)的像素大小，是为了让黏连小球半径到一个较小值快消失时直接消失
        if (state == STATE_DRAG && distanceOfCircle < maxDistance - maxDistance / 4) {
            // 画黏连小球
            canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, bubblePaint);
            controlX = (bubbleCenterX + circleCenterX) / 2;
            controlY = (bubbleCenterY + circleCenterY) / 2;
            // 计算两条贝塞尔曲线的起点和终点（详见博客http://blog.csdn.net/qq_31715429/article/details/54386934）
            float sin = (bubbleCenterY - circleCenterY) / distanceOfCircle;
            float cos = (bubbleCenterX - circleCenterX) / distanceOfCircle;
            circleStartX = circleCenterX - circleRadius * sin;
            circleStartY = circleCenterY + circleRadius * cos;
            bubbleEndX = bubbleCenterX - bubbleRadius * sin;
            bubbleEndY = bubbleCenterY + bubbleRadius * cos;
            bubbleStartX = bubbleCenterX + bubbleRadius * sin;
            bubbleStartY = bubbleCenterY - bubbleRadius * cos;
            circleEndX = circleCenterX + circleRadius * sin;
            circleEndY = circleCenterY - circleRadius * cos;

            //画二阶贝赛尔曲线
            bezierPath.reset();
            bezierPath.moveTo(circleStartX, circleStartY);
            bezierPath.quadTo(controlX, controlY, bubbleEndX, bubbleEndY);
            bezierPath.lineTo(bubbleStartX, bubbleStartY);
            bezierPath.quadTo(controlX, controlY, circleEndX, circleEndY);
            bezierPath.close();

            canvas.drawPath(bezierPath, bubblePaint);
        }

        //画消息个数的文本
        if (state != STATE_DISMISS && !TextUtils.isEmpty(text)) {
            textPaint.getTextBounds(text, 0, text.length(), textRect);      // 测量text的大小，并返回对应大小的矩形区域
            canvas.drawText(text, bubbleCenterX - textRect.width() / 2, bubbleCenterY + textRect.height() / 2, textPaint);
        }

        if (isExplosionAnimStart && curExplosionIndex < explosionDrawable.length) {
            // 设置气泡爆炸图片的位置
            explosionRect.set(bubbleCenterX - bubbleRadius, bubbleCenterY - bubbleRadius, bubbleCenterX + bubbleRadius, bubbleCenterY + bubbleRadius);
            //根据当前进行到爆炸气泡的位置index来绘制爆炸气泡bitmap
            canvas.drawBitmap(explosionBitmaps[curExplosionIndex], null, explosionRect, explosionPaint);
        }
    }

    /**
     * 设置气泡消失动画
     */
    private void setBubbleDismissAnim() {
        state = STATE_DISMISS;                  //气泡改为消失状态
        isExplosionAnimStart = true;
        if (mOnBubbleStateListener != null) {
            mOnBubbleStateListener.onDismiss();
        }
        // 做一个int型属性动画，从0开始，到气泡爆炸图片数组个数结束
        ValueAnimator anim = ValueAnimator.ofInt(0, explosionDrawable.length);
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(500);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 拿到当前的值并重绘
                curExplosionIndex = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 动画结束后改变状态
                isExplosionAnimStart = false;
            }
        });
        anim.start();
    }

    /**
     * 气泡复原动画
     */
    private void setBubbleRestoreAnim() {
        ValueAnimator anim = ValueAnimator.ofObject(new PointFEvaluator(),
                new PointF(bubbleCenterX, bubbleCenterY),
                new PointF(circleCenterX, circleCenterY));
        anim.setDuration(500);
        // 自定义Interpolator差值器达到颤动效果
        anim.setInterpolator(new TimeInterpolator() {

            @Override
            public float getInterpolation(float input) {
                // http://inloop.github.io/interpolator/
                float f = 0.571429f;
                return (float) (Math.pow(2, -4 * input) * Math.sin((input - f / 4) * (2 * Math.PI) / f) + 1);
            }
        });
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF curPoint = (PointF) animation.getAnimatedValue();
                bubbleCenterX = curPoint.x;
                bubbleCenterY = curPoint.y;
                invalidate();
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 动画结束后状态改为默认
                state = STATE_DEFAULT;
                if (mOnBubbleStateListener != null) {
                    mOnBubbleStateListener.onRestore();
                }
            }
        });
        anim.start();
    }

    /**
     * 气泡状态的监听器
     */
    public interface OnBubbleStateListener {
        /**
         * 拖拽气泡
         */
        void onDrag();

        /**
         * 移动气泡
         */
        void onMove();

        /**
         * 气泡恢复原来位置
         */
        void onRestore();

        /**
         * 气泡消失
         */
        void onDismiss();
    }

    private OnBubbleStateListener mOnBubbleStateListener;       // 气泡状态的监听

    /**
     * 设置气泡状态的监听器
     */
    public void setOnBubbleStateListener(OnBubbleStateListener onBubbleStateListener) {
        mOnBubbleStateListener = onBubbleStateListener;
    }

    /**
     * 设置气泡消息个数文本
     *
     * @param text 消息个数文本
     */
    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    /**
     * 重新生成气泡
     */
    public void reCreate() {
        initCenter(getWidth(), getHeight());
        invalidate();
    }
}

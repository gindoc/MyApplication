package com.cwenhui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.cwenhui.test.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by cwenhui on 2016.02.23
 */
public class LeafLoadingView extends View {
    // 淡白色
    private static final int WHITE_COLOR = 0xfffde399;
    // 橙色
    private static final int ORANGE_COLOR = 0xffffa800;
    // 中等振幅大小
    private static final int MIDDLE_AMPLITUDE = 13;
    // 不同类型之间的振幅差距
    private static final int AMPLITUDE_DISPARITY = 5;

    // 总进度
    private static final int TOTAL_PROGRESS = 100;
    // 叶子飘动一个周期所花的时间
    private static final long LEAF_FLOAT_TIME = 3000;
    // 叶子旋转一周需要的时间
    private static final long LEAF_ROTATE_TIME = 2000;

    // 用于控制绘制的进度条距离左／上／下的距离
    private static final int LEFT_MARGIN = 9;
    // 用于控制绘制的进度条距离右的距离
    private static final int RIGHT_MARGIN = 25;
    private static final String TAG = "LeafLoadingView";
    private int mLeftMargin, mRightMargin;
    // 中等振幅大小
    private int mMiddleAmplitude = MIDDLE_AMPLITUDE;
    // 振幅差
    private int mAmplitudeDisparity = AMPLITUDE_DISPARITY;

    // 叶子飘动一个周期所花的时间
    private long mLeafFloatTime = LEAF_FLOAT_TIME;
    // 叶子旋转一周需要的时间
    private long mLeafRotateTime = LEAF_ROTATE_TIME;
    private Resources mResources;

    private Bitmap leafBitmap, outerBitmap;
    private Paint whitePaint, orangePaint, bitmapPaint;
    private RectF whiteRectF, orangeRectF, arcRect;
    private Rect outerSrcRect, outerDestRect;
    private int leafWidth, leafHeight;
    private int outerWidth, outerHeight;
    private int totalWidth, totalHeight;
    private int processWidth;
    private int arcRightY;
    private int currentProcess;
    private int currentProcessPos;
    private int arcRadius;  //半径
    private int addTime;    //用于控制随机增加的时间,避免抱团出现
    private LeafFactory leafFactory;
    private List<LeafInfo> leafInfos;

    public LeafLoadingView(Context context) {
        this(context, null);
    }

    public LeafLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeafLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mResources = getResources();
        mLeftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LEFT_MARGIN, mResources
                .getDisplayMetrics());
        mRightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, RIGHT_MARGIN, mResources
                .getDisplayMetrics());
        initBitmap();
        initPaint();
        leafFactory = new LeafFactory();
        leafInfos = leafFactory.generateLeafs();
    }

    private void initPaint() {
        whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setColor(WHITE_COLOR);

        orangePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        orangePaint.setColor(ORANGE_COLOR);

        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setDither(true);
        bitmapPaint.setFilterBitmap(true);
    }

    private void initBitmap() {
        leafBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.leaf)).getBitmap();
        leafWidth = leafBitmap.getWidth();
        leafHeight = leafBitmap.getHeight();

        outerBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.leaf_kuang)).getBitmap();
        outerWidth = outerBitmap.getWidth();
        outerHeight = outerBitmap.getHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        totalWidth = w;
        totalHeight = h;
        processWidth = w - mLeftMargin - mRightMargin;
        arcRadius = (h - mLeftMargin * 2) / 2;

        outerSrcRect = new Rect(0, 0, outerWidth, outerHeight);
        outerDestRect = new Rect(0, 0, w, h);

        whiteRectF = new RectF(mLeftMargin + currentProcessPos, mLeftMargin, totalWidth - mRightMargin,
                totalHeight - mLeftMargin);
        orangeRectF = new RectF(mLeftMargin + arcRadius, mLeftMargin, currentProcessPos, totalHeight -
                mLeftMargin);
        arcRect = new RectF(mLeftMargin, mLeftMargin, mLeftMargin + 2 * arcRadius, h - mLeftMargin);
        arcRightY = mLeftMargin + arcRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制进度条和叶子
        // 之所以把叶子放在进度条里绘制，主要是层级原因
        drawProgressAndLeafs(canvas);

        canvas.drawBitmap(outerBitmap, outerSrcRect, outerDestRect, bitmapPaint);
        postInvalidate();
    }

    private void drawProgressAndLeafs(Canvas canvas) {
        if (currentProcess >= TOTAL_PROGRESS) {
            currentProcess = 0;
        }
        //currentProcessPos为进度条的宽度，根据当前进度算出进度条的位置
        currentProcessPos = processWidth * currentProcess / TOTAL_PROGRESS;
        //2种情况：
        if (currentProcessPos < arcRadius) {
            Log.e(TAG, "mProgress = " + currentProcess + "---mCurrentProgressPosition = "
                    + currentProcessPos
                    + "--mArcProgressWidth" + arcRadius);
            //1.绘制白色圆弧
            canvas.drawArc(arcRect, 90, 180, false, whitePaint);

            //2.绘制白色矩形
            whiteRectF.left = arcRightY;
            canvas.drawRect(whiteRectF, whitePaint);

            //3.绘制叶子
            drawLeaf(canvas);

            //4.绘制橙色圆弧
            double arc = Math.acos((arcRadius-currentProcessPos)/arcRadius);    //橙色圆弧的一半的弧度
            float angle = (float) Math.toDegrees(arc);                          //弧度转角度
            float startAngle = 180 - angle;
            float sweepAngle = 2 * startAngle;
            Log.i(TAG, "startAngle = " + startAngle);
            canvas.drawArc(arcRect, startAngle, sweepAngle, false, orangePaint);
        } else {
            // 这个层级进行绘制能让叶子感觉是融入棕色进度条中
            //1.绘制白色矩形
            whiteRectF.left = currentProcessPos;
            canvas.drawRect(whiteRectF, whitePaint);

            //2.绘制叶子
            drawLeaf(canvas);

            //3.绘制橙色圆弧
            canvas.drawArc(arcRect, 90, 180, false, orangePaint);

            //4.绘制橙色矩形
            orangeRectF.right = currentProcessPos;
            canvas.drawRect(orangeRectF, orangePaint);
        }
    }

    private void drawLeaf(Canvas canvas) {
        mLeafRotateTime = mLeafRotateTime <= 0 ? LEAF_ROTATE_TIME : mLeafRotateTime;
        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < leafInfos.size(); i++) {
            LeafInfo leafInfo = new LeafInfo();
            if (currentTime > leafInfo.startTime && leafInfo.startTime > 0) {
                // 绘制叶子－－根据叶子的类型和当前时间得出叶子的（x，y）
                getLeafLocation(leafInfo, currentTime);
                // 根据时间计算旋转角度
                canvas.save();
                Matrix matrix = new Matrix();
                float transX = mLeftMargin + leafInfo.x;
                float transY = mLeftMargin + leafInfo.y;
                Log.i(TAG, "left.x = " + leafInfo.x + "--leaf.y=" + leafInfo.y);
                matrix.postTranslate(transX, transY);
                // 通过时间关联旋转角度，则可以直接通过修改LEAF_ROTATE_TIME调节叶子旋转快慢
                float rotateFraction = (System.currentTimeMillis() - leafInfo.startTime) % mLeafFloatTime /
                        mLeafFloatTime;
                float angle = 360 * rotateFraction;     //待旋转角度
                // 根据叶子旋转方向确定叶子旋转角度
                float rotate = (leafInfo.rotateDirection == 0 ? leafInfo.rotateAngle + angle :
                        leafInfo.rotateAngle - angle);
                matrix.postRotate(rotate, transX + leafWidth / 2, transY + leafHeight / 2);
                canvas.drawBitmap(leafBitmap, matrix, bitmapPaint);
                canvas.restore();
            }else {
                continue;
            }
        }
    }

    private void getLeafLocation(LeafInfo leafInfo, long currentTime) {
        long intervalTime = currentTime - leafInfo.startTime;
        mLeafFloatTime = mLeafFloatTime <= 0 ? LEAF_FLOAT_TIME : mLeafFloatTime;
        if (intervalTime < 0) {
            return;
        }else if (intervalTime > mLeafFloatTime) {
            leafInfo.startTime = System.currentTimeMillis() + new Random().nextInt((int) (mLeafFloatTime));
        }
        float fraction = intervalTime / mLeafFloatTime;
        leafInfo.x = (int) (processWidth - processWidth * fraction);
        leafInfo.y = getLocationY(leafInfo);
    }

    // 通过叶子信息获取当前叶子的Y值
    private int getLocationY(LeafInfo leafInfo) {
        // y = A(wx+Q)+h
        float w = (float) (2 * Math.PI / processWidth);
        float a = MIDDLE_AMPLITUDE;
        switch (leafInfo.type) {
            case LITTLE:
                a = MIDDLE_AMPLITUDE - AMPLITUDE_DISPARITY;// 小振幅 ＝ 中等振幅 － 振幅差
                break;

            case MIDDLE:
                break;

            case BIG:
                a = MIDDLE_AMPLITUDE + AMPLITUDE_DISPARITY;// 大振幅 ＝ 中等振幅 + 振幅差
                break;
        }
        Log.i(TAG, "---a = " + a + "---w = " + w + "--leaf.x = " + leafInfo.x);
        return (int) (a*Math.sin(w*leafInfo.x)+2/3*arcRadius);
    }

    enum AmplitudeType {
        LITTLE, MIDDLE, BIG
    }

    class LeafInfo {
        int x, y;
        int rotateAngle;
        int rotateDirection;
        long startTime;
        AmplitudeType type;
    }

    class LeafFactory {
        private static final int MAX_LEAF_NUM = 8;

        public LeafInfo generateLeaf() {
            LeafInfo leafInfo = new LeafInfo();
            leafInfo.type = AmplitudeType.MIDDLE;
            Random random = new Random();
            int randomType = random.nextInt(3);
            switch (randomType) {
                case 0:
                    break;
                case 1:
                    leafInfo.type = AmplitudeType.LITTLE;
                    break;
                case 2:
                    leafInfo.type = AmplitudeType.BIG;
                    break;
            }
            leafInfo.rotateAngle = random.nextInt(360); // 随机起始的旋转角度
            leafInfo.rotateDirection = random.nextInt(2);// 随机旋转方向（顺时针或逆时针）
            mLeafFloatTime = mLeafFloatTime <= 0 ? LEAF_FLOAT_TIME : mLeafFloatTime;
            addTime += random.nextInt((int) (mLeafFloatTime * 2));
            leafInfo.startTime = System.currentTimeMillis() + addTime;
            return leafInfo;
        }

        // 根据最大叶子数产生叶子信息
        public List<LeafInfo> generateLeafs() {
            return generateLeafs(MAX_LEAF_NUM);
        }

        // 根据传入的叶子数量产生叶子信息
        public List<LeafInfo> generateLeafs(int leafSize) {
            List<LeafInfo> leafs = new LinkedList<LeafInfo>();
            for (int i = 0; i < leafSize; i++) {
                leafs.add(generateLeaf());
            }
            return leafs;
        }
    }
}

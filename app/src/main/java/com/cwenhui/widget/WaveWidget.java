package com.cwenhui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 运用公式 y = Asin(wx+b)+h w影响周期，A影响振幅，h影响y位置，b为初相； 最小正周期T = 2*PI/|w|
 * 
 * @author Administrator
 *
 */
public class WaveWidget extends View {
	private static final int WAVE_PAINT_COLOR = 0x880000aa; // 画笔的颜色
	private static final float STRETCH_FACTOR_A = 20; // 公式的A
	private static final float B = 0; // 公式的B，这里使用0
	private static final float OFFSET_Y = 0; // 公式的h
	private Paint mPaint;
	private float[] pointYPosition;
//	private float[] resetOneYPosition, resetTwoYPosition;
	private int mXOffsetSpeedOne, mXOffsetSpeedTwo;
	private int mOneXOffset, mTwoXOffset;
	private float circleViewWidthRate; // 公式的w,如果将View的宽度作为周期，则w=2*PI/viewWidth
	private int totalWidth, totalHeight;
//	private DrawFilter mDrawFilter; // 用于设置画布抗锯齿

	public WaveWidget(Context context) {
		this(context, null);
	}

	public WaveWidget(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WaveWidget(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPaint = new Paint();
		mPaint.setStyle(Style.FILL);
		mPaint.setFilterBitmap(true);
		mPaint.setColor(WAVE_PAINT_COLOR);
		mPaint.setAntiAlias(true);
		mXOffsetSpeedOne = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 10,
				getResources().getDisplayMetrics());
		mXOffsetSpeedTwo = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 15,
				getResources().getDisplayMetrics());
//		mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		totalWidth = w;
		totalHeight = h;
		pointYPosition = new float[totalWidth]; // 用于保存原始波纹的y值
//		resetOneYPosition = new float[totalWidth]; // 用于保存波纹1的y值
//		resetTwoYPosition = new float[totalWidth]; // 用于保存波纹2的y值
		circleViewWidthRate = (float) (Math.PI * 2 / totalWidth); // 将周期定为view的总宽度
		for (int i = 0; i < totalWidth; i++) {
			pointYPosition[i] = (float) (STRETCH_FACTOR_A * Math.sin(circleViewWidthRate * i + B) + OFFSET_Y);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 从canvas层面去除绘制时锯齿
//		canvas.setDrawFilter(mDrawFilter);
//		resetPositonY();
		for (int i = 0,j=0,k=0; i < totalWidth; i++) {
			// 绘制第一条水波纹
//			canvas.drawLine(i, totalHeight - resetOneYPosition[i] - 400, i, totalHeight, mPaint);
			// 绘制第二条水波纹
//			canvas.drawLine(i, totalHeight - resetTwoYPosition[i] - 400, i, totalHeight, mPaint);
			if (mOneXOffset + i < totalWidth) {
				canvas.drawLine(i, totalHeight - pointYPosition[mOneXOffset + i] - 400, i, totalHeight, mPaint);
			}
 			else {
				canvas.drawLine(i, totalHeight - pointYPosition[j] - 400, i, totalHeight, mPaint);
				j++;
			}

			if (mTwoXOffset + i < totalWidth) {
				canvas.drawLine(i, totalHeight - pointYPosition[mTwoXOffset + i] - 400, i, totalHeight, mPaint);
			}
			else {
				canvas.drawLine(i, totalHeight - pointYPosition[k] - 400, i, totalHeight, mPaint);
				k++;
			}
		}

		// 改变两条波纹的移动点
		mOneXOffset += mXOffsetSpeedOne;
		mTwoXOffset += mXOffsetSpeedTwo;
		
		// 如果已经移动到结尾处，则重头记录  
		if (mOneXOffset>=totalWidth) {
			mOneXOffset = 0;
		}
		if (mTwoXOffset>=totalWidth) {
			mTwoXOffset = 0;
		}
		postInvalidate();
	}

	/**
	 * 重置resetOneYPosition和resetTwoYPosition数组（代表的是即将显示的波浪的点的纵坐标）
	 */
//	private void resetPositonY() {
//		// mOneXOffset代表当前第一条水波纹要移动的距离
//		int oneLeft = totalWidth - mOneXOffset;
//		// 使用System.arraycopy方式重新填充第一条波纹的数据
//		System.arraycopy(pointYPosition, mOneXOffset, resetOneYPosition, 0, oneLeft);
//		System.arraycopy(pointYPosition, 0, resetOneYPosition, oneLeft, mOneXOffset);
//
//		int twoLeft = totalWidth - mTwoXOffset;
//		System.arraycopy(pointYPosition, mTwoXOffset, resetTwoYPosition, 0, twoLeft);
//		System.arraycopy(pointYPosition, 0, resetTwoYPosition, twoLeft, mTwoXOffset);
//	}
}

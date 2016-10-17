package com.cwenhui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.cwenhui.test.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SkyWidget extends View {
	private Resources resources;
	private Bitmap bg;
	private Bitmap startOne, startTwo, startThree;
	private Rect mStarOneSrcRect, mStarTwoSrcRect, mStarThreeSrcRect;
	private int oneWidth, oneHeight, twoWidth, twoHeight, threeWidth, threeHeight;
	private static float[][] START_LOCATION = {{0.5f, 0.2f},{0.68f, 0.35f}, {0.5f, 0.05f},  
            {0.15f, 0.15f}, {0.5f, 0.5f}, {0.15f, 0.8f},  
            {0.2f, 0.3f}, {0.77f, 0.4f}, {0.75f, 0.5f},  
            {0.8f, 0.55f}, {0.9f, 0.6f}, {0.1f, 0.7f},  
            {0.1f, 0.1f}, {0.7f, 0.8f}, {0.5f, 0.6f}};
	private int mFloatTransLowSpeed, mFloatTransMidSpeed, mFloatTransFastSpeed;
	private static final int LEFT=0, RIGHT=1, TOP=2, BOTTOM=3;
	private int count = 15;
	private int totalWidth, totalHeight;
	private List<StartInfo> startInfos;

	public SkyWidget(Context context) {
		this(context, null);
	}
	
	public SkyWidget(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SkyWidget(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		resources = getResources();
		mFloatTransLowSpeed = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, resources.getDisplayMetrics());
		mFloatTransMidSpeed = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.75f, resources.getDisplayMetrics());
		mFloatTransFastSpeed = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.0f, resources.getDisplayMetrics());
		startInfos = new ArrayList<StartInfo>();
		initBitmap();
	}

	private void initBitmap(){
		bg = ((BitmapDrawable)resources.getDrawable(R.drawable.xingkong)).getBitmap();
		
		startOne = ((BitmapDrawable)resources.getDrawable(R.drawable.start_one)).getBitmap();
		oneWidth = startOne.getWidth();
		oneHeight = startOne.getHeight();
		
		startTwo = ((BitmapDrawable)resources.getDrawable(R.drawable.start_two)).getBitmap();
		twoWidth = startTwo.getWidth();
		twoHeight = startTwo.getHeight();
		
		startThree = ((BitmapDrawable)resources.getDrawable(R.drawable.start_three)).getBitmap();
		threeWidth = startThree.getWidth();
		threeHeight = startThree.getHeight();
		
	}
	
	private float getRandomSize(float start, float end){
		float nextFloat = (float) Math.random();
		if (start < nextFloat && nextFloat < end) {
			return start;
		}else {
			return (float) Math.random();/*getRandomSize(start, end);*/
		}
	}
	
	private int getDirection(){
		Random random = new Random();
		int direction = random.nextInt(4);
		switch (direction) {
		case LEFT:
			direction = LEFT;
			break;

		case RIGHT:
			direction = RIGHT;
			break;
			
		case TOP:
			direction = TOP;
			break;
			
		case BOTTOM:
			direction = BOTTOM;
			break;
		default:
			break;
		}
		return direction;
	}
	
	private void initStartInfo(){
		StartInfo startInfo = null;
		Random random = new Random();
		for (int i = 0; i < count; i++) {
			float startSize = getRandomSize(0.4f, 0.9f);
			float[] startLocation = START_LOCATION[i];
			startInfo = new StartInfo();
			startInfo.scalePercent = startSize;
			// 初始化漂浮速度  
	        int randomSpeed = random.nextInt(3);  
	        switch (randomSpeed) {  
	            case 0:  
	            	startInfo.speed = mFloatTransLowSpeed;  
	                break;  
	            case 1:  
	            	startInfo.speed = mFloatTransMidSpeed;  
	                break;  
	            case 2:  
	            	startInfo.speed = mFloatTransFastSpeed;  
	                break;  
	  
	            default:  
	            	startInfo.speed = mFloatTransMidSpeed;  
	                break;  
	        }  
	        // 初始化星球透明度  
	        startInfo.alpha = getRandomSize(0.3f, 0.8f);  
	        // 初始化星球位置  
	        startInfo.xLocation = (int) (startLocation[0] * totalWidth);  
	        startInfo.yLocation = (int) (startLocation[1] * totalHeight);  
	        Log.e("eeee","xLocation = " + startInfo.xLocation + "--yLocation = "  
	                + startInfo.yLocation);  
	        Log.e("eeee","stoneSize = " + startSize + "---stoneAlpha = "  
	                + startInfo.alpha);  
	        // 初始化星球位置  
	        startInfo.direction = getDirection();
			Log.e("startInfo.direction:	", "startInfo.direction:" + startInfo.direction);
			startInfos.add(startInfo);
		}
	}

	private void drawStarDynamic(int count, StartInfo starInfo, Canvas canvas, Paint paint) {
		Log.e("count: ", "count :" + count);
		float starAlpha = starInfo.alpha;
		int xLocation = starInfo.xLocation;
		int yLocation = starInfo.yLocation;
		float sizePercent = starInfo.scalePercent;

		xLocation = (int) (xLocation / sizePercent);
		yLocation = (int) (yLocation / sizePercent);

		Log.e("xLocation: ", "xLocation:" + xLocation);
		Bitmap bitmap = null;
		Rect srcRect = null;
		Rect destRect = new Rect();

		if (count % 3 == 0) {
			bitmap = startOne;
			srcRect = mStarOneSrcRect;
			destRect.set(xLocation, yLocation,
					xLocation + oneWidth, yLocation
							+ oneHeight);
		} else if (count % 2 == 0) {
			bitmap = startThree;
			srcRect = mStarThreeSrcRect;
			destRect.set(xLocation, yLocation, xLocation
					+ threeWidth, yLocation + threeHeight);
		} else {
			bitmap = startTwo;
			srcRect = mStarTwoSrcRect;
			destRect.set(xLocation, yLocation, xLocation
					+ twoWidth, yLocation + twoHeight);
		}

		paint.setAlpha((int) (starAlpha * 255));
		canvas.save();
		canvas.scale(sizePercent, sizePercent);
		canvas.drawBitmap(bitmap, srcRect, destRect, paint);
		canvas.restore();

	}

	private void resetStarFloat(StartInfo starInfo) {
		Log.e("starInfo.direction:", starInfo.direction + "---<");
		switch (starInfo.direction) {
			case LEFT:
				starInfo.xLocation -= starInfo.speed;
				break;
			case RIGHT:
				starInfo.xLocation += starInfo.speed;
				break;
			case TOP:
				starInfo.yLocation -= starInfo.speed;
				break;
			case BOTTOM:
				starInfo.yLocation += starInfo.speed;
				break;
			default:
				break;
		}
		if (starInfo.xLocation >= totalWidth || starInfo.xLocation <= 0) {
			starInfo.xLocation = 0;
		}
		if (starInfo.yLocation >= totalHeight || starInfo.yLocation <= 0) {
			starInfo.yLocation = 0;
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		totalWidth = w;
		totalHeight = h;
		mStarOneSrcRect = new Rect(0, 0, startOne.getWidth(), startOne.getHeight());
		mStarTwoSrcRect = new Rect(0, 0, startTwo.getWidth(), startTwo.getHeight());
		mStarThreeSrcRect = new Rect(0, 0, startThree.getWidth(), startThree.getHeight());
		initStartInfo();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setFilterBitmap(true);

		for (int i = 0; i < startInfos.size(); i++) {
			drawStarDynamic(i, startInfos.get(i), canvas, paint);
			resetStarFloat(startInfos.get(i));
		}
		postInvalidate();
	}

	private class StartInfo {
		// 缩放比例
		float scalePercent;
		// x位置
		int xLocation;
		// y位置
		int yLocation;
		// 透明度
		float alpha;
		// 漂浮方向
		int direction;
		// 漂浮速度
		int speed;
	}
}

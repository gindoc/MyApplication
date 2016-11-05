package com.cwenhui.module.motionevent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.cwenhui.test.R;

/**
 * Created by cwenhui on 2016.02.23
 */
public class TestActivity extends Activity implements View.OnTouchListener{
    private ImageView image;
    private int lastX;
    private int lastY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);


        findViewById(R.id.btn_motionevent1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestActivity.this, MotionEventActivity.class));
            }
        });
        findViewById(R.id.btn_motionevent2).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(TestActivity.this, KeyEventActivity.class));
                return false;
            }
        });

        image = (ImageView) findViewById(R.id.activity_test_image);
        image.setOnTouchListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("TestActivity", "dispatchTouchEvent--"+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TestActivity", "onTouchEvent--"+event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //得到事件坐标
        int eventX = (int) event.getRawX();
        int eventY = (int) event.getRawY();
        Log.e("TestActivity", "onTouch--"+eventX);
        Log.e("TestActivity", "onTouch--"+eventY);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //第一次记录lastX、lastY
                lastX = eventX;
                lastY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算事件的偏移
                int dx = eventX - lastX;
                int dy = eventY - lastY;
                //根据事件的偏移来移动imageview
                int left = image.getLeft() + dx;
                int right = image.getRight() + dx;
                int top = image.getTop() + dy;
                int bottom = image.getBottom() + dy;
//                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//                int screenWidth = displayMetrics.widthPixels;
//                int screenHeight = displayMetrics.heightPixels;
//                DisplayMetrics dm = new DisplayMetrics();
                WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = windowManager.getDefaultDisplay();
//                display.getMetrics(dm);
                int screenWidth = display.getWidth();
                int screenHeight = display.getHeight();

                if (left < 0) {
                    right -= left;      //保持宽度不变, left为负数
                    left = 0;
                }
                if (right > screenWidth) {
                    left -= (right-screenWidth);
                    right = screenWidth;
                }
                if (top < 0) {
                    bottom -= top;
                    top = 0;
                }
                if (bottom > screenHeight) {
                    top -= (bottom-screenHeight);
                    bottom = screenHeight;
                }
                Log.e("TestActivity","left:" + left + " right:" + right + " top:" + top + " bottom:" + bottom);
                image.layout(left, top, right, bottom);

                //再次记录lastX、lastY
                lastX = eventX;
                lastY = eventY;
                break;

        }
        return true;    //所有的motionevent都交给imageview处理
    }
}

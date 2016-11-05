package com.cwenhui.module.motionevent;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.cwenhui.test.R;

/**
 * Created by cwenhui on 2016.02.23
 */
public class MotionEventActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_motionevent);

        findViewById(R.id.myImageview).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("MotionEventActivity", "setOnTouchListener--"+event.getAction());
//                return false;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("MotionEventActivity", "dispatchTouchEvent--"+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("MotionEventActivity", "onTouchEvent--"+event.getAction());
        return super.onTouchEvent(event);
    }
}

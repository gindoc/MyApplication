package com.cwenhui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cwenhui.test.R;

/**
 * Created by cwenhui on 2016.02.23
 */
public class TopBar extends RelativeLayout{
    public static final int LEFT_BUTTON = 0;
    public static final int RIGHT_BUTTON = 1;
    private Drawable mLeftBackground;
    private String mLeftText;
    private int mLeftTextColor;
    private String mTitleText;
    private float mTitleSize;
    private int mTitleTextColor;
    private Drawable mRightBackground;
    private String mRightText;
    private int mRightTextColor;

    private Button mLeft;
    private Button mRight;
    private TextView mTitle;

    public TopBar(Context context) {
        this(context, null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        mLeftBackground = ta.getDrawable(R.styleable.TopBar_leftBackground);
        mLeftText = ta.getString(R.styleable.TopBar_leftText);
        mLeftTextColor = ta.getColor(R.styleable.TopBar_leftColor, Color.BLACK);

        mRightBackground = ta.getDrawable(R.styleable.TopBar_rightBackground);
        mRightText = ta.getString(R.styleable.TopBar_rightText);
        mRightTextColor = ta.getColor(R.styleable.TopBar_rightColor, Color.BLACK);

        mTitleText = ta.getString(R.styleable.TopBar_titleText);
        mTitleSize = ta.getDimension(R.styleable.TopBar_titleSize, 14);
        mTitleTextColor = ta.getColor(R.styleable.TopBar_titleColor, Color.BLACK);

        ta.recycle();

        mLeft = new Button(context);
        mRight = new Button(context);
        mTitle = new TextView(context);

        mLeft.setText(mLeftText);
        mLeft.setBackground(mLeftBackground);
        mLeft.setTextColor(mLeftTextColor);

        mRight.setText(mRightText);
        mRight.setTextColor(mRightTextColor);
        mRight.setBackground(mRightBackground);

        mTitle.setText(mTitleText);
        mTitle.setTextColor(mTitleTextColor);
        mTitle.setTextSize(mTitleSize);
        mTitle.setGravity(Gravity.CENTER);

        LayoutParams leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        leftParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        addView(mLeft, leftParams);//将按钮添加进布局中

        LayoutParams rirhtParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rirhtParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        rirhtParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        addView(mRight, rirhtParams);//将按钮添加进布局中

        LayoutParams titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(mTitle, titleParams);//将按钮添加进布局中

        mLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLeftClick();
                }
            }
        });

        mRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRightClick();
                }
            }
        });
    }

    public interface TopBarClickListener{
        void onLeftClick();

        void onRightClick();
    }

    private TopBarClickListener listener;
    public void setOnTopBarClickListener(TopBarClickListener listener) {
        this.listener = listener;
    }

    public void setVisible(int childView, int visibility) {
        switch (childView) {
            case LEFT_BUTTON:
                mLeft.setVisibility(visibility);
                break;
            case RIGHT_BUTTON:
                mRight.setVisibility(visibility);
                break;
        }
    }
}

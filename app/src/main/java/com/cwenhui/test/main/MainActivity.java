package com.cwenhui.test.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.cwenhui.test.R;
import com.cwenhui.test.databinding.ActivityMainBinding;

/**
 * Created by cwenhui on 2016/11/4.
 */

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    }
}

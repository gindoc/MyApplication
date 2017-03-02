package com.cwenhui.module.bezier.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cwenhui.test.R;

/**
 * 作者: GIndoc
 * 日期: 2017/3/1 17:58
 * 作用:
 */

public class BezierView1Fragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bezier_view1, container, false);
        return view;
    }
}

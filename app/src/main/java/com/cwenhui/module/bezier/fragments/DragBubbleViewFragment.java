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
 * 日期: 2017/3/2 17:21
 * 作用:
 */

public class DragBubbleViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drag_bubble_view, container, false);
        return view;
    }
}

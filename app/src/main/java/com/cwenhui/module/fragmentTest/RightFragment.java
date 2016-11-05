package com.cwenhui.module.test.fragmentTest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cwenhui.test.R;

/**
 * Created by cwenhui on 2016.02.23
 */
public class RightFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("RightFragment", "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("RightFragment", "onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("RightFragment", "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("RightFragment", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("RightFragment", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("RightFragment", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("RightFragment", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("RightFragment", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("RightFragment", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("RightFragment", "onDetach");
    }
}

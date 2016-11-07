package com.cwenhui.module.fragmentTest;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cwenhui.test.R;

/**
 * Created by cwenhui on 2016.02.23
 */
public class LeftFragment extends Fragment {
//    private ScoreView mScoreView;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left, container, false);
//        mScoreView = new ScoreView(getContext(), 100);
//        ((ViewGroup)view).addView(mScoreView);

//        RoundImageView roundImageView = new RoundImageView(getContext());
//        roundImageView.setmSrc(R.mipmap.ic_launcher);
//        ((ViewGroup)view).addView(roundImageView);

//        RoundImageView2 view2 = new RoundImageView2(getContext());
//        view2.setImageResource(R.drawable.test);
//        ((ViewGroup)view).addView(view2);

//        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
//        ImageView v = new ImageView(getContext());
//        RoundImageDrawable drawable = new RoundImageDrawable(getContext(), bitmap);
//        v.setImageDrawable(drawable);
//        ((ViewGroup)view).addView(v);
//
//        final TextView w = (TextView) view.findViewById(R.id.wrap_content);
//        final TextView d = (TextView) view.findViewById(R.id.defineContent);
//
//        ViewTreeObserver vo = w.getViewTreeObserver();
//        vo.addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
//            @Override
//            public void onWindowFocusChanged(boolean hasFocus) {
//                RoundImageDrawable e = new RoundImageDrawable(getContext(), bitmap);
//                e.setViewWidth(w.getMeasuredWidth());
//                e.setViewHeight(w.getMeasuredHeight());
//                w.setBackground(e);
//
//                e.setViewWidth(d.getMeasuredWidth());
//                e.setViewHeight(d.getMeasuredHeight());
//                d.setBackground(e);
//            }
//        });

//        PoterDuffLoadingView pdl = new PoterDuffLoadingView(getContext());
//        ((ViewGroup)view).addView(pdl);

        return view;
    }


}

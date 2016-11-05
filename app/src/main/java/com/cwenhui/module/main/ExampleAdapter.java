package com.cwenhui.module.main;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cwenhui.utils.DensityUtil;
import com.cwenhui.widget.recyclerview.BaseRecyclerViewAdapter;

import javax.inject.Inject;

/**
 * Created by cwenhui on 2016/11/4.
 */

public class ExampleAdapter extends BaseRecyclerViewAdapter<String, ExampleAdapter.ExampleViewHolder> {
//    private Provider<ExampleViewModel> provider;

    @Inject
    public ExampleAdapter(Context context/*, Provider<ExampleViewModel> provider*/) {
        super(context);
//        this.provider = provider;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(mContext);
        return new ExampleViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        TextView textView = (TextView) holder.itemView;
        textView.setText(data.get(position));
        textView.setTextColor(Color.BLACK);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setHeight(DensityUtil.dip2px(mContext, 50));
        textView.setGravity(Gravity.CENTER_VERTICAL);

    }

    class ExampleViewHolder extends RecyclerView.ViewHolder {

        public ExampleViewHolder(View itemView) {
            super(itemView);
        }
    }
}

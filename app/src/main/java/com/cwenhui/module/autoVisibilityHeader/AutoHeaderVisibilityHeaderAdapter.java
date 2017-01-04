package com.cwenhui.module.autoVisibilityHeader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cwenhui.widget.recyclerview.BaseRecyclerViewAdapter;

import javax.inject.Inject;

/**
 * Author: GIndoc on 2016/12/20 21:21
 * email : 735506583@qq.com
 * FOR   :
 */

public class AutoHeaderVisibilityHeaderAdapter extends BaseRecyclerViewAdapter<String, RecyclerView.ViewHolder> {

    @Inject
    public AutoHeaderVisibilityHeaderAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        textView.setTextSize(50);
        textView.setPadding(10, 10, 10, 10);
        return new RecyclerView.ViewHolder(textView) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        TextView textView = (TextView) holder.itemView;
        textView.setText(data.get(position));
    }

}

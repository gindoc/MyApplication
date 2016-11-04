package com.cwenhui.test.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cwenhui.widget.recyclerview.BaseRecyclerViewAdapter;

import javax.inject.Provider;

/**
 * Created by cwenhui on 2016/11/4.
 */

public class ExampleAdapter extends BaseRecyclerViewAdapter<String, ExampleAdapter.ExampleViewHolder> {
//    private Provider<ExampleViewModel> provider;

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

    }

    class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ExampleViewHolder(View itemView) {
            super(itemView);
        }
    }
}

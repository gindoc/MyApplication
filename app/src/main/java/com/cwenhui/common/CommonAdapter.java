package com.cwenhui.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
	protected Context mContext;
	protected List<T> mDatas;
	protected LayoutInflater mInflater;
	private int layoutId;							//getView 中使用，指定ViewHolder 中的ConvertView
	private final String Tag = "CommonAdapter";

	public CommonAdapter(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	public void setmDatas(List<T> mDatas) {
		this.mDatas = mDatas;
	}

	public void setLayoutId(int layoutId) {
		this.layoutId = layoutId;
	}

	public CommonAdapter(Context context, List<T> datas, int layoutId){
		mContext = context;
		mDatas = datas;
		mInflater = LayoutInflater.from(context);
		this.layoutId = layoutId;
	}
	@Override
	public int getCount() {
		if(mDatas!=null){
			return mDatas.size();
		}else{
			return 0;
		}
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
		convert(holder,getItem(position));
		return holder.getConvertView();
	}
	
	public abstract void convert(ViewHolder holder,T t);

}
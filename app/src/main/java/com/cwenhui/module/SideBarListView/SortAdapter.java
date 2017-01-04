package com.cwenhui.module.SideBarListView;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cwenhui.common.CommonAdapter;
import com.cwenhui.common.ViewHolder;
import com.cwenhui.test.R;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by cwenhui on 2016/11/7.
 */

public class SortAdapter extends CommonAdapter<SideBarListModel> {

    @Inject
    public SortAdapter(Context context) {
        super(context);
    }

    public SortAdapter(Context context, List<SideBarListModel> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, SideBarListModel sideBarListModel) {
        int pos = holder.getPosition();
        TextView textView = holder.getView(R.id.catalog);
        SideBarListModel bean = mDatas.get(pos);
        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(pos);
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (pos == getPositionForSection(section)) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(bean.getSortLetters());
        } else {
            textView.setVisibility(View.GONE);
        }
        Glide.with(mContext).load(bean.getImgSrc()).into((ImageView) holder.getView(R.id.head_img));
        holder.setText(R.id.title, bean.getName());
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return mDatas.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mDatas.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }
}

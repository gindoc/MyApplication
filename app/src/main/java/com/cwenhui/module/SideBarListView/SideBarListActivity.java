package com.cwenhui.module.SideBarListView;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cwenhui.base.BaseActivity;
import com.cwenhui.base.BasePresenter;
import com.cwenhui.test.R;
import com.cwenhui.test.databinding.ActivitySidebarListBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by cwenhui on 2016/11/7.
 */
public class SideBarListActivity extends BaseActivity implements SideBar.OnTouchingLetterChangedListener {

    private ActivitySidebarListBinding mBinding;

    @Inject
    Resources mResources;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    @Inject
    PinyinComparator pinyinComparator;

    @Inject
    SortAdapter mSortAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getComponent().inject(this);
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sidebar_list);

        characterParser = CharacterParser.getInstance();

        List<SideBarListModel> sourceDateList = filledData(mResources.getStringArray(R.array.people), mResources.getStringArray(R.array.img_of_people));
        // 根据a-z进行排序源数据
        Collections.sort(sourceDateList, pinyinComparator);

        mSortAdapter.setLayoutId(R.layout.item_slidebar_list);
        mSortAdapter.setmDatas(sourceDateList);
        mBinding.lvCountry.setAdapter(mSortAdapter);

        mBinding.sidebar.setTextView(mBinding.tips);
        mBinding.sidebar.setOnTouchingLetterChangedListener(this);
    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SideBarListModel> filledData(String[] date, String[] imgData) {
        List<SideBarListModel> sortList = new ArrayList<SideBarListModel>();

        for (int i = 0; i < date.length; i++) {
            SideBarListModel sortModel = new SideBarListModel();
            sortModel.setImgSrc(imgData[i]);
            sortModel.setName(date[i]);
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            sortList.add(sortModel);
        }
        return sortList;

    }

    @Override
    protected BasePresenter createPresent() {
        return null;
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        // 该字母首次出现的位置
        int position = mSortAdapter.getPositionForSection(s.charAt(0));
        if (position != -1) {
            mBinding.lvCountry.setSelection(position);
        }
    }
}

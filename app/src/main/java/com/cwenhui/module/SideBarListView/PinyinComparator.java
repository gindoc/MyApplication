package com.cwenhui.module.SideBarListView;

import java.util.Comparator;

import javax.inject.Inject;

/**
 * 排序类
 */
public class PinyinComparator implements Comparator<SideBarListModel> {

    @Inject
    public PinyinComparator() {
    }

    public int compare(SideBarListModel o1, SideBarListModel o2) {
        if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }

}

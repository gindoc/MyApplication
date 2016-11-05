package com.cwenhui.utils;

import com.google.common.base.Verify;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by louiszgm on 2016/10/22.
 */

public class Validate {

    /**
     * 验证是否是合法手机号
     * @param phone_num
     * @return
     */
    public static boolean validatePhoneNum(String phone_num) {
        Verify.verifyNotNull(phone_num);
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}"); // 验证手机号
        m = p.matcher(phone_num);
        b = m.matches();
        return b;
    }
}

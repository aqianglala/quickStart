package com.example.mynewbaseapp.utils;

import android.content.Context;
import android.content.Intent;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zy1584 on 2017-3-30.
 */

public class MyUtils {

    /**
     * check null 相当于 TextUtils.isEmpty
     *
     * @param object
     * @return
     */
    public static boolean checkNull(Object object) {
        if (null == object) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 转化大小
     *
     * @param size
     * @return
     */
    public static String formatFileSize(long size) {
        return new DecimalFormat("##0.00").format(size * 1.0 / (1024 * 1024));
    }

    /**
     * 分享
     *
     * @param context
     * @param url
     */
    public static void shareToOther(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, url);
        intent.setType("text/plain");
        context.startActivity(Intent.createChooser(intent, "分享到..."));
    }

    /**
     * yhw
     * 手机号匹配规则
     *
     * @param mobiles
     * @return
     */
    public static boolean isTelephoneNum(String mobiles) {
        Pattern p = Pattern
                .compile("^1(3|4|5|7|8)\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * yhw
     * 邮箱匹配规则
     * 1:必须包含一个并且只有一个符号@
     * 2:第一个字符不允许是@或者.
     * 3:不允许出现@.或者.@
     * 4:结尾不得是字符@或者.
     * 5:不允许@前出现字符+
     * 6:不允许+在最前面或者 +@
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String emailStr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(emailStr);
        Matcher m = p.matcher(email);
        return m.matches();
    }

}

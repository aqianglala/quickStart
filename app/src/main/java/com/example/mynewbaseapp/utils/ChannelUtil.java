package com.example.mynewbaseapp.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Created by zy1584 on 2017-4-26.
 */

public class ChannelUtil {

    private static final String CHANNEL_KEY = "UMENG_CHANNEL";
    private static final String DEFAULT_CHANNEL = "doov";
    private static String mChannel;

    /**
     * 获取application中指定的meta-data
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getChannel(Context ctx) {
        if (!TextUtils.isEmpty(mChannel)) {
            return mChannel;
        }

        if (ctx == null) {
            return null;
        }
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        mChannel = applicationInfo.metaData.getString(CHANNEL_KEY);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(mChannel)){
            return mChannel;
        }
        return DEFAULT_CHANNEL;
    }
}

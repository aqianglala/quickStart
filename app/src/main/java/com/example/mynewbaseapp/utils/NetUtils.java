package com.example.mynewbaseapp.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 跟网络相关的工具类
 */
public class NetUtils
{
    // 适配低版本手机
    /** Network type is unknown */
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    /** Current network is GPRS */
    public static final int NETWORK_TYPE_GPRS = 1;
    /** Current network is EDGE */
    public static final int NETWORK_TYPE_EDGE = 2;
    /** Current network is UMTS */
    public static final int NETWORK_TYPE_UMTS = 3;
    /** Current network is CDMA: Either IS95A or IS95B */
    public static final int NETWORK_TYPE_CDMA = 4;
    /** Current network is EVDO revision 0 */
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    /** Current network is EVDO revision A */
    public static final int NETWORK_TYPE_EVDO_A = 6;
    /** Current network is 1xRTT */
    public static final int NETWORK_TYPE_1xRTT = 7;
    /** Current network is HSDPA */
    public static final int NETWORK_TYPE_HSDPA = 8;
    /** Current network is HSUPA */
    public static final int NETWORK_TYPE_HSUPA = 9;
    /** Current network is HSPA */
    public static final int NETWORK_TYPE_HSPA = 10;
    /** Current network is iDen */
    public static final int NETWORK_TYPE_IDEN = 11;
    /** Current network is EVDO revision B */
    public static final int NETWORK_TYPE_EVDO_B = 12;
    /** Current network is LTE */
    public static final int NETWORK_TYPE_LTE = 13;
    /** Current network is eHRPD */
    public static final int NETWORK_TYPE_EHRPD = 14;
    /** Current network is HSPA+ */
    public static final int NETWORK_TYPE_HSPAP = 15;

    /**
     * 区分信号类别，按速度划分
     */
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_WIFI = 1;
    public static final int TYPE_2G = 2;
    public static final int TYPE_3G = 3;
    public static final int TYPE_4G = 4;

    private NetUtils()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context)
    {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity)
        {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected())
            {
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity)
    {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    public static int getNetworkClass(Context context) {
        ConnectivityManager connec = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connec.getActiveNetworkInfo();

        if(info != null && !(info.isConnected())) {
            return TYPE_UNKNOWN;
        }else if ((info != null) && (info.getType() == 0)) {
            int subType = info.getSubtype();
            if(subType == NETWORK_TYPE_GPRS
                    || subType == NETWORK_TYPE_EDGE
                    || subType == NETWORK_TYPE_CDMA
                    || subType == NETWORK_TYPE_1xRTT
                    || subType == NETWORK_TYPE_IDEN) {
                return TYPE_2G;
            }else if(subType == NETWORK_TYPE_UMTS
                    || subType == NETWORK_TYPE_EHRPD
                    || subType == NETWORK_TYPE_EVDO_0
                    || subType == NETWORK_TYPE_EVDO_A
                    || subType == NETWORK_TYPE_EVDO_B
                    || subType == NETWORK_TYPE_HSDPA
                    || subType == NETWORK_TYPE_HSPA
                    || subType == NETWORK_TYPE_HSPAP
                    || subType == NETWORK_TYPE_HSUPA) {
                return TYPE_3G;
            }else if(subType == NETWORK_TYPE_LTE) {
                return TYPE_4G;
            }

        }else if ((info != null) && (info.getType() == 1)) {
            return TYPE_WIFI;
        }

        return TYPE_UNKNOWN;
    }

    public static String getNetworkClassStr(Context context){
        int networkClass = getNetworkClass(context);
        String str = "";
        switch (networkClass){
            case TYPE_WIFI:
                str = "WIFI";
                break;
            case TYPE_2G:
                str = "2G";
                break;
            case TYPE_3G:
                str = "3G";
                break;
            case TYPE_4G:
                str = "4G";
                break;
        }
        return str;
    }

}

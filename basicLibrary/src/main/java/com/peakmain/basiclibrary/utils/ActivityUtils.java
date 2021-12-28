package com.peakmain.basiclibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * author ：Peakmain
 * createTime：2020/3/9
 * mail:2726449200@qq.com
 * describe：Actvivity的工具类
 */
public class ActivityUtils {

    /**
     * 启动Actvity
     *
     * @param context 上下文
     * @param clazz   目标Activity
     */
    public static void gotoActivity(Context context, Class<? extends Activity> clazz) {
        gotoActivity(context, clazz, false, null, 0);
    }

    /**
     * 启动Actvity
     *
     * @param context 上下文
     * @param clazz   目标Activity
     * @param finish  是否关闭当前Activity
     */
    public static void gotoActivity(Context context, Class<? extends Activity> clazz, boolean finish) {
        gotoActivity(context, clazz, finish, null, 0);
    }

    /**
     * 启动Actvity
     *
     * @param context 上下文
     * @param clazz   目标Activity
     * @param pBundle 携带参数
     */
    public static void gotoActivity(Context context, Class<? extends Activity> clazz, Bundle pBundle) {
        gotoActivity(context, clazz, false, pBundle, 0);
    }

    /**
     * 启动Actvity
     *
     * @param context 上下文
     * @param clazz   目标Activity
     * @param pBundle 携带参数
     */
    public static void gotoActivity(Context context, Class<? extends Activity> clazz, Bundle pBundle, int flag) {
        gotoActivity(context, clazz, false, pBundle, flag);
    }

    /**
     * 启动Actvity
     *
     * @param context 上下文
     * @param clazz   目标Activity
     * @param finish  是否关闭当前Activity
     * @param pBundle 携带参数
     */
    public static void gotoActivity(Context context, Class<? extends Activity> clazz, boolean finish, Bundle pBundle) {
        Intent intent = new Intent(context, clazz);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }

        context.startActivity(intent);
        if (finish && context instanceof Activity) {
            ((Activity) context).finish();
        }
    }

    /**
     * 启动Actvity
     *
     * @param context 上下文
     * @param clazz   目标Activity
     * @param finish  是否关闭当前Activity
     * @param pBundle 携带参数
     */
    public static void gotoActivity(Context context, Class<? extends Activity> clazz, boolean finish, Bundle pBundle, int flag) {
        if (context != null) {
            Intent intent = new Intent(context, clazz);
            if (pBundle != null) {
                intent.putExtras(pBundle);
            }
            if (flag != 0) {
                intent.setFlags(flag);
            }
            context.startActivity(intent);
            if (finish && context instanceof Activity) {
                ((Activity) context).finish();
            }
        }

    }

    /**
     * 启动Actvity
     *
     * @param context 上下文
     * @param cls     目标Activity
     * @param bundle  携带参数
     * @param code    回调code
     */
    public static void gotoActivityResult(Context context, Class<?> cls, Bundle bundle, int code) {
        if (context != null) {
            Intent intent = new Intent();
            intent.setClass(context, cls);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            ((Activity) context).startActivityForResult(intent, code);
        }


    }
}

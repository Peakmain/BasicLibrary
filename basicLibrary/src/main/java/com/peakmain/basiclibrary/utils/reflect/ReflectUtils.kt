
package com.peakmain.basiclibrary.utils.reflect;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * author ：Peakmain
 * createTime：2022/3/1
 * mail:2726449200@qq.com
 * describe：
 */
public class ReflectUtils {

    private static final String TAG = "ReflectUtil";

    public static Object invokeStaticMethod(String clzName, String methodName, Class<?>[] methodParamTypes, Object... methodParamValues) {
        try {
            Class clz = Class.forName(clzName);
            if (clz != null) {
                Method med = clz.getDeclaredMethod(methodName, methodParamTypes);
                if (med != null) {
                    med.setAccessible(true);
                    Object retObj = med.invoke(null, methodParamValues);
                    return retObj;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "invokeStaticMethod got Exception:", e);
        }
        return null;
    }

    public static Object invokeMethod(String clzName, String methodName, Object methodReceiver, Class<?>[] methodParamTypes, Object... methodParamValues) {
        try {
            if (methodReceiver == null) {
                return null;
            }

            Class clz = Class.forName(clzName);
            if (clz != null) {
                Method med = clz.getDeclaredMethod(methodName, methodParamTypes);
                if (med != null) {
                    med.setAccessible(true);
                    Object retObj = med.invoke(methodReceiver, methodParamValues);
                    return retObj;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "invokeStaticMethod got Exception:", e);
        }
        return null;
    }

    public static final Object getStaticField(String clzName, String filedName) {
        try {
            Field field = null;
            Class<?> clz = Class.forName(clzName);
            if (clz != null) {
                field = clz.getField(filedName);
                if (field != null) {
                    return field.get("");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getStaticField got Exception:", e);
        }

        return null;
    }

    public static final Object getField(String clzName, Object obj, String filedName) {
        try {
            if (obj == null) {
                return null;
            }

            Class<?> clz = Class.forName(clzName);
            if (clz != null) {
                Field field = clz.getField(filedName);
                if (field != null) {
                    return field.get(obj);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getStaticField got Exception:", e);
        }

        return null;
    }
}

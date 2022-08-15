package com.peakmain.basiclibrary.extend

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.peakmain.basiclibrary.interfaces.OnPermissionCallback
import com.peakmain.basiclibrary.permission.RequestPermissionContract

/**
 * author ：Peakmain
 * createTime：2021/12/28
 * mail:2726449200@qq.com
 * describe：
 */
inline fun <reified T> Gson.fromJson(json: String): T {
    return fromJson(json, T::class.java)
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
val Number.dp: Float
    get() = toFloat() / Resources.getSystem().displayMetrics.density + 0.5f

val Number.px: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        toFloat(),
        Resources.getSystem().displayMetrics
    )

val Number.sp: Float
    get() = toFloat() * Resources.getSystem().displayMetrics.scaledDensity + 0.5f

fun Drawable.toBitmap(config: Bitmap.Config = Bitmap.Config.ARGB_8888): Bitmap {
    if (this is BitmapDrawable && this.bitmap != null) {
        return this.bitmap
    }
    val bitmap = if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
        Bitmap.createBitmap(1, 1, config)
    } else {
        Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, config)
    }
    val canvas = Canvas(bitmap)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)
    return bitmap
}

/**
 * number转成中文
 */
fun Int.numberToChina(): String? {
    val source = this
    val si: String = java.lang.String.valueOf(source)
    var sd: String? = ""
    when (si.length) {
        1 -> {//个
            sd += getChina(source)
            return sd!!
        }
        2 -> {//十
            sd += if (si.substring(
                    0,
                    1
                ) == "1"
            ) "十" else getChina(source / 10)
                .toString() + "十"
            sd += (source % 10).numberToChina()
        }
        3 // 百
        -> {
            sd += getChina(source / 100).toString() + "百"
            if (java.lang.String.valueOf(source % 100).length < 2) sd += "零"
            sd += (source % 100).numberToChina()
        }
        4 // 千
        -> {
            sd += getChina(source / 1000).toString() + "千"
            if (java.lang.String.valueOf(source % 1000).length < 3) sd += "零"
            sd += (source % 1000).numberToChina()
        }
        5 // 万
        -> {
            sd += getChina(source / 10000).toString() + "万"
            if (java.lang.String.valueOf(source % 10000).length < 4) sd += "零"
            sd += (source % 10000).numberToChina()
        }
    }
    return sd
}

private fun getChina(input: Int): String? {
    var sd = ""
    sd = when (input) {
        1 -> "一"
        2 -> "二"
        3 -> "三"
        4 -> "四"
        5 -> "五"
        6 -> "六"
        7 -> "七"
        8 -> "八"
        9 -> "九"
        else -> {
            "零"
        }
    }
    return sd
}

fun ActivityResultCaller.registerSingleForPermissionResult(
    fragment: Fragment,
    onPermissionCallback: OnPermissionCallback?
): ActivityResultLauncher<String> {
    return registerForActivityResult(RequestPermissionContract()) {
        val permission = it.first
        when {
            it.second -> onPermissionCallback?.onGranted(arrayOf(permission))
            permission.isNotEmpty() && fragment.shouldShowRequestPermissionRationale(permission) -> onPermissionCallback?.onDenied(
                arrayOf(permission),
                false
            )
            else -> onPermissionCallback?.onDenied(arrayOf(permission), true)
        }
    }
}

fun ActivityResultCaller.registerMultiForPermissionResult(
    fragment: Fragment,
    onPermissionCallback: OnPermissionCallback?
): ActivityResultLauncher<Array<String>> {
    return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        if (it.containsValue(false)) {
            val deniedList = mutableListOf<String>()
            for (entry in it.entries) if (!entry.value) deniedList.add(entry.key)
            val shouldPermissionList = deniedList.filter { permission ->
                fragment.shouldShowRequestPermissionRationale(permission)
            }
            if (shouldPermissionList.isNotEmpty()) {
                onPermissionCallback?.onDenied(shouldPermissionList.toTypedArray(), false)
            } else {
                onPermissionCallback?.onDenied(deniedList.toTypedArray(), true)
            }
        }else{
            onPermissionCallback?.onGranted(it.keys.toTypedArray())
        }
    }
}
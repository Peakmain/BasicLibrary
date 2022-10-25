package com.peakmain.basiclibrary.extend

import android.Manifest.permission.*
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.activity.result.ActivityResultLauncher
import com.google.gson.Gson
import com.peakmain.basiclibrary.config.ImageRequestConfig
import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.basiclibrary.constants.ImageSelectConstants
import com.peakmain.basiclibrary.permission.PkPermission

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
 * px->dp
 */
val Number.dp: Float
    get() = toFloat() / Resources.getSystem().displayMetrics.density + 0.5f
/**
 * dp->px
 */
val Number.px: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        toFloat(),
        Resources.getSystem().displayMetrics
    )

/**
 * sp->px
 */
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

private fun getChina(input: Int): String{
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

fun ActivityResultLauncher<Array<String>>.launchMulti(
    permissions: Array<String>,
    block: (() -> Unit)? = null
) {
    val permissionsSet = permissions.toMutableSet()
    if (AndroidVersion.isAndroid12()) {
        if (permissionsSet.contains(ACCESS_FINE_LOCATION) &&
            !permissionsSet.contains(ACCESS_COARSE_LOCATION) && !PkPermission.isGranted(
                ACCESS_COARSE_LOCATION
            )
        ) {
            //Android 12必须添加ACCESS_COARSE_LOCATION
            //官方适配文档：https://developer.android.google.cn/about/versions/12/approximate-location
            throw IllegalArgumentException(
                "在android 12或更高的版本中，请勿单独请求ACCESS_FINE_LOCATION权限，" +
                        "而应在单个运行时请求中同时请求ACCESS_FINE_LOCATION和ACCESS_COARSE_LOCATION权限。"
            )
        }
    }

    if (!permissionsSet.contains(ACCESS_BACKGROUND_LOCATION)) {
        launch(permissions)
        return
    }
    if (permissionsSet.contains(ACCESS_COARSE_LOCATION)
        && !permissionsSet.contains(ACCESS_FINE_LOCATION)
    ) {
        permissionsSet.add(ACCESS_FINE_LOCATION)
    }
    //后台定位权限不要和其他权限一起申请
    for (permission in permissions) {
        if (permission != ACCESS_FINE_LOCATION || permission != ACCESS_COARSE_LOCATION || permission == ACCESS_BACKGROUND_LOCATION) {
            continue
        }
        throw  IllegalArgumentException("因为有background location 权限, 请不要申请与位置无关的权限!!")
    }
    if (AndroidVersion.isAndroid10() && permissionsSet.size >= 2) {
        permissionsSet.remove(ACCESS_BACKGROUND_LOCATION)
        launchMulti(permissionsSet.toTypedArray())
        block?.invoke()
        return
    }
    launch(permissions)
}


fun ActivityResultLauncher<String?>.launchImage(config: ImageRequestConfig?) {
    when (config?.imageType) {
        ImageSelectConstants.IMAGE_TYPE -> launch("image/*")
        ImageSelectConstants.VIDEO_TYPE -> launch("video/*")
        ImageSelectConstants.ALL_TYPE -> launch("*/")
    }
}
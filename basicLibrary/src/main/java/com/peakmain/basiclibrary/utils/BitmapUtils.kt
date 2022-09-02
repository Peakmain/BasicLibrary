package com.peakmain.basiclibrary.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import kotlin.math.roundToInt

/**
 * author ：Peakmain
 * createTime：2022/09/02
 * mail:2726449200@qq.com
 * describe：
 */
object BitmapUtils {
    /**
     * Bitmap保存到sdk卡，并返回Uri
     */
    fun bitmap2Uri(bitmap: Bitmap, savePath: String): Uri? {
        val file = File(savePath)
        if (!file.exists()) {
            file.mkdir()
        }
        val img = File(file.absolutePath + TimeUtils.getCurrentTime() + ".png")
        return try {
            val fos = FileOutputStream(img)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos)
            fos.flush()
            fos.close()
            Uri.fromFile(img)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Bitmap保存到SD卡上，得到一个绝对路径
     */
    fun getBitmapPath(bitmap: Bitmap, savePath: String): String? {
        val file = File(savePath)
        if (!file.exists()) {
            file.mkdir()
        }
        val img = File(file.absolutePath + TimeUtils.getCurrentTime() + ".png")
        return try {
            val fos = FileOutputStream(img)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos)
            fos.flush()
            fos.close()
            img.canonicalPath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 计算图片的缩放值
     */
    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height: Int = options.outHeight
        val width: Int = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
            val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        return inSampleSize
    }
}
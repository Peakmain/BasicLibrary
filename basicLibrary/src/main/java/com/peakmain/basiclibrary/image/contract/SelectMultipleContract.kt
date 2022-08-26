package com.peakmain.basiclibrary.image.contract

import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper
import android.content.Intent
import android.app.Activity
import com.peakmain.basiclibrary.image.contract.SelectMultipleContract
import android.content.ClipData
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.peakmain.basiclibrary.constants.AndroidVersion
import java.util.ArrayList
import java.util.LinkedHashSet

/**
 * author ：Peakmain
 * createTime：2022/08/18
 * mail:2726449200@qq.com
 * describe：
 */
class SelectMultipleContract(val maxNum: Int) : ActivityResultContract<String, List<Uri?>>() {

    @CallSuper
    override fun createIntent(context: Context, input: String): Intent {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
            .addCategory(Intent.CATEGORY_OPENABLE)
            .setType(input)
        if (AndroidVersion.isAndroid4_3()) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        if (AndroidVersion.isAndroid13()) {
            return intent
                .putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, maxNum)
        }
        return intent

    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<Uri?> {
        return if (intent == null || resultCode != Activity.RESULT_OK) {
            emptyList<Uri>()
        } else getClipDataUris(intent)
    }

    companion object {
        fun getClipDataUris(intent: Intent): List<Uri?> {
            // Use a LinkedHashSet to maintain any ordering that may be
            // present in the ClipData
            val resultSet = LinkedHashSet<Uri?>()
            if (intent.data != null) {
                resultSet.add(intent.data)
            }
            val clipData = intent.clipData
            if (clipData == null && resultSet.isEmpty()) {
                return emptyList<Uri>()
            } else if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    val uri = clipData.getItemAt(i).uri
                    if (uri != null) {
                        resultSet.add(uri)
                    }
                }
            }
            return ArrayList(resultSet)
        }
    }
}
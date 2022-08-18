package com.peakmain.basiclibrary.image.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper

/**
 * author ：Peakmain
 * createTime：2022/08/18
 * mail:2726449200@qq.com
 * describe：
 */
internal class TakePictureContract : ActivityResultContract<Uri?, Pair<Boolean,Bitmap?>>() {

    @CallSuper
    override fun createIntent(context: Context, input: Uri?): Intent {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            .putExtra(MediaStore.EXTRA_OUTPUT, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Pair<Boolean,Bitmap?> {
        val data=intent?.getParcelableExtra<Bitmap>("data")
        return (resultCode == Activity.RESULT_OK) to data
    }
}
package com.peakmain.basiclibrary.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions.ACTION_REQUEST_PERMISSIONS
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSIONS
import com.peakmain.basiclibrary.helper.PermissionHelper

/**
 * author ：Peakmain
 * createTime：2022/08/15
 * mail:2726449200@qq.com
 * describe：
 */
class RequestPermissionContract : ActivityResultContract<String, Pair<String, Boolean>>() {
    private lateinit var mPermission: String
    override fun createIntent(context: Context, input: String): Intent {
        mPermission = input
        return Intent(ACTION_REQUEST_PERMISSIONS).putExtra(EXTRA_PERMISSIONS, arrayOf(input))
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Pair<String, Boolean> {
        if (intent == null || resultCode != Activity.RESULT_OK) return mPermission to false
        val grantResults =
            intent.getIntArrayExtra(RequestMultiplePermissions.EXTRA_PERMISSION_GRANT_RESULTS)
        return mPermission to
                if (grantResults == null || grantResults.isEmpty()) false
                else grantResults[0] == PackageManager.PERMISSION_GRANTED
    }

    override fun getSynchronousResult(
        context: Context,
        input: String?
    ): SynchronousResult<Pair<String, Boolean>>? =
        when {
            input == null -> SynchronousResult("" to false)
            PermissionHelper.instance.isGranted(input) -> SynchronousResult(input to true)
            else -> null
        }
}
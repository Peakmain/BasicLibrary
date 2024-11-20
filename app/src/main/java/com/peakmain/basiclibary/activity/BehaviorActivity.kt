package com.peakmain.basiclibary.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.peakmain.basiclibary.R
import com.peakmain.basiclibary.utils.PermissionUtils
import com.peakmain.basiclibrary.helper.BehaviorHelper
import com.peakmain.basiclibrary.interfaces.IBehaviorHelperCallback
import com.peakmain.basiclibrary.interfaces.IPermissionPopupListener
import com.peakmain.basiclibrary.interfaces.OnPermissionCallback
import com.peakmain.basiclibrary.manager.PermissionHandlerManager
import com.peakmain.basiclibrary.permission.PkPermission
import com.peakmain.basiclibrary.utils.toast.PkToastUtils
import com.peakmain.ui.utils.LogUtils
import com.peakmain.ui.widget.ShapeTextView

/**
 * author ：Peakmain
 * createTime：2022/11/14
 * mail:2726449200@qq.com
 * describe：
 */
class BehaviorActivity : AppCompatActivity() {
    val listener = object : IPermissionPopupListener {
        var utils = PkToastUtils.build(this@BehaviorActivity)
        override fun onShowPermissionPopup() {
            utils.setTitle("亚朵需要申请权限")
                .setMessage("为了您能正常使用分享功能，我们将申请启动第三方APP，您可以选择取消或者同意，取消请求不影响使用其他服务")
                .show()
        }

        override fun onHidePermissionPopup() {
            utils.dismiss()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_behavior)
        val flRoot = findViewById<FrameLayout>(R.id.fl_root)
        val llRoot = findViewById<LinearLayout>(R.id.ll_root)
        val behaviorHelper = BehaviorHelper(flRoot, llRoot, object : IBehaviorHelperCallback {
            override fun open(bottomSheet: View?, newState: Int) {
                LogUtils.e("打开了")
            }

            override fun close(bottomSheet: View?, newState: Int) {
                LogUtils.e("关闭了")
            }

        })
        PermissionHandlerManager.instance.registerListener(listener)
        findViewById<ShapeTextView>(R.id.stv_use_point).setOnClickListener {
            //behaviorHelper.toggle()

                if (PkPermission.isGranted(  Manifest.permission.CAMERA)) {
                    Log.e("TAG", "授予了权限:${  Manifest.permission.CAMERA}")
                } else {
                    if (PkPermission.shouldShowRequestPermissionRationale(this, arrayOf(  Manifest.permission.CAMERA))) {
                        //拒绝了权限，但是没有选择"Never ask again"
                        PkPermission.request(this, Manifest.permission.CAMERA, object : OnPermissionCallback {
                            override fun onGranted(permissions: Array<String>) {
                                Log.e("TAG", "授予了权限:Manifest.permission.CAMERA")
                            }

                            override fun onDenied(permissions: Array<String>, never: Boolean) {
                                Log.e("TAG", "是否永久:$never 拒绝了Manifest.permission.CAMERA权限")
                                /* fragment.context?.let {
                                             PkPermission.toAppSetting(it)
                                         }*/
                            }

                        })
                    } else {
                        //两种情况：1、从来没有申请过此权限 2、没有申请过权限并且选择"Never ask again"选项
                        /* if (isFirstTimeAsking(arrayOf(permission))) {
                             ToastUtils.showLong("第一次申请权限")
                             firstTimeAsking(arrayOf(permission), false)
                             requestSinglePkPermission(fragment, permission)
                         }else{
                             ToastUtils.showLong("权限之前被拒绝，并且用户选择不再提示")
                         }*/
                        PkPermission.request(this, Manifest.permission.CAMERA, object : OnPermissionCallback {
                            override fun onGranted(permissions: Array<String>) {
                                Log.e("TAG", "授予了权限:Manifest.permission.CAMERA")
                            }

                            override fun onDenied(permissions: Array<String>, never: Boolean) {
                                Log.e("TAG", "是否永久:$never 拒绝了Manifest.permission.CAMERA权限")
                                /* fragment.context?.let {
                                             PkPermission.toAppSetting(it)
                                         }*/
                            }

                        })
                    }
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        PermissionHandlerManager.instance.unregisterListener(listener)
    }
}
package com.peakmain.basiclibary

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.peakmain.basiclibary.adapter.TestAdapter
import com.peakmain.basiclibary.databinding.ActivityMainBinding
import com.peakmain.basiclibary.viewModel.MainViewModel
import com.peakmain.basiclibrary.adapter.CommonRecyclerDataBindingAdapter
import com.peakmain.basiclibrary.adapter.listener.OnItemClickListener
import com.peakmain.basiclibrary.base.activity.BaseActivity
import com.peakmain.basiclibrary.constants.PermissionConstants
import com.peakmain.basiclibrary.extend.click
import com.peakmain.basiclibrary.interfaces.OnPermissionCallback
import com.peakmain.basiclibrary.permission.PkPermission
import com.peakmain.basiclibrary.utils.GlobalCoroutineExceptionHandler
import java.util.jar.Manifest

class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun initView() {
        val testAdapter = TestAdapter(getData(), LinearLayoutManager(this))
        testAdapter.bindToRecyclerView(mBinding.recyclerview)
        mBinding.recyclerview.layoutManager = LinearLayoutManager(this)
        testAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                when (position) {
                    0 -> requestSinglePermission(android.Manifest.permission.CAMERA)
                    1 -> requestPermission(PermissionConstants.STORAGE)
                    2 -> requestPermission(PermissionConstants.LOCATION)


                }
            }

        })
        testAdapter.setOnLoadMoreListener(object :
            CommonRecyclerDataBindingAdapter.OnLoadMoreListener {
            override fun onLoadMoreListener() {
                if (data.size < 60) {
                    updateData()
                    testAdapter.loadMore()
                } else {
                    testAdapter.loadNoMore()
                }
            }

        })
        GlobalCoroutineExceptionHandler().coroutineExceptionCallback = { context, exception ->

        }
        mBinding.tvRefreshStatus.click {
        }
    }

    private fun requestPermission(@PermissionConstants.PermissionGroup permissions: String) {
        if (PkPermission.isGranted(PermissionConstants.getPermissions(permissions))) {
            Log.e("TAG", "授予了权限:${permissions}")
        } else {
            val permission = PermissionConstants.getPermissions(permissions)
            PkPermission.request(this, permission, object : OnPermissionCallback {
                override fun onGranted(permissions: Array<String>) {
                    Log.e("TAG", "授予了权限:$permission")
                }

                override fun onDenied(permissions: Array<String>, never: Boolean) {
                   if(never){
                       for (s in permissions) {
                           Log.e("TAG", "拒绝了权限:$s")
                       }
                       PkPermission.toAppSetting(this@MainActivity)
                   }else{
                       for (s in permissions) {
                           Log.e("TAG", "临时授予了权限:$s")
                       }
                   }

                }

            })
        }
    }

    private fun requestSinglePermission(permission: String) {
        if (PkPermission.isGranted(permission)) {
            Log.e("TAG", "授予了权限:${permission}")
        } else {
            PkPermission.request(this, permission, object : OnPermissionCallback {
                override fun onGranted(permissions: Array<String>) {
                    Log.e("TAG", "授予了权限:$permission")
                }

                override fun onDenied(permissions: Array<String>, never: Boolean) {
                    Log.e("TAG", "是否永久:$never 拒绝了${permission}权限")
                    PkPermission.toAppSetting(this@MainActivity)
                }

            })
        }
    }

    fun setClickView(view: View) {
    }

    val data = ArrayList<String>()
    private fun getData(): MutableList<String> {
        data.add("申请单个相机权限")
        data.add("申请读写权限")
        data.add("申请位置权限")
        return data
    }

    private fun updateData(): MutableList<String> {
        data.add("申请单个相机权限")
        data.add("申请读写权限")
        data.add("申请位置权限")
        return data
    }

}
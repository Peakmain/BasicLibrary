package com.peakmain.basiclibary

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.peakmain.basiclibary.adapter.TestAdapter
import com.peakmain.basiclibary.databinding.ActivityMainBinding
import com.peakmain.basiclibary.viewModel.MainViewModel
import com.peakmain.basiclibrary.adapter.CommonRecyclerDataBindingAdapter
import com.peakmain.basiclibrary.base.activity.BaseActivity
import com.peakmain.basiclibrary.constants.PermissionConstants
import com.peakmain.basiclibrary.extend.click
import com.peakmain.basiclibrary.interfaces.OnPermissionCallback
import com.peakmain.basiclibrary.permission.PkPermission
import com.peakmain.basiclibrary.utils.GlobalCoroutineExceptionHandler

class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var progressDialog: ProgressBar
    override fun initView() {
        val testAdapter = TestAdapter(getData(), LinearLayoutManager(this))
        testAdapter.bindToRecyclerView(mBinding.recyclerview)
        mBinding.recyclerview.layoutManager = LinearLayoutManager(this)
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
            if(PkPermission.isGranted(PermissionConstants.getPermissions(PermissionConstants.STORAGE))){
                Log.e("TAG","授予了读写权限")
            }else{
                val permission=PermissionConstants.getPermissions(PermissionConstants.STORAGE)
                PkPermission.request(this,permission,object :OnPermissionCallback{
                    override fun onGranted(permissions: Array<String>) {
                        Log.e("TAG","授予了读写权限")
                    }

                    override fun onDenied(permissions: Array<String>, never: Boolean) {
                        Log.e("TAG", "拒接了了读写权限:$never")
                        PkPermission.toAppSetting(this@MainActivity)
                    }

                })
            }
        }
    }

    fun setClickView(view: View) {
    }

    val data = ArrayList<String>()
    private fun getData(): MutableList<String> {
        for (i in 1..40) {
            data.add("test$i")
        }
        return data
    }

    private fun updateData(): MutableList<String> {
        data.add("peakmain")
        data.add("peakmain1")
        data.add("peakmain2")
        data.add("peakmain3")
        data.add("peakmain4")
        data.add("peakmain5")
        data.add("peakmain6")
        return data
    }

}
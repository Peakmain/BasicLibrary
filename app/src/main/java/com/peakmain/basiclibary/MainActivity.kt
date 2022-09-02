package com.peakmain.basiclibary

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.peakmain.basiclibary.adapter.TestAdapter
import com.peakmain.basiclibary.databinding.ActivityMainBinding
import com.peakmain.basiclibary.utils.PermissionUtils.requestPermission
import com.peakmain.basiclibary.utils.PermissionUtils.requestSinglePermission
import com.peakmain.basiclibary.viewModel.MainViewModel
import com.peakmain.basiclibrary.adapter.CommonRecyclerDataBindingAdapter
import com.peakmain.basiclibrary.adapter.listener.OnItemClickListener
import com.peakmain.basiclibrary.base.activity.BaseActivity
import com.peakmain.basiclibrary.constants.ImageSelectConstants
import com.peakmain.basiclibrary.constants.PermissionConstants
import com.peakmain.basiclibrary.dialog.SubmitLoading
import com.peakmain.basiclibrary.extend.click
import com.peakmain.basiclibrary.extend.ktxRunOnUiThreadDelay
import com.peakmain.basiclibrary.image.PkImageSelector
import com.peakmain.basiclibrary.image.SimpleImageSelectorCallback
import com.peakmain.basiclibrary.interfaces.OnImageSelectorCallback
import com.peakmain.basiclibrary.utils.GlobalCoroutineExceptionHandler

class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun initView() {
        val testAdapter = TestAdapter(getData(), LinearLayoutManager(this))
        testAdapter.bindToRecyclerView(mBinding.recyclerview)
        mBinding.recyclerview.layoutManager = LinearLayoutManager(this)

        testAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                when (position) {
                    0 -> requestSinglePermission(
                        this@MainActivity,
                        android.Manifest.permission.CAMERA
                    )
                    1 -> requestPermission(this@MainActivity, PermissionConstants.STORAGE)
                    2 -> requestPermission(this@MainActivity, PermissionConstants.LOCATION)
                    3 -> PkImageSelector.builder(this@MainActivity).setSingle(true)
                        .setType(ImageSelectConstants.IMAGE_TYPE)
                        .forResult(object : SimpleImageSelectorCallback() {
                            override fun onImageSelect(uris: List<Uri?>) {
                                for (uri in uris) {
                                    Log.e("TAG", "选择了图片:$uri")
                                }
                            }
                        })
                    4-> {
                        PkImageSelector.builder(this@MainActivity).setSingle(false)
                            .setType(ImageSelectConstants.IMAGE_TYPE)
                            .setMaxNum(4)
                            .forResult(object : SimpleImageSelectorCallback() {
                                override fun onImageSelect(uris: List<Uri?>) {
                                    for (uri in uris) {
                                        Log.e("TAG", "选择了图片:$uri")
                                    }
                                }
                            })
                    }
                    5 -> PkImageSelector.builder(this@MainActivity)
                        .setType(ImageSelectConstants.TAKE_PHOTO_TYPE).forResult()
                    6->{
                        SubmitLoading.instance.show(this@MainActivity)
                        ktxRunOnUiThreadDelay(2000) {
                            SubmitLoading.instance.success()
                        }
                    }

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

    val data = ArrayList<String>()
    private fun getData(): MutableList<String> {
        data.add("申请单个相机权限")
        data.add("申请读写权限")
        data.add("申请位置权限")
        data.add("选择单个图片")
        data.add("选择多个图片")
        data.add("拍照")
        data.add("加载loading")
        return data
    }

    private fun updateData(): MutableList<String> {
        data.add("申请单个相机权限")
        data.add("申请读写权限")
        data.add("申请位置权限")
        return data
    }

}
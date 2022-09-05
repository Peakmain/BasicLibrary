package com.peakmain.basiclibary.fragment

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.peakmain.basiclibary.R
import com.peakmain.basiclibary.adapter.TestAdapter
import com.peakmain.basiclibary.databinding.FragmentHomeBinding
import com.peakmain.basiclibary.utils.PermissionUtils.requestPermission
import com.peakmain.basiclibary.utils.PermissionUtils.requestSinglePermission
import com.peakmain.basiclibary.viewModel.HomeFragmentViewModel
import com.peakmain.basiclibrary.adapter.CommonRecyclerDataBindingAdapter
import com.peakmain.basiclibrary.adapter.listener.OnItemClickListener
import com.peakmain.basiclibrary.base.activity.BaseActivity
import com.peakmain.basiclibrary.base.fragment.BaseFragment
import com.peakmain.basiclibrary.constants.ImageSelectConstants
import com.peakmain.basiclibrary.constants.PermissionConstants
import com.peakmain.basiclibrary.dialog.SubmitLoading
import com.peakmain.basiclibrary.extend.click
import com.peakmain.basiclibrary.extend.ktxRunOnUiThreadDelay
import com.peakmain.basiclibrary.image.PkImageSelector
import com.peakmain.basiclibrary.image.SimpleImageSelectorCallback
import com.peakmain.basiclibrary.utils.GlobalCoroutineExceptionHandler
import com.peakmain.basiclibrary.utils.StatusBarUtils
import com.peakmain.ui.recyclerview.itemdecoration.DividerGridItemDecoration

class HomeFragment(override val layoutId: Int = R.layout.fragment_home) :
    BaseFragment<FragmentHomeBinding, HomeFragmentViewModel>() {
    override fun initView(fragmentView:View) {
        val testAdapter = TestAdapter(getData())
        testAdapter.bindToRecyclerView(mBinding.recyclerview)
        context?.let {
             mBinding.recyclerview.addItemDecoration(DividerGridItemDecoration(it))
        }
        testAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                when (position) {
                    0 -> requestSinglePermission(
                        this@HomeFragment,
                        android.Manifest.permission.CAMERA
                    )
                    1 -> requestPermission(this@HomeFragment, PermissionConstants.STORAGE)
                    2 -> requestPermission(this@HomeFragment, PermissionConstants.LOCATION)
                    3 -> PkImageSelector.builder(this@HomeFragment).setSingle(true)
                        .setType(ImageSelectConstants.IMAGE_TYPE)
                        .forResult(object : SimpleImageSelectorCallback() {
                            override fun onImageSelect(uris: List<Uri?>) {
                                for (uri in uris) {
                                    Log.e("TAG", "选择了图片:$uri")
                                }
                            }
                        })
                    4-> {
                        PkImageSelector.builder(this@HomeFragment).setSingle(false)
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
                    5 -> PkImageSelector.builder(this@HomeFragment)
                        .setType(ImageSelectConstants.TAKE_PHOTO_TYPE).forResult()
                    6->{
                        SubmitLoading.instance.show(this@HomeFragment)
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


}
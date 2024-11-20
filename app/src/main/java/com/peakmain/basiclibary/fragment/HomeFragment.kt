package com.peakmain.basiclibary.fragment

import android.Manifest
import android.app.ProgressDialog.show
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import com.peakmain.basiclibary.R
import com.peakmain.basiclibary.activity.BehaviorActivity
import com.peakmain.basiclibary.adapter.TestAdapter
import com.peakmain.basiclibary.databinding.FragmentHomeBinding
import com.peakmain.basiclibary.utils.PermissionUtils.requestPermission
import com.peakmain.basiclibary.utils.PermissionUtils.requestSinglePermission
import com.peakmain.basiclibary.viewModel.HomeFragmentViewModel
import com.peakmain.basiclibrary.adapter.CommonRecyclerDataBindingAdapter
import com.peakmain.basiclibrary.adapter.listener.OnItemClickListener
import com.peakmain.basiclibrary.base.fragment.BaseFragment
import com.peakmain.basiclibrary.constants.ImageSelectConstants
import com.peakmain.basiclibrary.constants.PermissionConstants
import com.peakmain.basiclibrary.dialog.SubmitLoading
import com.peakmain.basiclibrary.extend.click
import com.peakmain.basiclibrary.extend.ktxRunOnUiThreadDelay
import com.peakmain.basiclibrary.image.PkImageSelector
import com.peakmain.basiclibrary.image.SimpleImageSelectorCallback
import com.peakmain.basiclibrary.interfaces.IPermissionPopupListener
import com.peakmain.basiclibrary.manager.PermissionHandlerManager
import com.peakmain.basiclibrary.permission.PkPermission
import com.peakmain.basiclibrary.utils.GlobalCoroutineExceptionHandler
import com.peakmain.basiclibrary.utils.toast.PkToastUtils
import com.peakmain.ui.navigationbar.DefaultNavigationBar
import com.peakmain.ui.recyclerview.itemdecoration.DividerGridItemDecoration
import com.peakmain.ui.utils.ToastUtils

class HomeFragment(override val layoutId: Int = R.layout.fragment_home) :
    BaseFragment<FragmentHomeBinding, HomeFragmentViewModel>() {
    val listener = object : IPermissionPopupListener {
        var utils = PkToastUtils.build(activity)
        override fun onShowPermissionPopup() {
            utils.setTitle("亚朵需要申请权限")
                .setMessage("为了您能正常使用分享功能，我们将申请启动第三方APP，您可以选择取消或者同意，取消请求不影响使用其他服务")
                .show()
        }

        override fun onHidePermissionPopup() {
            utils.dismiss()
        }

    }

    override fun initView(fragmentView: View) {
        initDefaultNavigationBar(fragmentView)
        val testAdapter = TestAdapter(getData())
        testAdapter.bindToRecyclerView(mBinding.recyclerview)
        PermissionHandlerManager.instance.registerListener(listener)
        context?.let {
            mBinding.recyclerview.addItemDecoration(DividerGridItemDecoration(it))
        }
        val GROUP_LOCATION_BELOW_Q = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        testAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                when (position) {
                    0 -> requestSinglePermission(
                        this@HomeFragment,
                        Manifest.permission.CAMERA
                    )

                    1 -> requestPermission(this@HomeFragment, PermissionConstants.STORAGE)
                    2 -> requestPermission(this@HomeFragment, GROUP_LOCATION_BELOW_Q)
                    3 -> PkImageSelector.builder(this@HomeFragment).setSingle(true)
                        .setType(ImageSelectConstants.IMAGE_TYPE)
                        .forResult(object : SimpleImageSelectorCallback() {
                            override fun onImageSelect(uris: List<Uri?>) {
                                for (uri in uris) {
                                    Log.e("TAG", "选择了图片:$uri")
                                }
                            }
                        })

                    4 -> {
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

                    5 -> {
                        if (PkPermission.isGranted(
                                PermissionConstants.getPermissions(
                                    PermissionConstants.CAMERA
                                )
                            )
                        ) {
                            PkImageSelector.builder(this@HomeFragment)
                                .setType(ImageSelectConstants.TAKE_PHOTO_TYPE).forResult()
                        } else {
                            ToastUtils.showLong("请开启相机权限")
                        }
                    }

                    6 -> {
                        SubmitLoading.instance.show(this@HomeFragment)
                        ktxRunOnUiThreadDelay(2000) {
                            SubmitLoading.instance.success()
                        }
                    }

                    7 -> {
                        PkPermission.toNotificationSetting(context)
                    }

                    8 -> {
                        PkToastUtils.build(activity)
                            .setTitle("亚朵需要申请权限")
                            .setMessage("为了您能正常使用分享功能，我们将申请启动第三方APP，您可以选择取消或者同意，取消请求不影响使用其他服务")
                            .show()
                        startActivity(Intent(context, BehaviorActivity::class.java))
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

    private fun initDefaultNavigationBar(fragmentView: View) {
        DefaultNavigationBar.Builder(context, fragmentView.findViewById(R.id.view_root))
            .hideLeftText()
            .hideRightView()
            .setTitleText("首页")
            .setToolbarBackgroundColor(R.color.ui_color_01a8e3)
            .create()
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
        data.add("跳转消息通知权限界面")
        data.add("behavior封装")
        return data
    }

    override fun onDestroy() {
        super.onDestroy()
        PermissionHandlerManager.instance.unregisterListener(listener)
    }
}
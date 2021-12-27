package com.peakmain.basiclibrary.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.peakmain.basiclibrary.BR
import com.peakmain.basiclibrary.base.IApp
import com.peakmain.basiclibrary.base.viewmodel.BaseViewModel
import com.peakmain.basiclibrary.config.BasicLibraryConfig

/**
 * author ：Peakmain
 * createTime：2021/12/27
 * mail:2726449200@qq.com
 * describe：
 */
abstract class BaseFragment<T : ViewDataBinding, E : BaseViewModel>(var modelClass: Class<E>?) :
    Fragment() {

    protected lateinit var mViewModel: E
    protected lateinit var mBinding: T
    private var app: IApp? = BasicLibraryConfig.getInstance().getApp()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(layoutId, container, false)
        if(modelClass!=null){
            mViewModel=getViewModel(modelClass!!)
            mBinding = DataBindingUtil.bind(fragmentView)!!
            mBinding.setVariable(BR.vm, mViewModel)
            mViewModel.initModel()
        }
        initView(fragmentView)
        return fragmentView
    }

    protected fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        if(app==null){
            throw NullPointerException("app must not be null")
        }
        return app!!.getViewModelProvider().get(modelClass)
    }

    abstract val layoutId: Int
    abstract fun initView(fragmentView: View)
}
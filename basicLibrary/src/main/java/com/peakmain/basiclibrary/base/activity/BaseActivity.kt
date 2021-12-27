package com.peakmain.basiclibrary.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.peakmain.basiclibrary.BR
import com.peakmain.basiclibrary.base.IApp
import com.peakmain.basiclibrary.base.viewmodel.BaseViewModel
import com.peakmain.basiclibrary.config.BasicLibraryConfig

/**
 * author ：Peakmain
 * createTime：2021/12/24
 * mail:2726449200@qq.com
 * describe：
 */
abstract class BaseActivity<T : ViewDataBinding, E : BaseViewModel>(private var  modelClass: Class<E>) : AppCompatActivity() {
    abstract val layoutId: Int
    protected lateinit var mBinding: T
    protected lateinit var mViewModel: E
    private var app: IApp? = BasicLibraryConfig.getInstance().getApp()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBefore()
        mBinding = DataBindingUtil.setContentView(this, layoutId)
        mViewModel = getViewModel(modelClass)
        mViewModel.initModel()
        mBinding.setVariable(BR.vm, mViewModel)
        initView()
    }

    abstract fun initView()


    fun initBefore() {

    }

    protected fun <E : ViewModel> getViewModel(modelClass: Class<E>): E {
        if(app==null){
            throw NullPointerException("app must not be null")
        }
        return app!!.getViewModelProvider().get(modelClass)
    }
}
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
import java.lang.reflect.ParameterizedType

/**
 * author ：Peakmain
 * createTime：2021/12/24
 * mail:2726449200@qq.com
 * describe：
 */
abstract class BaseActivity<T : ViewDataBinding, E : BaseViewModel>() :
    AppCompatActivity() {
    abstract val layoutId: Int
    protected lateinit var mBinding: T
    protected lateinit var mViewModel: E
    private var app: IApp? = BasicLibraryConfig.getInstance().getApp()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBefore()
        mBinding = DataBindingUtil.setContentView(this, layoutId)
        initViewModel()
        mViewModel.initModel()
        mBinding.setVariable(BR.vm, mViewModel)
        initView()
    }

    private fun initViewModel() {
        val superClass = javaClass.genericSuperclass
        if (superClass is ParameterizedType) {
            val type = superClass.actualTypeArguments[1]
            val clazz = type as Class<E>
            mViewModel = getViewModel(clazz)
        }else{
            throw IllegalArgumentException("ParameterizedType error")
        }

    }


    abstract fun initView()


    fun initBefore() {
    }

    protected fun <E : ViewModel> getViewModel(modelClass: Class<E>): E {
        if (app == null) {
            throw NullPointerException("app must not be null")
        }
        return app!!.getViewModelProvider().get(modelClass)
    }
}
package com.peakmain.basiclibrary.config

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.peakmain.basiclibrary.adapter.holder.BaseLibraryFooterViewHolder

/**
 * author ：Peakmain
 * createTime：2022/2/17
 * mail:2726449200@qq.com
 * describe：显示更多的Config
 */
interface BaseAdapterFooterConfig<T, E : ViewDataBinding> {
    fun footerDataBinding(parent: ViewGroup): E
    fun footerViewHolder(holder: BaseLibraryFooterViewHolder<E>,data:MutableList<T>,loadStatus:Int)
    fun layoutManager(): LinearLayoutManager
}
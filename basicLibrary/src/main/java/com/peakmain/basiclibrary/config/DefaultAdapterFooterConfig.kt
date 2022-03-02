package com.peakmain.basiclibrary.config

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.peakmain.basiclibrary.R
import com.peakmain.basiclibrary.adapter.CommonRecyclerDataBindingAdapter
import com.peakmain.basiclibrary.adapter.holder.BaseLibraryFooterViewHolder
import com.peakmain.basiclibrary.databinding.LibraryRecyclerFootLayoutBinding

/**
 * author ：Peakmain
 * createTime：2022/2/17
 * mail:2726449200@qq.com
 * describe：
 */
class DefaultAdapterFooterConfig<T, E : ViewDataBinding>(
    var layoutManager: LinearLayoutManager,
    var loadContent: String = "正在加载中...",
    var loadCompleteContent: String = "加载完成",
    var loadNoMoreContent: String = "没有更多了",
    var emptyContent: String = "暂无内容"
) {

    val item = object : BaseAdapterFooterConfig<T, E> {
        override fun footerDataBinding(parent: ViewGroup): E {
            return DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.library_recycler_foot_layout,
                parent,
                false
            )
        }

        override fun footerViewHolder(
            holder: BaseLibraryFooterViewHolder<E>,
            data: MutableList<T>,
            loadStatus: Int
        ) {
            val dataBinding = holder.itemDataBinding as LibraryRecyclerFootLayoutBinding
            when (loadStatus) {
                CommonRecyclerDataBindingAdapter.STATUS_LOADING -> {
                    dataBinding.progress = true
                    dataBinding.content = loadContent
                }
                CommonRecyclerDataBindingAdapter.STATUS_COMPLETE -> {
                    dataBinding.progress = false
                    dataBinding.content = loadCompleteContent
                }
                CommonRecyclerDataBindingAdapter.STATUS_NO_MORE -> {
                    dataBinding.progress = false
                    dataBinding.content = if (data.isEmpty()) emptyContent else loadNoMoreContent
                }
            }
        }

        override fun layoutManager(): LinearLayoutManager {
            return layoutManager
        }
    }
}
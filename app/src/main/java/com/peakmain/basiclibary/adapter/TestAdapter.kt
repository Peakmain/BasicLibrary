package com.peakmain.basiclibary.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import com.peakmain.basiclibary.R
import com.peakmain.basiclibary.databinding.RecyclerAdpterTestBinding
import com.peakmain.basiclibrary.adapter.CommonRecyclerDataBindingAdapter
import com.peakmain.basiclibrary.adapter.holder.BaseLibraryViewHolder
import com.peakmain.basiclibrary.config.DefaultAdapterFooterConfig

/**
 * author ：Peakmain
 * createTime：2022/2/17
 * mail:2726449200@qq.com
 * describe：
 */
class TestAdapter(data: MutableList<String>, layoutManager: LinearLayoutManager) :
    CommonRecyclerDataBindingAdapter<String, RecyclerAdpterTestBinding>(
        data,
        R.layout.recycler_adpter_test,
        DefaultAdapterFooterConfig<String,RecyclerAdpterTestBinding>(layoutManager).item
    ) {
    override fun convert(
        holder: BaseLibraryViewHolder<RecyclerAdpterTestBinding>,
        itemData: String,
        position: Int
    ) {
        val binding = holder.itemDataBinding
        binding.vm = itemData
    }

}
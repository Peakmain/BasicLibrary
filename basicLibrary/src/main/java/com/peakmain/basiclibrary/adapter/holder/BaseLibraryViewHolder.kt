package com.peakmain.basiclibrary.adapter.holder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * author ：Peakmain
 * createTime：2022/2/17
 * mail:2726449200@qq.com
 * describe：
 */
open class BaseLibraryViewHolder<E : ViewDataBinding> constructor(var itemDataBinding: E) :
    RecyclerView.ViewHolder(itemDataBinding.root)
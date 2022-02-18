package com.peakmain.basiclibrary.adapter.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * author ：Peakmain
 * createTime：2022/2/17
 * mail:2726449200@qq.com
 * describe：
 */
abstract class BaseLibraryOnScrollListener constructor() : RecyclerView.OnScrollListener(),OnScrollCallback {

    private var mLinearLayoutManager: LinearLayoutManager? = null

    constructor(
        linearLayoutManager: LinearLayoutManager
    ) : this() {
        this.mLinearLayoutManager = linearLayoutManager
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {
            onScrolledUp()
           mLinearLayoutManager?.apply {
               val findLastVisibleItemPosition = findLastVisibleItemPosition()
               val itemCount = itemCount
               if(findLastVisibleItemPosition==itemCount-1){
                   onLoadMore()
               }
           }
        }else{
            onScrolledDown()
        }
    }
    abstract fun onLoadMore()
    override fun onScrolledUp() {

    }

    override fun onScrolledDown() {

    }
}

interface OnScrollCallback {
    fun onScrolledUp()
    fun onScrolledDown()
}
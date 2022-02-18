package com.peakmain.basiclibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peakmain.basiclibrary.adapter.holder.BaseLibraryFooterViewHolder
import com.peakmain.basiclibrary.adapter.holder.BaseLibraryViewHolder
import com.peakmain.basiclibrary.config.BaseAdapterFooterConfig
import com.peakmain.ui.recyclerview.listener.OnItemClickListener
import com.peakmain.ui.recyclerview.listener.OnLongClickListener
import androidx.annotation.IntDef
import com.peakmain.basiclibrary.adapter.listener.BaseLibraryOnScrollListener
import java.lang.RuntimeException


/**
 * author ：Peakmain
 * createTime：2022/2/17
 * mail:2726449200@qq.com
 * describe：MVVM的adapter
 */
abstract class CommonRecyclerDataBindingAdapter<T, E : ViewDataBinding>(
    data: MutableList<T>,
    var layoutId: Int,
    private var baseAdapterFooterConfig: BaseAdapterFooterConfig<T, E>? = null
) : RecyclerView.Adapter<BaseLibraryViewHolder<E>>() {
    companion object {
        const val TYPE_DEFAULT = 1
        const val TYPE_MORE = 2
        const val STATUS_LOADING = 1001
        const val STATUS_COMPLETE = 1002
        const val STATUS_NO_MORE = 1003
    }

    var mRecyclerView: RecyclerView? = null


    @IntDef(value = [STATUS_LOADING, STATUS_COMPLETE, STATUS_NO_MORE])
    @Retention(AnnotationRetention.SOURCE)
    annotation class LoadingStatus

    private lateinit var baseLibraryFooterViewHolder: BaseLibraryFooterViewHolder<E>
    private var showMore: Boolean = false
    private var status = STATUS_NO_MORE
    protected var mData //数据
            : MutableList<T>
    private var mLayoutId: Int
    protected lateinit var itemView: E


    override fun getItemViewType(position: Int): Int = if (showMore) {
        if (position + 1 == itemCount) TYPE_MORE
        else TYPE_DEFAULT
    } else TYPE_DEFAULT

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseLibraryViewHolder<E> {
        return when (viewType) {
            TYPE_DEFAULT -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                itemView = DataBindingUtil.inflate(layoutInflater, layoutId, parent, false)
                BaseLibraryViewHolder(itemView)
            }
            else -> {
                return BaseLibraryFooterViewHolder(
                    baseAdapterFooterConfig!!.footerDataBinding(
                        parent
                    )
                )
            }
        }

    }

    fun bindToRecyclerView(recyclerView: RecyclerView) {
        if (mRecyclerView != null) {
            throw RuntimeException("Don't bind twice")
        }
        mRecyclerView = recyclerView

        baseAdapterFooterConfig?.let {
            mRecyclerView?.apply {
                it.layoutManager().let {
                    layoutManager = it
                    addOnScrollListener(object : BaseLibraryOnScrollListener(it) {
                        override fun onLoadMore() {
                            onLoadMoreListener?.onLoadMoreListener()
                        }

                    })

                }
            }

        }
        mRecyclerView!!.adapter = this
    }


    override fun onBindViewHolder(holder: BaseLibraryViewHolder<E>, position: Int) {
        // 设置点击和长按事件
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener { mItemClickListener!!.onItemClick(holder.adapterPosition) }
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener { mLongClickListener!!.onLongClick(holder.adapterPosition) }
        }
        if (position == data.size) {
            baseAdapterFooterConfig?.let {
                baseLibraryFooterViewHolder = holder as BaseLibraryFooterViewHolder<E>
                it.footerViewHolder(
                    baseLibraryFooterViewHolder,
                    data,
                    status
                )
            }
        } else {
            convert(holder, data[position], position)
        }
    }

    fun hideMore() {
        showMore = false
        if (this::baseLibraryFooterViewHolder.isInitialized) {
            baseLibraryFooterViewHolder.itemDataBinding.root.visibility = View.GONE
        }
    }

    fun showMore(@LoadingStatus status: Int) {
        notifyLoadingMore(status)
    }

    fun loadMore() {
        notifyLoadingMore(STATUS_LOADING)
    }

    fun loadComplete() {
        notifyLoadingMore(STATUS_COMPLETE)
    }

    fun loadNoMore() {
        notifyLoadingMore(STATUS_NO_MORE)
    }

    private fun notifyLoadingMore(status: Int) {
        showMore = true
        this.status = status
        if (this::baseLibraryFooterViewHolder.isInitialized) {
            baseLibraryFooterViewHolder.itemDataBinding.root.visibility = View.VISIBLE
            baseAdapterFooterConfig?.footerViewHolder(baseLibraryFooterViewHolder, data, status)
        }
        notifyItemChanged(itemCount)
    }

    abstract fun convert(
        holder: BaseLibraryViewHolder<E>,
        itemData: T,
        position: Int
    )

    override fun getItemCount(): Int {
        return if (showMore) mData.size + 1 else mData.size
    }

    var mItemClickListener: OnItemClickListener? = null
    var mLongClickListener: OnLongClickListener? = null
    fun setOnItemClickListener(itemClickListener: OnItemClickListener?) {
        mItemClickListener = itemClickListener
    }

    fun setOnLongClickListener(longClickListener: OnLongClickListener?) {
        mLongClickListener = longClickListener
    }

    init {
        mData = data
        mLayoutId = layoutId
        setGridLayoutManager()
    }

    private fun setGridLayoutManager() {
        if (null != baseAdapterFooterConfig) {
            this.showMore = true
            baseAdapterFooterConfig!!.layoutManager().also {
                if (it is GridLayoutManager) {
                    it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            val type = getItemViewType(position)
                            return if (type == TYPE_MORE) {
                                1
                            } else {
                                it.spanCount
                            }
                        }
                    }
                }
            }
        } else {
            this.showMore = false
        }
    }

    fun addData(data: T) {
        mData.add(data)
        notifyItemInserted(mData.size - 1)
    }

    fun addData(@androidx.annotation.IntRange(from = 0) position: Int, data: T) {
        mData.add(position, data)
        notifyItemInserted(position)
    }

    fun addData(datas: Collection<T>) {
        mData.addAll(datas)
        notifyItemRangeInserted(mData.size - datas.size, datas.size)
    }

    fun replaceData(newData: Collection<T>) {
        if (mData !== newData) {
            mData.clear()
            mData.addAll(newData)
        }
        notifyItemRangeChanged(0, data.size)
    }

    fun removeData(@IntRange(from = 0) position: Int) {
        mData.removeAt(position)
        notifyItemRemoved(position)
    }

    val data: MutableList<T>
        get() = mData

    fun setData(data: MutableList<T>) {
        mData = data
        notifyItemRangeChanged(0, data.size)
    }

    private var onLoadMoreListener: OnLoadMoreListener? = null

    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener
    }

    interface OnLoadMoreListener {
        fun onLoadMoreListener()
    }
}
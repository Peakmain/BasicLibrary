package com.peakmain.ui.recyclerview.listener

/**
 * author ：Peakmain
 * version : 1.0
 * createTime：2019/2/25
 * mail:2726449200@qq.com
 * describe：Adapter条目的长按事件
 */
interface OnLongClickListener {
    fun onLongClick(position: Int): Boolean
}
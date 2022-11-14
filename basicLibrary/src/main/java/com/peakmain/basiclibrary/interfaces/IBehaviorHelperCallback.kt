package com.peakmain.basiclibrary.interfaces

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

/**
 * author ：Peakmain
 * createTime：2022/11/14
 * mail:2726449200@qq.com
 * describe：
 */
interface IBehaviorHelperCallback {
    fun open(bottomSheet: View?, @BottomSheetBehavior.State newState:Int)
    fun close(bottomSheet: View?, @BottomSheetBehavior.State newState:Int)
}
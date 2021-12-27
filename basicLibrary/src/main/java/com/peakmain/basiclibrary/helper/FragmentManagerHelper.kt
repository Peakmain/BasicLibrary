package com.peakmain.basiclibrary.helper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * author ：Peakmain
 * createTime：2021/3/26
 * mail:2726449200@qq.com
 * describe：Fragment的帮助类
 */
class FragmentManagerHelper constructor(
    private var mFragementManager: FragmentManager,
    private var mContainerViewId: Int
) {
    /**
     * 添加fragment
     */
    fun addFragment(fragment: Fragment) {
        val fragmentTransaction = mFragementManager.beginTransaction()
        fragmentTransaction.add(mContainerViewId, fragment)
        fragmentTransaction.commitAllowingStateLoss()
    }
    /**
     * 切换fragment
     */
    fun switchFragment(fragment: Fragment){
        val fragmentTransaction = mFragementManager.beginTransaction()
        val fragments = mFragementManager.fragments
        fragments.forEach { fragment ->
            fragmentTransaction.hide(fragment)
        }
        if(!fragments.contains(fragment)){
            fragmentTransaction.add(mContainerViewId,fragment)
        }else{
            fragmentTransaction.show(fragment)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }
}
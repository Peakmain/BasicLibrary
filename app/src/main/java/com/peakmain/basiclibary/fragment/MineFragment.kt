package com.peakmain.basiclibary.fragment

import android.view.View
import com.peakmain.basiclibary.R
import com.peakmain.basiclibary.databinding.FragmentMineBinding
import com.peakmain.basiclibrary.base.fragment.BaseFragment
import com.peakmain.basiclibrary.viewmodel.EmptyViewModel
import com.peakmain.ui.navigationbar.DefaultNavigationBar

/**
 * author ：Peakmain
 * createTime：2020/3/9
 * mail:2726449200@qq.com
 * describe：
 */
class MineFragment(override val layoutId: Int = R.layout.fragment_mine) :
    BaseFragment<FragmentMineBinding, EmptyViewModel>() {
    override fun initView(fragmentView: View) {
        DefaultNavigationBar.Builder(context, fragmentView.findViewById(R.id.view_root))
            .hideLeftText()
            .hideRightView()
            .setTitleText("我的")
            .setToolbarBackgroundColor(R.color.ui_color_01a8e3)
            .create()
    }
}
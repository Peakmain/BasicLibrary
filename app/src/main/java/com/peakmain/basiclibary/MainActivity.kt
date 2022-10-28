package com.peakmain.basiclibary

import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.peakmain.basiclibary.databinding.ActivityMainBinding
import com.peakmain.basiclibary.fragment.HomeFragment
import com.peakmain.basiclibary.fragment.MineFragment
import com.peakmain.basiclibary.viewModel.MainViewModel
import com.peakmain.basiclibrary.base.activity.BaseActivity
import com.peakmain.basiclibrary.utils.StatusBarUtils

/**
 * author ：Peakmain
 * createTime：2022/9/2
 * mail:2726449200@qq.com
 * describe：
 */
class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, MainViewModel>() {
    //View
    var mBottomNavigation: BottomNavigationView? = null

    //fragments
    private var mHomeFragment: HomeFragment? = null
    private var mMineFragment: MineFragment? = null

    override fun initView() {
        StatusBarUtils.setColor(this, ContextCompat.getColor(this, R.color.ui_color_01a8e3), 0)
        mBottomNavigation = findViewById(R.id.bottom_navigation)
        showFragment(FRAGMENT_HOME)
        mBottomNavigation!!.setOnNavigationItemSelectedListener { item: MenuItem ->
            onOptionsItemSelected(
                item
            )
        }
        mBottomNavigation!!.selectedItemId = R.id.menu_home
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            R.id.menu_home -> {
                showFragment(FRAGMENT_HOME)
                return true
            }
            R.id.menu_me -> {
                showFragment(FRAGMENT_ME)
                return true
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showFragment(index: Int) {
        val ft = supportFragmentManager.beginTransaction()
        hintFragment(ft)
        when (index) {
            FRAGMENT_HOME ->
                /**
                 * 如果Fragment为空，就新建一个实例
                 * 如果不为空，就将它从栈中显示出来
                 */
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment()
                    ft.add(R.id.container, mHomeFragment!!, HomeFragment::class.java.name)
                } else {
                    ft.show(mHomeFragment!!)
                }
            FRAGMENT_ME -> if (mMineFragment == null) {
                mMineFragment = MineFragment()
                ft.add(R.id.container, mMineFragment!!, MineFragment::class.java.name)
            } else {
                ft.show(mMineFragment!!)
            }
            else -> {
            }
        }
        ft.commit()
    }

    /**
     * 隐藏fragment
     */
    private fun hintFragment(ft: FragmentTransaction) {
        // 如果不为空，就先隐藏起来
        if (mHomeFragment != null) {
            ft.hide(mHomeFragment!!)
        }
        if (mMineFragment != null) {
            ft.hide(mMineFragment!!)
        }
    }

    companion object {
        //底部切换的tab常量
        private const val FRAGMENT_HOME = 0
        private const val FRAGMENT_ME = 1
    }
}